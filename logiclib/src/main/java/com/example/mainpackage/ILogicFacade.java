package com.example.mainpackage;

import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.dblogic.User;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.filemanagement.FileType;

import android.arch.lifecycle.LiveData;
import android.support.v4.app.FragmentActivity;

import java.io.FileNotFoundException;
import java.util.List;

public interface ILogicFacade {

    void setCurrentUsername(String username);

    String getCurrentUsername();

    List<ComponentType> getComponentsTypes();

    String getComponentTypeName(ComponentType type);

    List<FileType> getFileTypes();

    String getFileTypeName(FileType type);

    Project getProject(String filePath) throws Exception;

    //is a new project when we are creating a new project... isn't a new project when we are just saving the tests or simulations
    boolean saveProject(Project project, boolean isNewProject, String filePath, FileType fileType);

    void removeProject(String filePath) throws FileNotFoundException;

    /* Edit actions */

    void newEdition(boolean isSimpleProject);

    List<Component> getActualComponents();

    void cancelConnection();

    void selectComponent(String name);

    void addComponent(ComponentType type, int[] pos);

    void addModule(String filePathProject, com.example.mainpackage.logic.user.User user, int[] position);

    void undoOperation();

    void redoOperation();

    /* Db access methods */

    User findUserByUsername(String username, FragmentActivity fragmentActivity);

    LiveData<List<FilePath>> getAllFilesPathOfUser(int user_id, FragmentActivity fragmentActivity);

    void deleteFilePath(FilePath filePath, FragmentActivity fragmentActivity);

    FilePath findFilePathEntityByProjectName(String projectName, FragmentActivity fragmentActivity);

    void insertFilePath(FilePath filePath, FragmentActivity fragmentActivity);
}