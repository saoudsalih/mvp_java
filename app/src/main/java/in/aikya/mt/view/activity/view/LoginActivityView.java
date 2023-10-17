package in.aikya.mt.view.activity.view;

import in.aikya.mt.view.base.BaseActivityView;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public interface LoginActivityView extends BaseActivityView {
    String getUserName();
    String getUserPassword();
    void onError(String field,String message);
}
