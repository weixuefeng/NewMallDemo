package org.newtonproject.newpay.android.sdk.bean;

public class ProfileInfo {
    public String address;
    public String newid;
    public String countryCode;
    public String cellphone;
    public String inviteCode;
    public String avatarPath;
    public String name;
    public String access_key;


    @Override
    public String toString() {
        return String.format("{address: %s, " +
                "newid: %s, " +
                "countryCode: %s," +
                "cellphone: %s," +
                "inviteCode: %s," +
                "avatarPath: %s," +
                "name: %s, " +
                "access_key: %s }", address, newid, countryCode, cellphone, inviteCode, avatarPath, name, access_key);
    }
}
