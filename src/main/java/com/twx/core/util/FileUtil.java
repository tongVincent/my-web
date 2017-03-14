package com.twx.core.util;

import java.io.File;

/**
 * Created by vincent.tong on 2016/8/3.
 */
public abstract class FileUtil {

    public static String getPostfix(String fileName) {
        if (fileName == null) {
            return "";
        }

        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            return fileName.substring(index + 1);
        } else {
            return "";
        }
    }

    public static boolean exists(String path) {
        return StringUtil.hasText(path) && new File(path).exists();
    }
}
