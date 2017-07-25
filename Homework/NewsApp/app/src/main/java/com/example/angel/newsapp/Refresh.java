package com.example.angel.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.angel.newsapp.model.DatabaseHelper;
import com.example.angel.newsapp.model.DatabaseUtility;
import com.example.angel.newsapp.model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by angel on 7/23/17.
 */

public class Refresh {

// Implemented this class to refresh the News Articles, the News Job class uses this class to
    // refresh in the background method

        public static void refreshNewsArticles(Context context) {
            ArrayList<NewsItem> news = null;

            URL url = NetworkUtils.buildUrl();

            SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

            try {
                DatabaseUtility.deleteAll(db);
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                news = NetworkUtils.jsonParsed(json);
                DatabaseUtility.bulkInsert(db, news);

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            db.close();
        }
    }

