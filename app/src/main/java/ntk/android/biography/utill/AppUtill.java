package ntk.android.biography.utill;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ntk.android.biography.BuildConfig;
import ntk.android.biography.R;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AppUtill {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static String GetDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String GetVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static long DownloadData(Context context, String UrlDownload, String Path, String FileName) {

        long downloadReference;
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(UrlDownload));
        request.setTitle(context.getString(R.string.app_name));
        request.setDescription("در حال دانلود...");
        request.setDestinationInExternalPublicDir(Path, FileName);
        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }

    public static int GetStatusDownload(Context context, long downloadId) {
        DownloadManager downloadManager =
                (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            c.close();
            return status;
        }
        return -1;
    }

    @SuppressLint("SetWorldReadable")
    public static void AppInstall(Context context, String Path) {
        File file = new File(Path);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            file.setReadable(true, false);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.getApplicationContext().startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri fileUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID,
                    file);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }
    }

    public static String GetDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static String GregorianToPersian(String value) {
        String[] date = value.split("/");
        String result = "";

        try {
            int e = Integer.parseInt(date[0]);
            int m = Integer.parseInt(date[1]);
            int d = Integer.parseInt(date[2]);
            if (e != 0 || m != 0 || d != 0) {
                UtillDate roozh = new UtillDate();
                roozh.GregorianToPersian(e, m, d);
                result = roozh.toString();
                result = result.replace("-", "/");
            }
        } catch (Exception var8) {
            result = "Error Convert Date";
        }

        return result;
    }

    public static String PersianToGregorian(String value) {
        String[] Value = value.split("T");
        String[] date = Value[0].split("-");
        String result = "";

        try {
            int e = Integer.parseInt(date[0]);
            int m = Integer.parseInt(date[1]);
            int d = Integer.parseInt(date[2]);
            if (e != 0 || m != 0 || d != 0) {
                UtillDate roozh = new UtillDate();
                roozh.PersianToGregorian(e, m, d);
                result = roozh.toString();
                result = result.replace("-", "/");
            }
        } catch (Exception var8) {
            result = "Error Convert Date";
        }

        return result;
    }

    public static int GetMinDayOfYear(String Persian, String Gregorian) {
        String[] DateSplit = Persian.split("/");
        int Day = Integer.parseInt(DateSplit[2]);
        SimpleDateFormat Sample = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Date d = new Date();
        try {
            d = Sample.parse(Gregorian);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, -(Day - 1));
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int GetMaxDayOfYear(String Persian, String Gregorian) {
        String[] DateSplit = Persian.split("/");
        int Day = Integer.parseInt(DateSplit[2]);
        int Month = Integer.parseInt(DateSplit[1]);
        SimpleDateFormat Sample = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Date d = new Date();
        try {
            d = Sample.parse(Gregorian);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        if (Month >= 7 && Month < 12) {
            calendar.add(Calendar.DAY_OF_MONTH, (30 - Day));
        } else if (Month == 12) {
            calendar.add(Calendar.DAY_OF_MONTH, (29 - Day));
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, (31 - Day));
        }
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static String GetMinOfYear(String Persian) {
        String[] DateSplit = Persian.split("/");
        int Year = Integer.parseInt(DateSplit[0]);
        return AppUtill.PersianToGregorian(Year + "-01-01");
    }

    public static String GetMaxOfYear(String Persian) {
        String[] DateSplit = Persian.split("/");
        int Year = Integer.parseInt(DateSplit[0]);
        return AppUtill.PersianToGregorian(Year + "-12-29");
    }
}

