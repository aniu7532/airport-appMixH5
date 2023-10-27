package com.tencent.shadow.sample.plugin.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
                LogUtils.logD("文件创建成功");
            } else {
                LogUtils.logD("文件创建失败");
            }
        }
        // 用日期作为文件名，确保唯一性
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);
        String fileName = saveDir + "/" + formatter.format(date) + ".png";

        return fileName;
    }
}
