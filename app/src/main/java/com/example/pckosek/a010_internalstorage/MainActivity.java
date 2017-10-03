package com.example.pckosek.a010_internalstorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mWriteTextView;
    private TextView mReadTextView;
    private TextView mDisplayTextView;

    private final String INTERNAL_STORAGE_FILE = "storage.txt";


    // ------------ MAIN  ------------ //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWriteTextView = (TextView) findViewById(R.id.tv_write);
        mReadTextView = (TextView) findViewById(R.id.tv_read);
        mDisplayTextView = (TextView) findViewById(R.id.tv_display);

        mWriteTextView.setOnClickListener(this);
        mReadTextView.setOnClickListener(this);
    }


     // ------------ INPUT HANDLER ------------ //
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_write :
                writeInternalFile("SOME TEXT");
                break;
            case R.id.tv_read :
                String s = readInternalFile();
                mDisplayTextView.setText(s);
                break;
            default :
        }
    }


    // ------------ FILE IO  ------------ //
    public void writeInternalFile(String s) {
        Context context = getApplicationContext();
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(INTERNAL_STORAGE_FILE, Context.MODE_PRIVATE);
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readInternalFile() {
        Context context = getApplicationContext();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            FileInputStream fis;
            fis = context.openFileInput(INTERNAL_STORAGE_FILE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UNTESTED!! [taking photos simply and SO 17674634]
    public String writeInternalImageFile(Bitmap b) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        Context context = getApplicationContext();
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(imageFileName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return imageFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UNTESTED!!
    public Bitmap readInternalImageFile(String fname)
    {
        Context context = getApplicationContext();
        try {
            FileInputStream fis;
            fis = context.openFileInput(fname);
            Bitmap b = BitmapFactory.decodeStream(fis);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
