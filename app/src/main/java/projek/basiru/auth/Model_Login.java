package projek.basiru.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Login
{
    //aplod gbr
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    //aplod gbr

    //login dan regis
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;
    //login dan regis


    //donasi
    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("program")
    @Expose
    private String program;

    @SerializedName("no_telp")
    @Expose
    private String no_telp;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("image")
    @Expose
    private String image;
    //donasi


//    //program
//    //donasi
//    @SerializedName("judul")
//    @Expose
//    private String judul;
//
//    @SerializedName("mulai")
//    @Expose
//    private String mulai;
//
//    @SerializedName("deadline")
//    @Expose
//    private String deadline;
//
//    @SerializedName("status")
//    @Expose
//    private String status;
//    //program


    //setter dan getter untuk login dan regis
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    //setter dan getter untuk login dan regis


    //setter dan getter untuk donasi
    public String getNama( String nama) { return nama; }
    public void setNama(String nama) { this.nama = nama; }


    public String getProgram( String program) { return program; }
    public void setProgram(String program) { this.program = program; }


    public String getNo_telp( String no_telp) { return no_telp; }
    public void setNo_telp(String nominalNo_telp) { this.no_telp = no_telp; }


    public String getNominal( String n) { return nominal; }
    public void setNominal(String nominal) { this.nominal = nominal; }


    public String getBank( String bank) { return bank; }
    public void setBank(String bank) { this.bank = bank; }


    public String getImage( String image) { return image; }
    public void setImage(String image) { this.image = image; }
    //setter dan getter untuk donasi


//    //setter dan getter untuk program
//    public String getJudul() { return judul; }
//    public void setJudul(String judul) { this.judul = judul; }
//
//    public String getMulai() { return mulai; }
//    public void setMulai(String mulai) { this.mulai = mulai; }
//
//    public String getDeadline() { return deadline; }
//    public void setDeadline(String deadline) { this.deadline = deadline; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//    //setter dan getter untuk program

}
