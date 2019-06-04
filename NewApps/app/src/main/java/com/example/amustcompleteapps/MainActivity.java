package com.example.amustcompleteapps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView lvphone;         //listOfView
    ArrayList<HashMap<String, String>> phonelist;  //phonelist array
    ArrayList<HashMap<String, String>> cartlist;   //cartList array
    double total;
    Spinner spBrand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvphone = findViewById(R.id.listviewRest); //listview
        cartlist = new ArrayList<>(); //
        spBrand = findViewById(R.id.spinner); //
        //package
        Intent intent = getIntent();
        loadPhone(spBrand.getSelectedItem().toString());
        lvphone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phoneid", phonelist.get(position).get("phoneid"));
                bundle.putString("phonebrand", phonelist.get(position).get("phonebrand"));
                bundle.putString("phonename", phonelist.get(position).get("phonename"));
                bundle.putString("phoneprice", phonelist.get(position).get("phoneprice"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        spBrand.setSelection(0, false);
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadPhone(spBrand
                        .getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //-------------------------------------------------------------------------------------------------------------
    private void loadPhone(final String brand) {
        class LoadPhone extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... volids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("phonebrand", brand);
                RequestHandler rh = new RequestHandler();
                phonelist = new ArrayList<>();
                String s = rh.sendPostRequest
                        ("http://www.socstudents.net/phpcoNNect/load_phoneBrand.php", hashMap);
                return s;
            }

            //-----------------------------------------------------------------------------------------------------------------
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                phonelist.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray phonearray = jsonObject.getJSONArray("phone");
                    Log.e("coNNect", jsonObject.toString());
                    for (int i = 0; i < phonearray.length(); i++) {
                        JSONObject c = phonearray.getJSONObject(i);
                        String pid = c.getString("phoneid");
                        String pbrand = c.getString("phonebrand");
                        String pname = c.getString("phonename");
                        String pprice = c.getString("phoneprice");


                        HashMap<String, String> phonelisthash = new HashMap<>();
                        phonelisthash.put("phoneid", pid);
                        phonelisthash.put("phonebrand", pbrand);
                        phonelisthash.put("phonename", pname);
                        phonelisthash.put("phoneprice", pprice);

                        phonelist.add(phonelisthash);
                    }
                } catch (final JSONException e) {
                    Log.e("JSONERROR", e.toString());
                }

                ListAdapter adapter = new CustomAdapter(
                        MainActivity.this, phonelist,
                        R.layout.cust_list_product, new String[]
                        {"phonebrand", "phonename", "phoneprice"}, new int[]
                        {R.id.textView, R.id.textView2, R.id.textView3});
                lvphone.setAdapter(adapter);
            }

        }
        LoadPhone loadPhone = new LoadPhone();
        loadPhone.execute();
    }
}
