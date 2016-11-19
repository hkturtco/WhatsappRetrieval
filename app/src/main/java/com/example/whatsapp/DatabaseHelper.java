package com.example.whatsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Man on 10/27/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "msgstore.db";

    public DatabaseHelper(Context context)
    {
        super(new Databasecontext(context), DB_NAME, null, 3);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
