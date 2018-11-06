package org.newtonproject.newpay.android.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class NewPayApi {

    private static Application mApplication;
    private static String privateKey;
    private static String appId;

    public static final int REQUEST_CODE_NEWPAY = 3001;
    public static final int REQUEST_CODE_NEWPAY_PAY = 3002;

    private static WeakReference<Activity> mCurrentActivity;

    private NewPayApi() {}

    public static void init(Application context, String appKey, String appid) {
        mApplication = context;
        privateKey = appKey;
        appId = appid;
    }

    public static void requestProfileFromNewPay(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("newpay://org.newtonproject.newpay.android.authorize"));
        String message = getMessage();
        Sign.SignatureData sig = getSignMessage(message, privateKey);
        intent.putExtra("message", message);
        intent.putExtra("sign_r", Numeric.toHexString(sig.getR()));
        intent.putExtra("sign_s", Numeric.toHexString(sig.getS()));
        intent.putExtra("appId", appId);
        PackageManager packageManager = mApplication.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY);
        } else{
            Toast.makeText(activity, R.string.no_newpay_application, Toast.LENGTH_SHORT).show();
        }
    }

    public static void requestPayForThirdApp(Activity activity, String address, BigInteger account){
        String unitStr = "NEW";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("newpay://org.newtonproject.newpay.android.pay"));
        intent.putExtra("SYMBOL", unitStr);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("AMOUNT", account.toString(10));
        intent.putExtra("REQUEST_PAY_SOURCE", activity.getPackageName());
        PackageManager packageManager = mApplication.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY_PAY);
        } else{
            Toast.makeText(activity, R.string.no_newpay_application, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getMessage() {
        return System.currentTimeMillis() + new Random().nextInt(1000000) + "";

    }

    private static Sign.SignatureData getSignMessage(String message, String privateKey) {
        return Sign.signMessage(message.getBytes(), ECKeyPair.create(Numeric.toBigInt(privateKey)));
    }
}
