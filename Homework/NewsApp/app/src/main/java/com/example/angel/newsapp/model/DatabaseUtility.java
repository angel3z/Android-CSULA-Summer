package com.example.angel.newsapp.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import static com.example.angel.newsapp.model.Contract.TABLE_NEWS_ARTICLES.*;
/**
 * Created by angel on 7/23/17.
 */
// Implemented the Database Utility class to put all the values into the Database
public class DatabaseUtility {

        public static Cursor getAll(SQLiteDatabase db) {
            Cursor cursor = db.query(
                    TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COLUMN_NAME_PUBLISHED_DATE
                            + " DESC"
            );
            return cursor;
        }

        public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> news_articles) {

            db.beginTransaction();
            try {
                for (NewsItem news : news_articles) {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME_AUTHOR, news.getAuthor());
                    cv.put(COLUMN_NAME_TITLE, news.getTitle());
                    cv.put(COLUMN_NAME_DESCRIPTION,news.getDescription());
                    cv.put(COLUMN_NAME_PUBLISHED_DATE, news.getDate());
                    cv.put(COLUMN_NAME_URL, news.getUrl());
                    cv.put(COLUMN_NAME_THUMBURL, news.getImage());
                    db.insert(TABLE_NAME, null, cv);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }

        public static void deleteAll(SQLiteDatabase db) {
            db.delete(TABLE_NAME, null, null);
        }

    }


