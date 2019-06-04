package com.example.amustcompleteapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends SimpleAdapter {

    private Context mContext;
    public LayoutInflater inflater=null;
    public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        try{
            if(convertView==null)
                vi = inflater.inflate(R.layout.cust_list_product, null);
            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

            //edit part connect to layout
            TextView tvpName = vi.findViewById(R.id.textView);
            TextView tvpBrand = vi.findViewById(R.id.textView2);
            TextView tvpPrice = vi.findViewById(R.id.textView3);
            CircleImageView imgphone =vi.findViewById(R.id.imageView2);

            //entity
            String phonename = (String) data.get("phonename");
            String phonebrand =(String) data.get("phonebrand");
            String phoneid=(String) data.get("phoneid");
            String phoneprice=(String) data.get("phoneprice");
            tvpName.setText(phonename);
            tvpBrand.setText(phonebrand);
            tvpPrice.setText(phoneprice);

            String image_url = "http://www.socstudents.net/phpcoNNect/profileimages/"+phoneid+".jpg";
            Picasso.with(mContext).load(image_url)
                    .fit().into(imgphone);

        }catch (IndexOutOfBoundsException e){

        }

        return vi;
    }
}