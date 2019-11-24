package com.example.ajithtasknewsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ajithtasknewsapp.Dao.Article;
import com.example.ajithtasknewsapp.http.Contextor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {

    List<Article> articleList;
    Context ctx;
    ItemClickListener itemClickListener;


    public NewsRecyclerAdapter(Context ctx,List<Article> articleList1) {
        this.articleList = articleList1;
        this.ctx = ctx;
    }

    public interface ItemClickListener {

        public void onItemClick(String newsUrl);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(ctx)
                .inflate(R.layout.news_single_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        if (articleList.get(i).getUrlToImage()!=null) {
            Glide.with(ctx).load(articleList.get(i).getUrlToImage())
                    .placeholder(R.drawable.placeholder)
                    .into(myViewHolder.ivDisplay);

        }

        if(articleList.get(i).getTitle()!=null && !articleList.get(i).getTitle().isEmpty()) {
            myViewHolder.tvTitle.setText(articleList.get(i).getTitle());
        }else {
            myViewHolder.tvTitle.setVisibility(View.GONE);

        }

        if(articleList.get(i).getDescription()!=null && !articleList.get(i).getDescription().isEmpty()) {
            myViewHolder.tvDescription.setText(articleList.get(i).getDescription());
        }else {
            myViewHolder.tvDescription.setVisibility(View.GONE);

        }
        if(articleList.get(i).getAuthor()!=null && !articleList.get(i).getAuthor().isEmpty()) {
            myViewHolder.tvAutor.setText(articleList.get(i).getAuthor());
        }else {
            myViewHolder.tvAutor.setVisibility(View.GONE);

        }
        if(articleList.get(i).getPublishedAt()!=null && !articleList.get(i).getPublishedAt().isEmpty()) {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy / HH:mm");

            Date d = null;
            try
            {
//                2019-11-23T01:29:00+00:00
                d = input.parse(articleList.get(i).getPublishedAt());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            String formatted = output.format(d);
            Log.i("DATE", "" + formatted);
            myViewHolder.tvTime.setText(formatted);
        }else {
            myViewHolder.tvTime.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvAutor)
        TextView tvAutor;
        @BindView(R.id.tvtime)
        TextView tvTime;
        @BindView(R.id.ivImage)
        ImageView ivDisplay;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(articleList.get(getLayoutPosition()).getUrl());
                    }
                }
            });
        }
    }
}
