package com.example.newsapp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.newsapp.data.SourceModel;
import java.util.List;

@Dao
public interface SourceDao {
    @Query("SELECT * FROM Sources")
    List<SourceModel> getSources();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSource(SourceModel source);

    @Query("DELETE FROM Sources")
    void deleteAllSources();
}
