package com.example.lucas.logic.dblogic;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table")
    List<User> findAllUsers();

    @Query("SELECT * from user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * from user_table where username = :username")
    User findUserByUsername(String username);


}
