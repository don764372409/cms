package com.minicms.util;

import java.io.File;

public class FileUtil {
	public static void deleteAllFiles(File f) {  
        if(f.exists()) {  
            File[] files = f.listFiles();  
            if(files != null) {  
                for(File file : files)  
                    if(file.isDirectory()) {  
                        deleteAllFiles(file);  
                        file.delete(); //删除目录下的所有文件后，该目录变成了空目录，可直接删除  
                    }  
                    else if(file.isFile()) {  
                        file.delete();  
                    }  
            }  
            f.delete(); //删除最外层的目录  
        }  
    }  
}
