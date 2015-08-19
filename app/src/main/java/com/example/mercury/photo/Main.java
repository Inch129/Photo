package com.example.mercury.photo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main extends ActionBarActivity {
    private static final int REQUEST_OF_CAMERA = 17;
    ImageView img;
    Button takePicture;
    AlertDialog.Builder builder1;
    static final String LOG = "myLogs";
    static Bitmap picture;
    static EditText edit;
    static FileOutputStream out;
    MyDialog dialog;
    private static AsyncTask task = null;

    private void findId() {
        Log.d(LOG, "findId");
        img = (ImageView) findViewById(R.id.imageView);
        takePicture = (Button) findViewById(R.id.take_picture);
        builder1 = new AlertDialog.Builder(getBaseContext());
        takePicture.setOnClickListener(new takePictureClicker());
        edit = (EditText) findViewById(R.id.editText);
    }


    public static void saveFile(final String filaName) {
        if (task != null) {
            task.cancel(true);
        }
        task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(LOG, "save button click event");
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                Log.w("LOG", "root = " + root);
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                String fname = filaName + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists())
                    file.delete();
                try {
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    picture.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//                    File f = new File(fname);
//                    f.createNewFile();
//                    FileOutputStream fo = new FileOutputStream(f);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
                    out = new FileOutputStream(file);
                    picture.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    try {
//                        if (out != null)
//                            out.flush();
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }

                return null;
            }
        };
        task.execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dialog = new MyDialog();
        edit = new EditText(getApplicationContext());
        findId();


    }


    class takePictureClicker implements Button.OnClickListener {
        //обработка кажатия на take picture
        @Override
        public void onClick(View v) {
            Log.d(LOG, "Camera is called");
            Intent toCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(toCamera, REQUEST_OF_CAMERA);

        }
    }

    //Вывод фотографии
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG, "onActivityResult");
        if (requestCode == REQUEST_OF_CAMERA) {

            picture = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(picture);
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG, "onCreateOptMenu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG, "onOptionsItemSelected");

        dialog.show(getSupportFragmentManager(), MyDialog.TAG);

        edit = (EditText) findViewById(R.id.editText);

        return super.onOptionsItemSelected(item);
    }
}

