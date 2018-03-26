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
                        file.delete(); //ɾ��Ŀ¼�µ������ļ��󣬸�Ŀ¼����˿�Ŀ¼����ֱ��ɾ��  
                    }  
                    else if(file.isFile()) {  
                        file.delete();  
                    }  
            }  
            f.delete(); //ɾ��������Ŀ¼  
        }  
    }  
}
