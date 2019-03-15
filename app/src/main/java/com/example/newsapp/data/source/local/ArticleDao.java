package com.example.newsapp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.newsapp.data.ArticleModel;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM Articles WHERE sourceId = :sourceId")
    List<ArticleModel> getArticlesBySource(String sourceId);

    @Query("SELECT * FROM Articles WHERE sourceId = :sourceId AND title LIKE :query")
    List<ArticleModel> getArticlesBySourceAndQuery(String sourceId, String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(ArticleModel article);

    @Query("DELETE FROM Articles WHERE sourceId = :sourceId")
    void deleteAllArticlesBySource(String sourceId);
}
