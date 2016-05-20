package org.lisp.ecl;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EclApplication extends Application {
    private static String TAG = "EclApplication";
    private static String RESOURCES_DIR = "lisp";
    private static String APP_RESOURCES_DIR = "resources";

    private static AssetManager assetManager;
    private static File uncompressedFilesDir;
    public static FileReader outputSource;

    @Override
    public void onCreate() {
        super.onCreate();
        assetManager = getAssets();

        SharedPreferences settings = getSharedPreferences("inferior", MODE_PRIVATE);
        boolean assetsUncompressed = settings.getBoolean("assetsUncompressed", false);
        uncompressedFilesDir = getDir(APP_RESOURCES_DIR, MODE_PRIVATE);

        if (!assetsUncompressed) {
            uncompressDir(RESOURCES_DIR, uncompressedFilesDir);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("assetsUncompressed", true);
            editor.commit();
        }

        EmbeddedCommonLisp.setup(EclApplication.getResourcesPath());

        try {
            outputSource = new FileReader(getResourcesPath() + "/ecl_output");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getResourcesPath() {
        return uncompressedFilesDir.getAbsolutePath();
    }

    private void uncompressDir(String in, File out) {
        try {
            String[] files = assetManager.list(in);
            Log.w(TAG, "Uncompressing: " + files.length + " files");
            for (int i = 0; i < files.length; i++) {
                Log.w(TAG, "Uncompressing: " + files[i]);
                File fileIn = new File(in, files[i]);
                File fileOut = new File(out, files[i]);

                try {
                    uncompressFile(fileIn, fileOut);
                } catch (FileNotFoundException e) {
                    // fileIn is a directory, uncompress the subdir
                    if (!fileOut.isDirectory()) {
                        Log.w(TAG, "Creating dir: " + fileOut.getAbsolutePath());
                        fileOut.mkdir();
                    }
                    uncompressDir(fileIn.getPath(), fileOut);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uncompressFile(File fileIn, File fileOut)
        throws IOException {
        InputStream in = assetManager.open
            (fileIn.getPath(),
             android.content.res.AssetManager.ACCESS_RANDOM);
        OutputStream out = new FileOutputStream(fileOut);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
        Log.i(TAG, "File copied.");
    }
}
