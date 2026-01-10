package  com.example.newapp;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager{

    private static final String PREF_NAME = "app_session";

    private static final String KEY_USER_ID = "user_id";


    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUser(Context context, int userId) {
        prefs(context).edit()
                .putInt(KEY_USER_ID, userId)
                .apply();
    }

    public static int getUserId(Context context) {
        return prefs(context).getInt(KEY_USER_ID, -1);
    }

    public static boolean isLoggedIn(Context context) {
        return getUserId(context) != -1;
    }

    public static void clear(Context context) {
        prefs(context).edit().clear().apply();
    }



}