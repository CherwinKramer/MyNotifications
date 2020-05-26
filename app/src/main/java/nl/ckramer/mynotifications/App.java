package nl.ckramer.mynotifications;

import android.app.Application;

import io.objectbox.BoxStore;
import nl.ckramer.mynotifications.Util.ObjectBox;

public class App extends Application {
    public static final String TAG = "App";

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }

    public static App getApp() {
        return sApp;
    }
}
