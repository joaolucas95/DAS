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

    public String projectName;
    public String filePath;

    @ColumnInfo(name = "user_id")
    public int userId;

    public FilePath(String projectName, String filePath, int userId) {
        this.projectName = projectName;
        this.filePath = filePath;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FilePath{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", userId=" + userId +
                '}';
    }
}
