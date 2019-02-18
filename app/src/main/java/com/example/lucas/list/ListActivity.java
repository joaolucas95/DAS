package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.logic.LogicController;
import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.logic.dblogic.User;
import com.example.lucas.logic.dblogic.FileHistoryViewModel;
import com.example.lucas.main.R;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setUiComponents();


        final FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(this).get(FileHistoryViewModel.class);

/*
        //
        //  example - add file path to user
        //
        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);
        FilePath filePath = new FilePath("projetoxpt" , user.id);
        mFileHistoryViewModel.insertFilePath(filePath);

        //
        //  example - remove file path
        //
        //FilePath filePath = mFileHistoryViewModel.findFilePathEntityByFilePath("projetoxpt");
        //if(filePath != null)
            //mFileHistoryViewModel.deleteFilePath(filePath);
*/
        //just a test
        mFileHistoryViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

                Log.d("test", "------ All users");
                for(User user : users)
                {
                    Log.d("test", String.valueOf(user));
                    Log.d("test", "FilePath list:\n");

                    for(FilePath filePath : mFileHistoryViewModel.findAllFilesPathOfUser(user.id))
                        Log.d("test", String.valueOf(filePath));
                }
                Log.d("test", "---------");
            }
        });

        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);
        if(user != null)
        {
            mFileHistoryViewModel.getAllFilesPathOfUser(user.id).observe(this, new Observer<List<FilePath>>() {
                @Override
                public void onChanged(@Nullable List<FilePath> filePaths) {
                    //TODO: add data of this list to adapter
                }
            });
        }

    }

    private void setUiComponents() {
        setupToolbar();
        setNewButton();
    }

    private void setupToolbar() {
        String username = LogicController.getInstance().getFacade().getCurrentUsername();

        String helloMessage = getString(R.string.hello_message, username);

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(helloMessage);
    }

    private void setNewButton() {
        findViewById(R.id.btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, EditActivity.class));

            }
        });
    }
}