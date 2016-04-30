package com.oimtrust.osata.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.oimtrust.osata.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Oim on 4/26/2016.
 */
public class GalleryItemAdapter extends BaseAdapter{
    String[] items;
    Activity activity;

    public GalleryItemAdapter(Activity activity, String[] items){
        this.activity   = activity;
        this.items      = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view   = convertView;
        ViewHolder holder   = null;

        if (view == null){
            holder                  = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view                    = inflater.inflate(R.layout.item_grid_gallery, null);
            holder.imgGalleryItem   = (ImageView) view.findViewById(R.id.img_item_grid_gallery);
            view.setTag(holder);
        }else {
            holder  = (ViewHolder) view.getTag();
        }

        Picasso.with(activity).load(items[position]).placeholder(ContextCompat.getDrawable(activity, R.drawable.placeholder))
                .into(holder.imgGalleryItem);
        return view;
    }

    static class ViewHolder{
        ImageView imgGalleryItem;
    }
}
