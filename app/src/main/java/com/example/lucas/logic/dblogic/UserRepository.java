package com.example.lucas.logic.dblogic;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;


    public UserRepository(Application application) {
        FileHistoryRoomDatabase db = FileHistoryRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return mUserDao.getAllUsers();
    }

    public List<User> findAllUsers() {
        return mUserDao.findAllUsers();
    }

    public User findUserByUsername(String username){ return mUserDao.findUserByUsername(username); }

    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {

            //verify if does not exist an user with that username
            if(mAsyncTaskDao.findUserByUsername(params[0].username) != null)
                return null;

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
