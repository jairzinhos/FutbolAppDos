package org.beginningandroid.futbolappdos;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import java.util.UUID;

public class DeviceUtils {

    private static final String PREFS_NAME = "DevicePrefs";
    private static final String KEY_HYBRID_ID = "hybrid_id";

    // Generar o recuperar el HybridId
    public static String getHybridId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String hybridId = prefs.getString(KEY_HYBRID_ID, null);

        if (hybridId == null) {
            String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            String uuid = UUID.randomUUID().toString();
            hybridId = androidId + "_" + uuid;

            // Guardar en SharedPreferences
            prefs.edit().putString(KEY_HYBRID_ID, hybridId).apply();
        }

        return hybridId;
    }
}