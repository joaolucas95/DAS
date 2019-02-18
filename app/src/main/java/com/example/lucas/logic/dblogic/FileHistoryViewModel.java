package com.example.lucas.logic.dblogic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FileHistoryViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;


    public FileHistoryViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getAllUsers() { return mUserRepository.getAllUsers(); }

    public List<User> findAllUsers() { return mUserRepository.findAllUsers(); }


    public void insertUser(User user) { mUserRepository.insert(user); }
}
