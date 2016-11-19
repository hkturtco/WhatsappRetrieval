package com.example.whatsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Runtime rt = Runtime.getRuntime();
        Process proc;
        try {
            proc = rt.exec(new String[] { "su", "-c", "chmod 777 " + "./data/data/com.whatsapp/databases/msgstore.db" });
            proc.waitFor();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    try {
                        File sd = Environment.getExternalStorageDirectory();
                        File data = Environment.getDataDirectory();

                        if (sd.canWrite()) {
                            String currentDBPath = "/data/com.whatsapp/databases/msgstore.db";
                            String backupDBPath = "msgstore.db";
                            File currentDB = new File(data, currentDBPath);
                            File backupDB = new File(sd, backupDBPath);

                            if (currentDB.exists()) {
                                FileChannel src = new FileInputStream(currentDB).getChannel();
                                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                dst.transferFrom(src, 0, src.size());
                                src.close();
                                dst.close();
                            }
                        }
                    } catch (Exception e1) {
                    }
                    String data = "cannot found db";
                    try {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        //db.openDataBase();
                        data = null;

                        SQLiteDatabase newdb = db.getWritableDatabase();
                        Cursor c = newdb.rawQuery("SELECT data FROM messages WHERE data!=''", null);


                        if (c != null) {
                            if (c.moveToFirst()) {
                                do {
                                    data = c.getString(c.getColumnIndex("data"));
                                    // results.add(data); //adding to arrayList
                                }
                                while (c.moveToNext());
                            }
                        }
                        newdb.close();
                    }catch(Exception e2){
                        data = "cannot found db";
                    }

                    text = (TextView) findViewById(R.id.textView);
                    text.setText(data);
                }

            }, 10000);

        } catch (Exception e){ //DOSTUFFS
             }



    }
}




