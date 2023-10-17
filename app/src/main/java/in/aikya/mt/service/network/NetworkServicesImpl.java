package in.aikya.mt.service.network;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.aikya.mt.BuildConfig;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.utils.TraxsmartSteganoGraphy;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServicesImpl extends TraxsmartSteganoGraphy implements NetworkServices {

    private static volatile NetworkServicesImpl sInstance;

    private static volatile Context mContext;

    private Retrofit mRetrofit ;

    APIServices mAPIServices ;

    /**
     * Singleton pattern implementation
     */

    public static NetworkServices getInstance(Context context) {
        if (null == sInstance) {
            synchronized (NetworkServicesImpl.class) {
                sInstance = new NetworkServicesImpl();
                mContext = context;
            }
        }

        return sInstance;
    }

    private NetworkServicesImpl() {
        super(GlobalConstants.WEB_APP_URL,true, GlobalConstants.URL_PATTERN);
    }

    @Override
    protected void getSteganoWord(String stegano) {

    }
    private OkHttpClient getHttpClient() {

        int timeout = 180;
        return new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request original = chain.request();
                        okhttp3.Request request = original
                                .newBuilder()
//                                .headers(getHeaders())
                                .build();
                        return chain.proceed(request);
                    }
                })/*.addInterceptor(getLoggingInterceptor())*/.build();
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
   /* private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(getLoggingInterceptor());
        httpClient.addInterceptor(new ConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return NetworkServicesImpl.this.isNetworkAvailable(mContext);
            }
            @Override
            public void onInternetUnavailable() {
                ConnectionListener.getInstance().notifyNetworkChange(false);
            }
        });
        return httpClient.build();
    }*/
   /* private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }*/
    @Override
    protected void getText(String stegano) {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        //BuildConfig.BASE_URL
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
//                .addCallAdapterFactory(RetryCallAdapterFactory.create()) // retry calls
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
//                .client(provideOkHttpClient())
                .build();
        mAPIServices = mRetrofit.create(APIServices.class);
    }

   /* @Override
    public void checkUsername(LoginModel param, Callback<LoginResponseModel> callback) {
       Call<LoginResponseModel> call= mAPIServices.login(param);
        call.enqueue(callback);*/

//    }
}
