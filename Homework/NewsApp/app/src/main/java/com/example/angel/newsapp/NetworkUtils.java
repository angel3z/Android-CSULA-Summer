package com.example.angel.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    final static String API_KEY = "Insert key here";

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


}
