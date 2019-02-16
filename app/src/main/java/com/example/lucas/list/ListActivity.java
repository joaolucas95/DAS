package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setUiComponents();
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