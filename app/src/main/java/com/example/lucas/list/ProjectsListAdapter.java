package com.example.lucas.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.Project;

import java.util.List;
public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.MyViewHolder> {

    private List<FilePath> filePathList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public ProjectsListAdapter(List<FilePath> filePathList) {
        this.filePathList = filePathList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FilePath filePath= filePathList.get(position);
        holder.title.setText(filePath.getProjectName());
    }

    @Override
    public int getItemCount() {
        return filePathList.size();
    }
}
