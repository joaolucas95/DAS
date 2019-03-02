package com.example.mainpackage.logic.dblogic;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FilePathDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FilePath filePath);

    @Query("DELETE FROM filepath_table")
    void deleteAll();

    @Query("SELECT * from filepath_table")
    List<FilePath> findAllFilesPath();

    @Query("SELECT * from filepath_table")
    LiveData<List<FilePath>> getAllFilesPath();

    @Query("SELECT * from filepath_table where user_id = :user_id")
    List<FilePath> findAllFilesPathOfUser(int user_id);

    @Query("SELECT * from filepath_table where user_id = :user_id")
    LiveData<List<FilePath>> getAllFilesPathOfUser(int user_id);

    @Query("DELETE FROM filepath_table where id = :filepath_id")
    void deleteById(int filepath_id);

    @Query("SELECT * from filepath_table where projectName = :projectName")
    FilePath findFilePathEntityByProjectName(String projectName);
}
