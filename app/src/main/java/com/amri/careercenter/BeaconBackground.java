package com.amri.careercenter;

/**
 * Created by XXX on 5/21/2017.
 */

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.net.URISyntaxException;
import java.util.List;

public class BeaconBackground extends Application implements BootstrapNotifier {
    private String TAG = this.getClass().getSimpleName();
    private RegionBootstrap regionBootstrap;
    public static final String PACKAGE_NAME = "jp.naver.line.android";
    private List<ApplicationInfo> m_appList;
    private BackgroundPowerSaver backgroundPowerSaver;

    @Override
    public void onCreate() {
        super.onCreate();
        backgroundPowerSaver = new BackgroundPowerSaver(this);
        Log.d(TAG, "App started up");
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundScanPeriod(5000);
        beaconManager.setBackgroundBetweenScanPeriod(10000);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        Region region = new Region("com.amri.careercenter.BeaconBackground",
                Identifier.parse("8492E75F-4FD6-469D-B132-043FE94921D8"), null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "Got a didEnterRegion call");
        addNotification();
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("ITB Career Center ")
                        .setContentText("Tambahkan teman & ketik 'INFO'");

        String sendText = "line://ti/p/@gmy0277s";
        Intent intent = null;
        if(checkLineInstalled()) {
            String lineString = sendText;
            try {
                intent = Intent.parseUri(lineString, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            //startActivity(intent);
        }

        //  Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private boolean checkLineInstalled() {
        PackageManager pm = getPackageManager();
        m_appList = pm.getInstalledApplications(0);
        boolean lineInstallFlag = false;
        for (ApplicationInfo ai : m_appList) {
            if (ai.packageName.equals(PACKAGE_NAME)) {
                lineInstallFlag = true;
                break;
            }
        }
        return lineInstallFlag;
    }

    public void sendTextHandler() {
        String sendText = "line://ti/p/@gmy0277s";
        if(checkLineInstalled()){
            String lineString = sendText;
            Intent intent = null;
            try {
                intent = Intent.parseUri(lineString, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            Toast toast = Toast.makeText(this, "Ketik : INFO", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this, "LINE belum terinstall di gadget anda", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}