package com.example.ajithtasknewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ajithtasknewsapp.Dao.Article;
import com.example.ajithtasknewsapp.Dao.News;
import com.example.ajithtasknewsapp.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private NewsRecyclerAdapter mNewsRecyclerAdapter;
    List<Article> articleList=new ArrayList<>();
    @BindView(R.id.news_recycler)
    RecyclerView newsRecyclerView;
    @BindView(R.id.loader)
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        progressBar.setVisibility(View.VISIBLE);
        getFilterObservable().subscribe(new FilterDetailsObserver(FilterDetailsObserver.MODE_LOAD));
    }


    private Observable<News> getFilterObservable() {
        return HttpManager
                .getInstance()
                .getService()
                .getNewsData("google-news","45c3cda6320b4acf9f3be1bacf368ab4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class FilterDetailsObserver implements Observer<News>, NewsRecyclerAdapter.ItemClickListener {

        public static final int MODE_LOAD = 1;
        public static final int MODE_LOAD_MORE = 2;

        private int mode;

        public FilterDetailsObserver(int mode) {
            this.mode = mode;
        }

        @Override
        public void onCompleted() {
//
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            Log.e("DiscoverFragment", e.getMessage());
            progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onNext(final News news ) {

            List<Article> results =new ArrayList<>();
            results=news.getArticles();

            if (mode == MODE_LOAD) {

            }

//
            mNewsRecyclerAdapter=new NewsRecyclerAdapter(MainActivity.this,results);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
            newsRecyclerView.setLayoutManager(mLayoutManager);
            newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            newsRecyclerView.setAdapter(mNewsRecyclerAdapter);
            mNewsRecyclerAdapter.setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(String fullnewsUrl) {

//            Toast.makeText(MainActivity.this, ""+fullnewsUrl, Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullnewsUrl));
                startActivity(myIntent);
        }
    }
}
