package in.aikya.mt.service.preference;



public interface SharedPrefService {

    void saveString(String key, String value);
    String getString(String key);

    boolean isLoggedIn(String key);
    void isLoggedIn(String key,Boolean value);

    void clearAll();
}
