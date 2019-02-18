package com.example.lucas.logic.dblogic;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

    /*


    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback(){

            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;

        PopulateDbAsync(FileHistoryRoomDatabase db) {
            mDao = db.userDao();
        }


        @Override
        protected Void doInBackground(final Void... params) {
            /*
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);

            return null;
        }
    }
    */
}
