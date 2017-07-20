package com.sargent.mark.todolist.data;

import android.provider.BaseColumns;

/**
 * Created by mark on 7/4/17.
 */

public class Contract {
// Added Values to the Contract such as Category and the Completed state
    public static class TABLE_TODO implements BaseColumns{
        public static final String TABLE_NAME = "todoitems";

        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";

        public static final String COLUMN_CATEGORY = "category";
        public static final int COLUMN_COMPLETED = 0;
    }
}
