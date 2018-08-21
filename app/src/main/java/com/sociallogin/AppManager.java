package com.sociallogin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.sociallogin.constants.AppConstants;
import com.sociallogin.managers.SharedPreferenceManager;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Santosh on 16-08-2018.
 */
public class AppManager extends Application implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        instantiateManagers();
    }

    /**
     * Method to instantiate all the managers in this app
     */
    private void instantiateManagers() {
        FacebookSdk.sdkInitialize(this);
        Fresco.initialize(this);
        SharedPreferenceManager.getSharedInstance().initiateSharedPreferences(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(AppConstants.TWITTER_KEY, AppConstants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
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

    }


}

