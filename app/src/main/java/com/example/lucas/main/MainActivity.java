package com.example.lucas.main;

import com.example.lucas.list.ListActivity;
import com.example.lucas.logic.LogicController;
import com.example.lucas.logic.dblogic.User;
import com.example.lucas.logic.dblogic.FileHistoryViewModel;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUiComponents();
    }

    private void setUiComponents() {
        mEditTextInput = findViewById(R.id.et_name);

        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnEnterClick();
            }
        });
    }

    private void handleOnEnterClick() {
        String input = mEditTextInput.getText().toString();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, R.string.invalid_username, Toast.LENGTH_SHORT).show();
            return;
        }


        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(this).get(FileHistoryViewModel.class);

        User usertmp = new User(input);
        mFileHistoryViewModel.insertUser(usertmp);

        LogicController.getInstance().getFacade().setCurrentUsername(input);
        startActivity(new Intent(MainActivity.this, ListActivity.class));
    }
}