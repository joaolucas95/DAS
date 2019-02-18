package com.example.lucas.logic.dblogic;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FilePathRepository {
    private FilePathDao mFilePathDao;


    public FilePathRepository(Application application) {
        FileHistoryRoomDatabase db = FileHistoryRoomDatabase.getDatabase(application);
        this.mFilePathDao = db.filePathDao();
    }

    public LiveData<List<FilePath>> getAllFilesPath() { return mFilePathDao.getAllFilesPath(); }

    public void insert (FilePath filePath) {
        new FilePathRepository.insertAsyncTask(mFilePathDao).execute(filePath);
    }

    private static class insertAsyncTask extends AsyncTask<FilePath, Void, Void> {

        private FilePathDao mAsyncTaskDao;

        insertAsyncTask(FilePathDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FilePath... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
