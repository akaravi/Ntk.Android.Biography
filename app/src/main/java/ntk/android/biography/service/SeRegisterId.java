package ntk.android.biography.service;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import ntk.android.biography.BuildConfig;

/**
 * Created by Mehrdad Safari on 18-Jan-17.
 */

public class SeRegisterId extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.APPLICATION_ID);
    }
}
