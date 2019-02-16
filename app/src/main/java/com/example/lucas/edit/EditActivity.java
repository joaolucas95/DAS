package com.example.lucas.edit;

import com.example.lucas.main.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setUiComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_undo:
                //TODO
                Toast.makeText(this, "undo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_redo:
                //TODO
                Toast.makeText(this, "redo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_save:
                //TODO
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUiComponents() {
        // TODO
    }
}