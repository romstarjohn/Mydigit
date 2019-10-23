package com.project.android.finanzm.Interfaces;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.project.android.finanzm.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public final class Helper {
    private static final String TAG = "Helper";

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return null;
    }


    public static void setConfigValue(Context context, String name){
        Resources resources = context.getResources();

        try {
            Properties properties = new Properties();
            properties.setProperty(name, "1");
            File file = new File("config.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Favorite Things");
            fileOut.close();
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }
    }
}
