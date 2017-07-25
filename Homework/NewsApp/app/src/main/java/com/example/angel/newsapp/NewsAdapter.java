package com.example.angel.newsapp;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angel.newsapp.model.Contract;
import com.example.angel.newsapp.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


// Followed Class Example
// Updated this class to get the data from the database instead of the array list.
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{
    public static final String TAG = "newsAdapter";
    //private ArrayList<NewsItem> data;
    ItemClickListener listener;
    private Cursor cursor;
    private Context context;

// Updated the Constructor
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;

        View view = inflater.inflate(R.layout.news_layout, parent, shouldAttach);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }
// Updated this method
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

// Updated this class to add the Image thumbnail
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView date;
        TextView author;
        TextView url;
        ImageView image;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.news_title);
            description = (TextView)view.findViewById(R.id.news_description);
            date = (TextView)view.findViewById(R.id.news_date);
//            url = (TextView)view.findViewById(R.id.news_url);
            image = (ImageView) view.findViewById(R.id.news_image);
            author = (TextView) view.findViewById(R.id.news_author);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            //NewsItem news = data.get(pos);
            cursor.moveToPosition(pos);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));
            author.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_AUTHOR)));


            String image_url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS_ARTICLES.COLUMN_NAME_THUMBURL));
            Log.d(TAG, image_url);

            //Uses Picasso
            //8) 5pts: Use Picasso to load a thumbnail for each news item in the recycler view.


            if(image_url != null){
                Picasso.with(context)
                        .load(image_url)
                        .into(image);
            }
//            title.setText(news.getTitle());
//            description.setText(news.getDescription());
//            date.setText(news.getDate());
//            url.setText(news.getUrl());

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }



}