package org.lisp.ecl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;

public class EmbeddedCommonLisp extends Service {
    private static final String TAG = "EmbeddedCommonLisp";
    public static final String BROADCAST_STATUS = "org.lisp.ecl.status";
    public static final String BROADCAST_RESULT = "org.lisp.ecl.result";

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            String action = b.getString("action");

            if (action == "ENSURE_LISP") {
                Log.w(TAG,"Ensure lisp");
                start();
                sendBroadcast
                    (new Intent(BROADCAST_STATUS)
                     .putExtra("data", "ECL is up and running")
                     .putExtra("status", "start done")
                     );
            } else if (action == "EVAL") {
                String data = b.getString("data");
                String result = exec(data);
                Log.w(TAG,"Eval: " + data);
                sendBroadcast
                    (new Intent(BROADCAST_RESULT).putExtra("data", result));
            } else {
                Log.w(TAG, "Unknown action, ignoring... " + action);
            }
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                                                 Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Extract action and data from the intent
        Bundle b = new Bundle();
        b.putString("action", intent.getAction());
        b.putString("data", intent.getDataString());

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.setData(b);
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    public static native void start();
    public static native void setup(String path);
    public static native String exec(String string);
    public static native String botExec(String string);

    static {
        System.loadLibrary("ecl");
        System.loadLibrary("ecl_android");
        Log.w(TAG, "Done loading the library");
    }
}
