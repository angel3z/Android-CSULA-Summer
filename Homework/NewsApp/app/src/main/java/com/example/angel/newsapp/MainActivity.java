package com.example.angel.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    private TextView newsTextView;
    private ProgressBar load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsTextView = (TextView) findViewById(R.id.news_data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflates = getMenuInflater();
        inflates.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        load = (ProgressBar)findViewById(R.id.progressBar);
        load.setVisibility(View.VISIBLE);
        if (itemSelected == R.id.search){

            newsTextView.setText("");
            load.setVisibility(View.VISIBLE);




        }
        useNewsData();
        //load.setVisibility(View.GONE);

        return super.onOptionsItemSelected(item);
    }

    //  Create a class that extends AsyncTask to perform network requests
    public class GetNewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... parameters) {

            URL newsRequestUrl = NetworkUtils.buildUrl();

            try {
                String newsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);
                return newsResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String newsData) {
            load.setVisibility(View.GONE);
            newsTextView.setText(newsData);

        }
    }


    private void useNewsData() {
        new GetNewsTask().execute();
    }

}
