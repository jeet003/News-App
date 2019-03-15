package com.example.newsapp.screen.article;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.ViewModelFactory;
import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.data.SourceModel;
import com.example.newsapp.databinding.ActivityArticleBinding;
import com.example.newsapp.screen.article_detail.ArticleDetailActivity;
import com.example.newsapp.screen.source.SourceActivity;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    public static final String ARTICLE_EXTRA = "article-extra";

    private ArticleViewModel viewModel;
    private SourceModel source;

    private SearchView searchView;
    private Handler handler;
    String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityArticleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        viewModel = obtainViewModel(this);
        binding.setViewModel(viewModel);

        source = getIntent().getParcelableExtra(SourceActivity.SOURCE_EXTRA);

        setupToolbar();
        setupRecyclerView();
        setupSearchView();

        viewModel.start(source.getSourceId());
        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showSnackBar(s);
            }
        });
        viewModel.openArticleDetailEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Intent intent = new Intent(ArticleActivity.this, ArticleDetailActivity.class);
                intent.putExtra(ARTICLE_EXTRA, s);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private static ArticleViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ArticleViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(ArticleViewModel.class);

        return viewModel;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(source.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.article_recyclerview_articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        ArticleAdapter adapter = new ArticleAdapter(this, new ArrayList<ArticleModel>(), viewModel);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        handler = new Handler();
        searchView = findViewById(R.id.article_searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchQuery = s;
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.loadArticlesBySourceAndQuery(true, viewModel.sourceId.get(), searchQuery);
                    }
                }, 1000);
                return false;
            }
        });
    }

    private void showSnackBar(String message) {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.article_coordinatorlayout_container);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .show();
    }
}
