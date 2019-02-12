package org.newtonproject.newpay.android.sdk.constant;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019/2/12--4:51 PM
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class Constant {

    public interface MainNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.release.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.release.authorize";
    }

    public interface TestNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.authorize";
    }

    public interface BetaNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.beta.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.beta.authorize";
    }

    public interface DevNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.dev.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.dev.authorize";
    }
}
