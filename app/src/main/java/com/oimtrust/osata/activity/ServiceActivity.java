package com.oimtrust.osata.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.oimtrust.osata.R;
import com.oimtrust.osata.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceActivity extends AppCompatActivity {

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_UPLOAD_IMAGE_REQUEST_CODE = 200;

    final Context context   = this;

    EditText txtNamaWisata, txtDeskripsi, txtIdUser;
    Spinner spinnerDistrict;
    ImageView imgWisataPhoto;
    Button btnChoicePhoto, btnSendDataWisata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        String list_district[]    = {
                "-Pilih Provinsi-",
                "Aceh",
                "Bali",
                "Banten",
                "Bengkulu",
                "DI Yogyakarta",
                "DKI Jakarta",
                "Gorontalo",
                "Jambi",
                "Jawa Barat",
                "Jawa Tengah",
                "Jawa Timur",
                "Kalimantan Barat",
                "Kalimantan Selatan",
                "Kalimantan Tengah",
                "Kalimantan Timur",
                "Kalimantan Utara",
                "Kep. Bangka Belitung",
                "Kep. Riau",
                "Lampung",
                "Maluku",
                "Maluku Utara",
                "Nusa Tenggara Barat",
                "Nusa Tenggara Timur",
                "Papua",
                "Papua Barat",
                "Riau",
                "Sulawesi Barat",
                "Sulawesi Selatan",
                "Sulawesi Tengah",
                "Sulawesi Tenggara",
                "Sulawesi Utara",
                "Sumatera Barat",
                "Sumatera Selatan",
                "Sumatera Utara"
        };

        Toolbar toolbar     = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNamaWisata       = (EditText) findViewById(R.id.namaWisata_text_content_service);

        /**
         * Untuk membuat Spinner menu provinsi
         */
        spinnerDistrict     = (Spinner) findViewById(R.id.spinner_district_content_service);
        ArrayAdapter<String> adapterList    = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_district);
        adapterList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDistrict.setAdapter(adapterList);

        /**
         * Untuk Foto Wisata
         */
        imgWisataPhoto      = (ImageView) findViewById(R.id.imgPreview_content_service);

        /**
         * Untuk Mengambil Foto dengan Button
         */
        btnChoicePhoto      = (Button) findViewById(R.id.btn_choicePhoto_content_service);
        btnChoicePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /**
         * untuk ID_User
         * Tapi untuk sementara kosongan dulu
         */

        //==========================

        btnSendDataWisata       = (Button) findViewById(R.id.btn_send_content_service);
        btnSendDataWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Kosongan Dulu yah
                 */
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private void selectImage(){
        final CharSequence[] items  = {"Ambil Foto", "Pilih Dari Galeri", "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
        builder.setTitle("Tambah Foto");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Foto")){
                    Intent intentFoto   = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentFoto, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                }else if (items[item].equals("Pilih Dari Galeri")){
                    Intent intentGaleri = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    );
                    intentGaleri.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intentGaleri, "Pilih File"),
                            GALLERY_UPLOAD_IMAGE_REQUEST_CODE
                    );
                }else if (items[item].equals("Batal")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
                onCaptureImageResult(data);
            else if (requestCode == GALLERY_UPLOAD_IMAGE_REQUEST_CODE)
                onSelectFromGalleryResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

        String partFilename = currentDateFormat();
        storeCameraPhotoInSDCard(bitmap, partFilename);

        // display the image from SD Card to ImageView Control
        String storeFilename = "IMG_" + partFilename + ".jpg";
        Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
        imgWisataPhoto.setImageBitmap(mBitmap);

    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        imgWisataPhoto.setImageBitmap(bm);
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.US);
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.IMAGE_DIRECTORY_NAME + File.separator
                + "IMG_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.IMAGE_DIRECTORY_NAME + File.separator
                + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
