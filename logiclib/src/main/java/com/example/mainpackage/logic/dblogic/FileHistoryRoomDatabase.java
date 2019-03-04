package com.example.mainpackage.logic.dblogic;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, FilePath.class}, version = 1)
public abstract class FileHistoryRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract FilePathDao filePathDao();

    private static volatile FileHistoryRoomDatabase INSTANCE;

    static FileHistoryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FileHistoryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, FileHistoryRoomDatabase.class, "file_history_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
