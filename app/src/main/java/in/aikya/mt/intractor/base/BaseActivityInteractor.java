package in.aikya.mt.intractor.base;





import java.net.UnknownHostException;
import java.util.Collection;

import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.model.base.BaseResponseModel;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.service.holder.ServiceHolder;


public abstract class BaseActivityInteractor<APRESENTER extends BaseActivityPresenter> {

    public static final String USER_JWT_TOKEN = "USER_JWT_TOKEN";

    private static final String SUCCESS = "success";
    private static final String INTERNET_CONNECTION_ISNT_WORKING = "No network connection!";
    private static final String AUTH_TOKEN_FORMAT = " JWT %s";

//    private CompositeDisposable disposable;
    private APRESENTER mPresenter;

    private ServiceHolder mServiceHolder;

    public BaseActivityInteractor(APRESENTER presenter){
        mPresenter = presenter;
        mServiceHolder = ServiceHolder.getInstance(presenter.getContext());
//        disposable = new CompositeDisposable();
//        addInternetConnectionListener();
    }

  /*  private void addInternetConnectionListener() {
        disposable.add(ConnectionListener.getInstance()
                .listenNetworkChange().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override public void accept(Boolean aBoolean) throws Exception {
                        onInternetUnavailable();
                    }
                }));
    }*/

    protected abstract  void onInternetUnavailable();

    /**
     * @return {@link APRESENTER} - for interactors to invoke methods in presenter
     */
   public APRESENTER getPresenter(){
        return mPresenter;
    }

    /**
     * @return {@link ServiceHolder} instance for Interactors to access services
     */
    protected ServiceHolder getServices(){
        return mServiceHolder;
    }

    /**
     *
     * @param response Response from the API of type {@link BaseResponseModel}
     * @return {@link Boolean} - whether the api request is success or failure
     */
    protected boolean isSuccess(BaseResponseModel response) {

        return response.getStatus().equals(SUCCESS);
    }

    /**
     * Method that converts an internal {@link Exception} to displayable message for API errors
     * @param throwable - value obtained from network callback
     * @return {@link String} message that is to be displayed to the user
     */
    protected String getErrorMessage(Throwable throwable) {
        String errorMessage = null;
        if (throwable instanceof UnknownHostException){
            errorMessage = INTERNET_CONNECTION_ISNT_WORKING;
        }
        return errorMessage;
    }

    /**
     * Returns the Authorization token in required format for API calls
     * @return {@link String} authorization token required for API calls
     */
    protected String getAuthToken(){
        return String.format(AUTH_TOKEN_FORMAT, getServices().getPersistenceServices().getString(USER_JWT_TOKEN));
    }

    /**
     * @param collection
     * @param <T>
     * @return {@link Boolean} whether the collection is empty or not
     */
    protected  <T> boolean isEmptyCollection(Collection<T> collection) {
        return null == collection || collection.size() == 0;
    }
    protected String getAuthKey(){

        return getServices().getPersistenceServices().getString(GlobalConstants.USER_AUTH_KEY);
    }
}