package com.example.lucas.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.main.R;
import com.example.lucas.simulation.SimulationActivity;
import com.example.mainpackage.logic.project.Project;

import java.util.List;
public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.MyViewHolder> {

    private List<FilePath> filePathList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }

    }


    public ProjectsListAdapter(List<FilePath> filePathList, Context context) {
        this.filePathList = filePathList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final FilePath filePath= filePathList.get(position);
        holder.title.setText(filePath.getProjectName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "" + filePath.toString());

                String[] items = {"Edit Project", "Simulation", "Tests", "Remove Project"};

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.options)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which){
                                    case 0:
                                        break;
                                    case 1:
                                        Intent intent = new Intent(context, SimulationActivity.class);
                                        intent.putExtra("filePath", filePath);
                                        context.startActivity(intent);
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                }
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filePathList.size();
    }
}
