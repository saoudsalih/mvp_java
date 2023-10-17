package in.aikya.mt.model.RequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class Attributes {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @SerializedName("address")
    @Expose
    private String address;

    @Expose
    private String comments;
}
