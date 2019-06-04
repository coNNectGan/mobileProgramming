package com.example.amustcompleteapps;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity {
    TextView tvreg;
    EditText edname,edpassword;
    Button btnlogin;
    SharedPreferences sharedPreferences;
    CheckBox cbrem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        edname = findViewById(R.id.editTextName);
        edpassword = findViewById(R.id.editTextPass);
        btnlogin = findViewById(R.id.butLogin);
        cbrem = findViewById(R.id.checkBoxRme);
        tvreg = findViewById(R.id.textViewRegister);
        //go to register page
        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
            }
        });
        //-------------------------login button------------------------------------------------------------------------


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String pass= edpassword.getText().toString();
                //database会用到的
                 loginUser(name,pass);
            }
        });
        //-------------------------check box remember me------------------------------------------------------------------------

        cbrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String pass = edpassword.getText().toString();
                //database会用到的
                savePref(name,pass);


            }
        });

        loadPref();
    }
//-------------------------------database part----------------------------------------------------------------------
    private void savePref(String n, String p) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", n);
        editor.putString("password", p);
        editor.commit();
        Toast.makeText(this, "Preferences has been saved", Toast.LENGTH_SHORT).show();
    }

    private void loadPref() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String prename = sharedPreferences.getString("name", "");
        String prpass = sharedPreferences.getString("password", "");
        if (prename.length()>0){
            cbrem.setChecked(true);
            edname.setText(prename);
            edpassword.setText(prpass);
        }
    }
    private void loginUser(final String name, final String pass) {
        class LoginUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginPage.this,
                        "Login user","...",false,false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("password",pass);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest
                        ("http://www.socstudents.net/phpcoNNect/login.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(LoginPage.this, s, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginPage.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid",name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginUser loginUser = new LoginUser();
        loginUser.execute();
    }

}
