package com.example.lucas.edit.choose;

import com.example.lucas.edit.EditUtils;
import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.ViewHolder> {

    private List<FilePath> mFilePathList;
    private ChooseActivity mActivity;

    ChooseListAdapter(List<FilePath> filePathList, ChooseActivity activity) {
        mFilePathList = filePathList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ChooseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_row, parent, false);

        return new ChooseListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChooseListAdapter.ViewHolder holder, int position) {
        final FilePath filePath = mFilePathList.get(position);
        holder.title.setText(filePath.getProjectName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = filePath.getFilePath();

                try {
                    Project project = LogicController.getInstance().getFacade().getProject(path);
                    handleOnModuleChosen(project.getComponentModule());

                } catch (Exception e) {
                    throw new IllegalStateException("Failed to obtain file. path=" + path);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilePathList.size();
    }

    private void handleOnModuleChosen(Component module) {
        mActivity.setResult(EditUtils.REQUEST_CODE_CHOOSE_MODULE);
        Intent result = new Intent();

        result.putExtra(EditUtils.EXTRA_MODULE, module);

        mActivity.setResult(Activity.RESULT_OK, result);
        mActivity.finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }
}