package com.example.angel.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.angel.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;



public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String NEWS_URL =
            "https://newsapi.org/v1/articles";

    private static final String NEWS_BASE_URL = NEWS_URL;

    /* The format we want our API to return */
    private static final String source = "the-next-web";
    private static final String time = "latest";


    final static String SOURCE_PARAM = "source";
    final static String SORT_BY = "sortBy";
    final static String API ="apiKey";
    final static String API_KEY = "f6f1bbe646eb4043bd502a39a715aa96";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, source)
                .appendQueryParameter(SORT_BY,time)
                .appendQueryParameter(API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built URI " + url);


        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
// Added Method to Parse the Data
    public static ArrayList<NewsItem> jsonParsed(String json) throws JSONException{
        ArrayList<NewsItem> data = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");

        for (int i = 0; i < items.length(); i++){
            JSONObject articles = items.getJSONObject(i);
            String name = articles.getString("author");
            String title = articles.getString("title");
            String description = articles.getString("description");
            String url = articles.getString("url");
            String date = articles.getString("publishedAt");

            NewsItem news = new NewsItem(name,title,description,date,url);
            data.add(news);


        }
        return data;

    }




}
