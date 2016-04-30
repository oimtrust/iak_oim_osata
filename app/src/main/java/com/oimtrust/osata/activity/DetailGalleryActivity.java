package com.oimtrust.osata.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.oimtrust.osata.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailGalleryActivity extends AppCompatActivity {
    private ImageView imgDetail;
    public static String KEY_IMAGE  = "gallery";
    private String galleryURL;
    private PhotoViewAttacher photoViewAttacher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gallery);

        imgDetail           = (ImageView) findViewById(R.id.img_detail_Gallery);
        galleryURL          = getIntent().getStringExtra(KEY_IMAGE);
        photoViewAttacher   = new PhotoViewAttacher(imgDetail);

        Picasso.with(DetailGalleryActivity.this).load(galleryURL).into(imgDetail);
    }

    public static void toDetailImageActivity(Activity activity, String galleryURL){
        Intent intent   = new Intent(activity, DetailGalleryActivity.class);
        intent.putExtra(KEY_IMAGE, galleryURL);
        activity.startActivityForResult(intent, 0);
    }
}
