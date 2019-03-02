package com.example.lucas.list;

import com.example.lucas.main.R;
import com.example.lucas.simulation.SimulationActivity;
import com.example.lucas.test.TestActivity;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.utils.Config;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.MyViewHolder> {

    private List<FilePath> filePathList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }

    }


    public ProjectsListAdapter(List<FilePath> filePathList, Context context) {
        this.filePathList = filePathList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final FilePath filePath = filePathList.get(position);
        holder.title.setText(filePath.getProjectName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "" + filePath.toString());

                String[] items = {"Simulation", "Tests", "Open HTML", "Remove Project"};

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.options)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = null;
                                switch (which) {
                                    case 0:
                                        intent = new Intent(context, SimulationActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("filePath", filePath);
                                        context.startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(context, TestActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("filePath", filePath);
                                        context.startActivity(intent);
                                        break;
                                    case 2:
                                        try {
                                            String path = Config.BASE_FILE_PATH + filePath.getProjectName() + ".html";
                                            Log.d("test", "HTML file path:" + path);
                                            Uri uri = Uri.fromFile(new java.io.File(path));
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                            browserIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                                            browserIntent.setData(uri);
                                            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(browserIntent);
                                        } catch (Exception e) {
                                            Toast.makeText(context, "Need to create and save simulations or tests first.", Toast.LENGTH_SHORT).show();
                                        }

                                        break;
                                    case 3:

                                        /*
                                        FilePath filePathTmp = mFileHistoryViewModel.findFilePathEntityByProjectName(filePath.projectName);
                                        if (filePathTmp != null) {
                                            try {
                                                mFileHistoryViewModel.deleteFilePath(filePathTmp);
                                                LogicController.getInstance().getFacade().removeProject(filePathTmp.filePath);
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                         */

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
