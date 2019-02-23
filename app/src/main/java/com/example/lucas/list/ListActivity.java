package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.logic.LogicController;
import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.logic.dblogic.User;
import com.example.lucas.logic.dblogic.FileHistoryViewModel;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.Project;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    private FileHistoryViewModel mFileHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.mFileHistoryViewModel = ViewModelProviders.of(this).get(FileHistoryViewModel.class);

        setUiComponents();


/*
        //
        //  example - add file path to user
        //
        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);
        FilePath filePath = new FilePath("projectxpto", "/projectxpto.bin" , user.id);
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
                    Log.d("test", "FilePath list of user:\n");

                    for(FilePath filePath : mFileHistoryViewModel.findAllFilesPathOfUser(user.id))
                        Log.d("test", String.valueOf(filePath));
                }
                Log.d("test", "---------");
            }
        });
    }

    private void setUiComponents() {
        setupToolbar();
        setNewButton();
        setRecyclerView();
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

                String username = LogicController.getInstance().getFacade().getCurrentUsername();
                User user = mFileHistoryViewModel.findUserByUsername(username);

                Random randomno = new Random();
                String projectName = "project" + randomno.nextInt();
                String filePathString = "/" + projectName + ".bin";

                FilePath filePath = new FilePath(projectName, filePathString , user.id);
                mFileHistoryViewModel.insertFilePath(filePath);

                startActivity(new Intent(ListActivity.this, EditActivity.class));
            }
        });
    }

    private void setRecyclerView() {

        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);

        if(user != null)
        {
            mFileHistoryViewModel.getAllFilesPathOfUser(user.id).observe(this, new Observer<List<FilePath>>() {
                @Override
                public void onChanged(@Nullable List<FilePath> filePaths) {

                    //Log.d("test", "projects of user: " + String.valueOf(filePaths));

                    RecyclerView recyclerView;
                    RecyclerView.Adapter mAdapter;
                    RecyclerView.LayoutManager layoutManager;

                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    recyclerView.setHasFixedSize(true);

                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);

                    // specify an adapter (see also next example)
                    mAdapter = new ProjectsListAdapter(filePaths);
                    recyclerView.setAdapter(mAdapter);
                }
            });
        }
    }
}