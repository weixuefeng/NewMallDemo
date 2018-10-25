package org.newtonproject.newpay.android.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

public class NewPayApi {

    private static Application mApplication;
    private static String privateKey;
    private static String appId;

    public static final int REQUEST_CODE_NEWPAY = 3000;

    private static WeakReference<Activity> mCurrentActivity;

    private NewPayApi() {}

    public static void init(Application context, String appKey, String appid) {
        mApplication = context;
        privateKey = appKey;
        appId = appid;
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mCurrentActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mCurrentActivity.clear();
                mCurrentActivity = null;
            }
        });
    }

    public static void requestProfileFromNewPay() {
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
            mCurrentActivity.get().startActivityForResult(intent, REQUEST_CODE_NEWPAY);
        } else{
            Toast.makeText(mCurrentActivity.get(), R.string.no_newpay_application, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getMessage() {
        return System.currentTimeMillis() + new Random().nextInt(1000000) + "";

    }

    private static Sign.SignatureData getSignMessage(String message, String privateKey) {
        return Sign.signMessage(message.getBytes(), ECKeyPair.create(Numeric.toBigInt(privateKey)));
    }
}
