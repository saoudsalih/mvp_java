package in.aikya.mt.service.holder;

import android.content.Context;

import in.aikya.mt.service.network.NetworkServices;
import in.aikya.mt.service.network.NetworkServicesImpl;
import in.aikya.mt.service.preference.PreferenceServiceImpl;
import in.aikya.mt.service.preference.SharedPrefService;


public class ServiceHolder {


    private static volatile ServiceHolder sInstance;
    NetworkServices mNetworkServices;
    SharedPrefService mSharedPreferanceService;
//    DbServiceInterface mDbServiceInterface;

    public static ServiceHolder getInstance(Context context){
        if(null == sInstance){
            synchronized (ServiceHolder.class){
                sInstance = new ServiceHolder(context);
            }
        }

        return sInstance;


    }


    private ServiceHolder(Context context){
        mNetworkServices = NetworkServicesImpl.getInstance(context);
        mSharedPreferanceService = PreferenceServiceImpl.getInstance(context);
//        mDbServiceInterface = DbSeviceImplementation.getInstance(context);

    }

    public NetworkServices getNetworkServices(){
        return mNetworkServices;
    }

    /**
     * @return Singleton instance of the {@Link IPersistenceServices} for interactors to use storage and retrieval methods
     */
    public SharedPrefService getPersistenceServices(){
        return mSharedPreferanceService;
    }

    /*public DbServiceInterface getDbServiceInterface(){
        return mDbServiceInterface;
    }*/

}