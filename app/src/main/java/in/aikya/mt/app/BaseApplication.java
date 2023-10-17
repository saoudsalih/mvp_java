package in.aikya.mt.app;

import android.app.ActionBar;
import android.app.Application;

import com.google.firebase.FirebaseApp;


public class BaseApplication extends Application {


    public static ActionBar actionBar;
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
        FirebaseApp.initializeApp(this);
      //  SQLiteDatabase.loadLibs(this);

    }

    public static BaseApplication getInstance(){return instance;}

}
