package projek.basiru.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponsesAuth
{

    @SerializedName("Msg")
    @Expose
    private String Msg;

    @SerializedName("data")
    @Expose
    private List<Model_Login> bd;

    public List<Model_Login> getBd() {
        return bd;
    }

    public void setBd(List<Model_Login> bd) {
        this.bd = bd;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
