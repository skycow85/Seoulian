package kr.go.seoul.seoulian.Network;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by jkwoo on 2015-09-15.
 */
public class NetworkManager {
    public static boolean IsNetworkEnable(Context context) {
        boolean bEnable = false;
        boolean isEmul = false;
        String imei = null;

        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        imei = mTelephonyMgr.getDeviceId();

        if (imei != null) {
            isEmul = imei.equals("000000000000000");
        }

        if (isEmul == true) {
            bEnable = true;
        } else {
            ConnectivityManager networkMgr = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
            NetworkInfo net = networkMgr.getActiveNetworkInfo();
            if (net != null) {
                bEnable = true;
            }
        }

        return bEnable;
    }
}
