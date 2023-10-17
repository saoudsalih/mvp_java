package in.aikya.mt.intractor.intractorImpl;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;

import in.aikya.mt.app.BaseApplication;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.base.BaseActivityInteractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenterImpl.RegistrationActivityPresenterImpl;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class RegistrationActivityIntractor extends BaseActivityInteractor<RegistrationActivityPresenterImpl> {
    SQLiteDatabase mDatabase;
    public RegistrationActivityIntractor(RegistrationActivityPresenterImpl presenter) {
        super(presenter);
        mDatabase = BaseApplication.getInstance().openOrCreateDatabase(GlobalConstants.DATABASE_NAME, MODE_PRIVATE, null);
        createUserDetailsInfo();
    }


    private void createUserDetailsInfo() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS userInfo (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT user_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    mobile varchar(200) NOT NULL,\n" +
                        "    email varchar(200) NOT NULL,\n" +
                        "    password varchar(200) NOT NULL,\n" +
                        "    attributes varchar(200) NOT NULL\n" +
                        ");"
        );
    }

    public void addUser(UserRegistrationModel registrationModel){
        String insertSQL = "INSERT INTO userInfo \n" +
                "(name, mobile, email, password,attributes)\n" +
                "VALUES \n" +
                "(?, ?, ?, ?,?);";
        mDatabase.execSQL(insertSQL, new String[]{registrationModel.getName(), registrationModel.getPhoneNumber(), registrationModel.getEmail(), registrationModel.getPassword(),new Gson().toJson(registrationModel.getAttributes())});
        getPresenter().addUserSuccessfully(registrationModel);
    }

    @Override
    protected void onInternetUnavailable() {

    }
}
