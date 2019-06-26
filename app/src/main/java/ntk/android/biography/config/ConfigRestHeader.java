package ntk.android.biography.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import ntk.android.biography.BuildConfig;
import ntk.android.biography.utill.AppUtill;

public class ConfigRestHeader {

    @SuppressLint("HardwareIds")
    public Map<String, String> GetHeaders(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Map<String, String> headers = new HashMap<>();
        headers.put("Token", "");
        headers.put("LocationLong", "0");
        headers.put("LocationLat", "0");
        headers.put("DeviceId", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        headers.put("DeviceBrand", AppUtill.GetDeviceName());
        headers.put("Country", Base64.encodeToString(context.getResources().getConfiguration().locale.getDisplayCountry().getBytes(), Base64.NO_WRAP));
        headers.put("Language", Base64.encodeToString(context.getResources().getConfiguration().locale.getLanguage().getBytes(), Base64.NO_WRAP));
        headers.put("SimCard", manager.getSimOperatorName());
        headers.put("PackageName", "ntk.cms.vitrin.app11");
        headers.put("AppBuildVer", String.valueOf(BuildConfig.VERSION_CODE));
        headers.put("AppSourceVer", BuildConfig.VERSION_NAME);
        String NotId = FirebaseInstanceId.getInstance().getToken();

        if (NotId != null && !NotId.isEmpty() && !NotId.toLowerCase().equals("null"))
            headers.put("NotificationId", NotId);

        return headers;
    }
}