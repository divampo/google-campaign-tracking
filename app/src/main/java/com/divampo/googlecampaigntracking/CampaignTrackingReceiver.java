package com.divampo.googlecampaigntracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CampaignTrackingReceiver extends BroadcastReceiver {

    private static CampaignTrackingReceiver instance;

    // text that will be read by Unity
    public static String text = "";


    // static method to create our receiver object, it'll be Unity that will create ou receiver object (singleton)
    public static void createInstance() {
        if(instance ==  null) {
            instance = new CampaignTrackingReceiver();
        }

    }

    // Triggered when an Intent is caught
    @Override
    public void onReceive(Context context, Intent intent) {
        // We get the data the Intent has
        String sentIntent = intent.getStringExtra("referrer");
        if (sentIntent != null) {
            // We assigned it to our static variable
            text = sentIntent;
        }
    }
}
