package nl.ckramer.mynotifications.Util;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import nl.ckramer.mynotifications.App;
import nl.ckramer.mynotifications.BuildConfig;
import nl.ckramer.mynotifications.Entity.MyObjectBox;

public class ObjectBox {

    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();

        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
    }

    public static BoxStore get() {
        return boxStore;
    }

}