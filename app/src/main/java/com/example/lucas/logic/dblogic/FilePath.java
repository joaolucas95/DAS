package com.example.lucas.logic.dblogic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "filepath_table", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "user_id"))
public class FilePath {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String filePath;

    @ColumnInfo(name = "user_id")
    public int userId;
}
