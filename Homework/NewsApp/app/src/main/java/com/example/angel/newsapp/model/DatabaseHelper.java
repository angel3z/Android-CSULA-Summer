package com.example.angel.newsapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by angel on 7/23/17.

 * Implemented the Database Helper which extends the SQLiteOpenHelper, I added the appropriate
    values for the DataBase

 * 3) 10pts: Create a contract, subclass SQLiteOpenHelper,
 modify your app so that your network call stores the data for your news stories in the database
 (you decide column and table names).
 */

public class DatabaseHelper extends SQLiteOpenHelper {


        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "newsArticleDataBase.db";
        private static final String TAG = "dataBaseHelper";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "Create hello");

            String queryString = "CREATE TABLE " + Contract.TABLE_NEWS_ARTICLES.TABLE_NAME + " ("+
                    Contract.TABLE_NEWS_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_PUBLISHED_DATE + " DATE, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_THUMBURL + " TEXT, " +
                    Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_URL + " TEXT" +
                    "); ";

            Log.d(TAG, "Create the table SQL: " + queryString);
            db.execSQL(queryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         //  db.execSQL("drop table " + Contract.TABLE_NEWS_ARTICLES.TABLE_NAME + " if exists;");
        }
    }

