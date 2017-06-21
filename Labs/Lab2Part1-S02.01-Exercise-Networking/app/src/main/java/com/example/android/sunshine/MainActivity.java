/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        // FINISHED (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.


        // FINISHED (3) Delete the for loop that populates the TextView with dummy data


        // FINISHED (9) Call loadWeatherData to perform the network request to get the weather
        useWeatherData();
    }


    // FINISHED (5) Create a class that extends AsyncTask to perform network requests
    public class GetWeatherTask extends AsyncTask<String, Void, String[]> {
        // FINISHED (6) Override the doInBackground method to perform your network requests
        // Note:The three periods after the final parameter's type indicate that the final argument
// may be passed as an array or as a sequence of arguments.
// Varargs can be used only in the final argument position.
        @Override
        protected String[] doInBackground(String... parameters) {

//            Checking if the parameters has a length that is not zero to continue
            if (parameters.length == 0) {
                return null;
            }

            String location = parameters[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] easyJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return easyJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        // FINISHED (7) Override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String[] weatherData) {
            if (weatherData != null) {

                  //Go through the array and append the Strings to the TextView.
                 //The reason why we use the "\n\n\n" after the String is to make it look good
                // between each String in the TextView
                for (String w : weatherData) {
                    mWeatherTextView.append((w) + "\n\n\n");
                }
            }
        }
    }

    // FINISHED (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData

    private void useWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new GetWeatherTask().execute(location);
    }

}
