package com.example.angel.newsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.angel.newsapp.model.NewsItem;

import java.util.ArrayList;


// Followed Class Example
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{

    private ArrayList<NewsItem> data;
    ItemClickListener listener;


    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
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

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView date;
        TextView url;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.news_title);
            description = (TextView)view.findViewById(R.id.news_description);
            date = (TextView)view.findViewById(R.id.news_date);
            url = (TextView)view.findViewById(R.id.news_url);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            NewsItem news = data.get(pos);
            title.setText(news.getTitle());
            description.setText(news.getDescription());
            date.setText(news.getDate());
            url.setText(news.getUrl());

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }



}