package org.newtonproject.newpay.android.sdk.bean;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2018/10/27--10:34 AM
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class SigMessage {

    public String signS;
    public String signR;
    public String message;

    public SigMessage(String signR, String signS, String message) {
        this.signR = signR;
        this.signS = signS;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("{" +
                "signS: %s " +
                "signR: %s " +
                "message: %s " +
                "}", signS, signR, message);
    }
}
