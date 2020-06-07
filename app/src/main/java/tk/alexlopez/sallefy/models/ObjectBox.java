package tk.alexlopez.sallefy.models;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import tk.alexlopez.sallefy.BuildConfig;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ObjectBox {
    private static BoxStore boxStore;
    public static final String TAG = "ObjectBox";

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}