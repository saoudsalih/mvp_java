package in.aikya.mt.intractor.intractorImpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import in.aikya.mt.app.BaseApplication;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.base.BaseActivityInteractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenterImpl.LoginActivityPresenterImpl;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class LoginActivityIntractor extends BaseActivityInteractor<LoginActivityPresenterImpl> {


    SQLiteDatabase mDatabase;
    UserRegistrationModel userRegistrationModel;
    public LoginActivityIntractor(LoginActivityPresenterImpl presenter) {
        super(presenter);
        mDatabase = BaseApplication.getInstance().openOrCreateDatabase(GlobalConstants.DATABASE_NAME, MODE_PRIVATE, null);
    }



    public Boolean isUserLoggedIn(){
        return getServices().getPersistenceServices().isLoggedIn(GlobalConstants.LOGGED_IN);
    }

    public UserRegistrationModel getUserDetails(String userEmail,String password) {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM userInfo WHERE email='"+userEmail+"' AND password='"+password+"'", null);

        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                userRegistrationModel = new UserRegistrationModel(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4),
                        cursorEmployees.getString(5)
                );
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();

        return userRegistrationModel;
    }
    @Override
    protected void onInternetUnavailable() {

    }
}
