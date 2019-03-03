package com.example.lucas.edit.choose;

import com.example.lucas.edit.EditUtils;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;

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
                handleOnModuleChosen(filePath.getFilePath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilePathList.size();
    }

    private void handleOnModuleChosen(String path) {
        mActivity.setResult(EditUtils.REQUEST_CODE_CHOOSE_MODULE);
        Intent result = new Intent();

        result.putExtra(EditUtils.EXTRA_MODULE, path);

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