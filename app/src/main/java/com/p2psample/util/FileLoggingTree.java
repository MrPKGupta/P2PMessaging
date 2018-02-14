package com.p2psample.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Purushottam Gupta on 2/13/2018.
 */

public class FileLoggingTree extends Timber.DebugTree {
    private Context context;

    public FileLoggingTree(Context context) {
        this.context = context;
    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        super.log(priority, tag, message, t);
        FileOutputStream fileOutputStream;
        try {
            String logTimeStamp = new SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa", Locale.getDefault())
                    .format(new Date());
            fileOutputStream = context.openFileOutput(Constants.LOG_FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(("<p style=\"background:lightgray;\"><strong style=\"background:lightblue;\">&nbsp&nbsp"
                    + logTimeStamp + " :&nbsp&nbsp</strong>&nbsp&nbsp" + message + "</p>").getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}