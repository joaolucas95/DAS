package com.example.mainpackage.logic.project.filemanagement;

import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private FileUtils() {
        // Do nothing
    }

    public static List<FileType> getFileTypes() {
        FileType[] fileTypes = FileType.values();
        return Arrays.asList(fileTypes);
    }

    public static String getFileTypeName(FileType type) {
        switch (type) {
            case BINARY:
                return "Binary";

            case BLIF:
                return "Blif";

            default:
                throw new IllegalStateException("illegal type:" + type);
        }
    }
}