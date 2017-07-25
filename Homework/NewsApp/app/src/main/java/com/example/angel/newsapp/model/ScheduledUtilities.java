package com.example.angel.newsapp.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.angel.newsapp.NewsJob;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;


/**
 * Created by angel on 7/23/17.
 */
// Implemented this class so that it loads the data every minute to the database
//5) 10pts: Using Firebase's JobDispatcher,
// modify your app so that it loads new news information every minute.


public class ScheduledUtilities {

        private static final int INTERVAL_MINUTE = 60;
        private static final int START = 0;
        private static final String NEWS_TAG = "news_tag";

        private static boolean sInitialized;

        synchronized public static void scheduleRefresh(@NonNull final Context context){
            if(sInitialized) return;

            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            Job constraintRefreshJob = dispatcher.newJobBuilder()
                    .setService(NewsJob.class)
                    .setTag(NEWS_TAG)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(START,INTERVAL_MINUTE))
                    .setReplaceCurrent(true)
                    .build();

            dispatcher.schedule(constraintRefreshJob);
            sInitialized = true;

        }

    }

