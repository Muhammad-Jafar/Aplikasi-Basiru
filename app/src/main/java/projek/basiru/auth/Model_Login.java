package projek.basiru.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Login
{
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getNominal( String nominal) { return nominal; }

    public void setNominal(String nominal) { this.nominal = nominal; }
}
