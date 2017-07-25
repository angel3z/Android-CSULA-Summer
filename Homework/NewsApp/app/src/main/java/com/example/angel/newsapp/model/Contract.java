package com.example.angel.newsapp.model;

import android.provider.BaseColumns;

/**
 * Created by angel on 7/23/17.
 */
//Implemented the Contract class with the appropriate values
    //3) 10pts: Create a contract
public class Contract {
    public static class TABLE_NEWS_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "news_articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_THUMBURL = "news_thumb_url";
        public static final String COLUMN_NAME_URL = "url";
    }
}
