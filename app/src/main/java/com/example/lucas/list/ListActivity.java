package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.logic.LogicController;
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


        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(this).get(FileHistoryViewModel.class);

        mFileHistoryViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d("test", "------ All users");
                for(User user : users)
                    Log.d("test", String.valueOf(user));
                Log.d("test", "---------");
            }
        });

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