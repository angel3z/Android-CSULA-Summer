package com.example.angel.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.angel.newsapp.model.Contract;
import com.example.angel.newsapp.model.DatabaseHelper;
import com.example.angel.newsapp.model.DatabaseUtility;
import com.example.angel.newsapp.model.NewsItem;
import com.example.angel.newsapp.model.ScheduledUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.data;
// Followed some of Mark's Android Class Examples
/**
 * Updated by angel on 7/23/17.
 */

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{

    static final String TAG = "mainactivity";
    private ProgressBar progress;
    private RecyclerView rv;

    //Added these values

    private static final int NEWS_LOADER = 1;

    private NewsAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;

    /**
     * Notes:
     * Interface for accessing and modifying preference data returned by getSharedPreferences(String, int).
     * For any particular set of preferences, there is a single instance of this class that all clients share.
     * Modifications to the preferences must go through an SharedPreferences.Editor object to ensure
     * the preference values remain in a consistent state and control when they are committed to storage.
     * Objects that are returned from the various get methods must be treated as immutable by the application.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView)findViewById(R.id.recyclerview_news);
        rv.setLayoutManager(new LinearLayoutManager(this));


// Added the shared Preferences, notes are above
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            useNewsData();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduledUtilities.scheduleRefresh(this);
    }

//7) 2pts: In onCreate, have your activity load what's currently in your database into the recyclerview for display.

    //6) 3pts: Have your activity check if the app has been installed before, if not, load data into your database using your network methods.



    private boolean appInstalled(String uri)
    {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean installed = appInstalled("com.example.angel.newsapp");
        if(installed)
        {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("com.example.angel.newsapp");
            startActivity(LaunchIntent);

            Log.d(TAG, "App is Installed");
            db = new DatabaseHelper(MainActivity.this).getReadableDatabase();
            cursor = DatabaseUtility.getAll(db);
            adapter = new NewsAdapter(cursor, this);
            rv.setAdapter(adapter);

        }
        else
        {
            Log.d(TAG, "App is Not Installed");
            db = new DatabaseHelper(MainActivity.this).getReadableDatabase();
            cursor = DatabaseUtility.getAll(db);
            adapter = new NewsAdapter(cursor, this);
            rv.setAdapter(adapter);

        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemNumber = item.getItemId();
//        progress.setVisibility(View.VISIBLE);
//        if (itemNumber == R.id.search) {
//            progress.setVisibility(View.VISIBLE);
//            rv.setAdapter(null);
//        }
//        useNewsData();

        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            rv.setAdapter(null);

        }
        useNewsData();
        return true;
    }

    //2) 10pts: Modify Homework 2 so that AsyncTask is replaced with AsyncTaskLoader,
    // implementing all the required callbacks.



    @Override
        public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                Refresh.refreshNewsArticles(MainActivity.this);
                return null;
            }

        };
    }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DatabaseHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtility.getAll(db);

        adapter = new NewsAdapter(cursor, this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public void onLoaderReset(Loader<Void> loader) {
    }
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));



        //String url = data.get(clickedItemIndex).getUrl();
        //Intent to open browser
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        progress.setVisibility(View.GONE);

    }

//    class GetNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress.setVisibility(View.VISIBLE);
//
//        }
//        @Override
//        protected ArrayList<NewsItem> doInBackground(String... params) {
//            ArrayList<NewsItem> result = null;
//            URL newsRequestUrl = NetworkUtils.buildUrl();
//            try {
//                String newsResponse = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);
//                result = NetworkUtils.jsonParsed(newsResponse);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<NewsItem> data) {
//            super.onPostExecute(data);
//            progress.setVisibility(View.GONE);
//            if (data != null) {
//                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener() {
//                    @Override
//                    public void onItemClick(Cursor cursor, int clickedItemIndex) {
//                        String url = data.get(clickedItemIndex).getUrl();
//                        //Intent to open browser
//                        Uri webpage = Uri.parse(url);
//                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//                        if (intent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(intent);
//                        }
//                        Log.d(TAG, String.format("Url %s", url));
//
//                    }
//                });
//                rv.setAdapter(adapter);
//
//            }
//        }
//    }

    private void useNewsData(){
//        new GetNewsTask().execute();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

 }

}
