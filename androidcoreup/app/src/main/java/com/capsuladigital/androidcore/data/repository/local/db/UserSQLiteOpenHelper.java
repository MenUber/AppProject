package com.capsuladigital.androidcore.data.repository.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lvert on 5/03/2018.
 */

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

    private String sqlTeamsCreate="create table TEAMS(ID integer primary key, NAME text, ICON text)";

    public UserSQLiteOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, dbName, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       sqLiteDatabase.execSQL(sqlTeamsCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists TEAMS");
        sqLiteDatabase.execSQL(sqlTeamsCreate);

    }
}
