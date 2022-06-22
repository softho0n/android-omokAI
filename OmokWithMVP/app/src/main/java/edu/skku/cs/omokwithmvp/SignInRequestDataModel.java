package edu.skku.cs.omokwithmvp;

public class SignInRequestDataModel {
    private String name;
    private String passwd;

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }
}
