package com.jz.algo.recursion;

import java.io.File;

public class getAllFIles {

    public static void main(String[] args) {
        File f = new File("zip");
        TraverseFolder(f);
    }

    public static void TraverseFolder(File file) {
        File files[] = file.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println("File-->: " + files[i].getAbsolutePath());
            } else {
                System.out.println("Dir-->: " + files[i].getAbsolutePath());
                TraverseFolder(files[i]);
            }

        }

    }

}
