package com.telecom.lille.AndroidPictureAnalyser;


import com.example.tpandroid1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdaptater extends ArrayAdapter<Picture> {

   public ListAdaptater (Context context, Picture[] values) {
        super(context, R.layout.rowlayout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
          getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView brand = (TextView) rowView.findViewById(R.id.label);
        TextView website = (TextView) rowView.findViewById(R.id.website);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        brand.setText(getItem(position).getBrand());
        if(getItem(position).match()){
        	brand.setText(getItem(position).getBrand() + "  " + getItem(position).getMatchingSize() );
        }else{
        	brand.setText(getItem(position).getBrand());
        }
        website.setText(getItem(position).getWebsite());

        if(convertView == null)
          imageView.setImageResource(getItem(position).getBrandId());
        else
          rowView = (View)convertView;
        
       /* viewHolder.button.setOnClickListener
        (new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int position = (int) viewHolder.button.getTag();
          Log.d(TAG, "Position is: " +position);
        }
      });
      */

        return rowView;
    }

}