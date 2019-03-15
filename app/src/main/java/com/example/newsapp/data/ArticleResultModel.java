package com.example.newsapp.data;

import java.util.List;

public class ArticleResultModel extends BaseModel {
    private int totalResults;
    private List<ArticleModel> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModel> articles) {
        this.articles = articles;
    }
}
