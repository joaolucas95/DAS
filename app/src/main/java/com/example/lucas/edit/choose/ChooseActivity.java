package com.example.lucas.edit.choose;

import com.example.lucas.list.ProjectsListAdapter;
import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.dblogic.User;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        setUiComponents();
    }

    private void setUiComponents() {
        setupToolbar();
        setRecyclerView();
    }

    private void setupToolbar() {
        String title = getString(R.string.choose_module);

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(title);
    }

    private void setRecyclerView() {
        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = LogicController.getInstance().getFacade().findUserByUsername(username, this);

        if (user != null) {
            LogicController.getInstance().getFacade().getAllFilesPathOfUser(user.id, this).observe(this, new Observer<List<FilePath>>() {
                @Override
                public void onChanged(@Nullable List<FilePath> filePaths) {

                    RecyclerView recyclerView = findViewById(R.id.recycler_view);

                    recyclerView.setHasFixedSize(true);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);

                    RecyclerView.Adapter adapter = new ProjectsListAdapter(filePaths, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }
}