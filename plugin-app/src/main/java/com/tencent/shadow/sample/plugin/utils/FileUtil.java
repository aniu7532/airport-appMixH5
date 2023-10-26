package com.tencent.shadow.sample.plugin.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    /**
     * 生成文件路径和文件名 Environment
     *
     * @return
     */
    public static String getFileName(Context context) {
        String saveDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File dir = new File(saveDir);
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdirs();
            if (isSuccess) {
               // Toast.makeText(MainActivity.this, "文件夹创建成功", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(MainActivity.this, "文件夹创建失败", Toast.LENGTH_LONG).show();
            }
        }
        // 用日期作为文件名，确保唯一性
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = saveDir + "/" + formatter.format(date) + ".png";

        return fileName;
    }
}
