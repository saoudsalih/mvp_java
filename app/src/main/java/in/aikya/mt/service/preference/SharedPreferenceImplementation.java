package in.aikya.mt.service.preference;

import android.content.Context;
import android.content.SharedPreferences;

import in.aikya.mt.app.GlobalConstants;


public class SharedPreferenceImplementation implements SharedPrefService {

    private SharedPreferences mSharedPrefs;

    public SharedPreferenceImplementation(Context context){
        mSharedPrefs = context.getSharedPreferences(GlobalConstants.USER_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getString(String key) {
        return mSharedPrefs.getString(key, null);
    }

    @Override
    public boolean isLoggedIn(String key) {
        return mSharedPrefs.getBoolean(key, false);
    }

    @Override
    public void isLoggedIn(String key, Boolean value) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    @Override
    public void clearAll() {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.clear();
        editor.commit();
    }
}
