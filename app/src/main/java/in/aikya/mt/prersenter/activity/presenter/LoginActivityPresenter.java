package in.aikya.mt.prersenter.activity.presenter;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public interface LoginActivityPresenter {
    void validateCredentials(String userName,String password);
    void signUpUser();
    void redirectPage();
    void message(String message);
}
