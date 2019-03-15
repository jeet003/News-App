package com.example.newsapp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.newsapp.data.ArticleModel;

@Database(entities = {ArticleModel.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {
    private static ArticleDatabase INSTANCE;

    public abstract ArticleDao articleDao();

    public static ArticleDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ArticleDatabase.class, "Articles.db")
                    .build();
        }
        return INSTANCE;
    }
}
