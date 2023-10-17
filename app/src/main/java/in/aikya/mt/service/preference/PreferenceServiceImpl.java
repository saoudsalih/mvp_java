package in.aikya.mt.service.preference;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceServiceImpl implements PreferenceServiceInterface {


    private static volatile PreferenceServiceImpl sInstance;

    private SharedPrefService mSharedPrefService;

    public static PreferenceServiceImpl getInstance(Context context){
        if(null == sInstance){
            sInstance = new PreferenceServiceImpl(context);
        }

        return sInstance;
    }

    private PreferenceServiceImpl(Context context){
        mSharedPrefService = new SharedPreferenceImplementation(context);
    }


    @Override
    public void saveString(String key, String value) {

        mSharedPrefService.saveString(key, value);
    }

    @Override
    public String getString(String key) {
       return mSharedPrefService.getString(key);
    }

    @Override
    public boolean isLoggedIn(String key) {
        return mSharedPrefService.isLoggedIn(key);
    }

    @Override
    public void isLoggedIn(String key, Boolean value) {
        mSharedPrefService.isLoggedIn(key,value);
    }


    @Override
    public void clearAll() {
        mSharedPrefService.clearAll();
    }
}
