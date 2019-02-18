package com.example.lucas.logic.dblogic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FileHistoryViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;
    private FilePathRepository mFilePathRepository;


    public FileHistoryViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mFilePathRepository = new FilePathRepository(application);
    }

    //methos of user management
    public LiveData<List<User>> getAllUsers() { return mUserRepository.getAllUsers(); }
    public List<User> findAllUsers() { return mUserRepository.findAllUsers(); }
    public void insertUser(User user) { mUserRepository.insert(user);}
    public User findUserByUsername(String username){ return mUserRepository.findUserByUsername(username); }


    //methos of filePath management
    public LiveData<List<FilePath>> getAllFilesPath() { return mFilePathRepository.getAllFilesPath(); }
    public void insertFilePath(FilePath filePath) { mFilePathRepository.insert(filePath);}
    public List<FilePath> findAllFilesPathOfUser(int user_id) { return mFilePathRepository.findAllFilesPathOfUser(user_id);}
    public LiveData<List<FilePath>> getAllFilesPathOfUser(int user_id) { return mFilePathRepository.getAllFilesPathOfUser(user_id); }
    public void deleteFilePath(FilePath filePath) { mFilePathRepository.delete(filePath);}
    public FilePath findFilePathEntityByFilePath(String filePath) { return mFilePathRepository.findFilePathEntityByFilePath(filePath);}
}
