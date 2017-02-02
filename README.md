# google-campaign-tracking

Unity plugin for those, who want to track app installs referrers from Google Play Store without Google Analytics

## Dependency

 - Gradle 2.+
 - Android SDK 24.+

## Build

Open project in Android Studio. Add target platform to the project settings. Then run `exportJar` task from Gradle tasks.
Build is done, .jar is available under `release/` folder.

## Install

Copy exported jar into `Assets/Plugins/Android/` folder. Open `AndroidManifest.xml` in the same location and add following changes:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.unity3d.player">

  ...

  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

  ...

  <application>

    ...

    <receiver android:name="com.divampo.googlecampaigntracking.CampaignTrackingReceiver"
        android:enabled="true"
        android:exported="true">
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" ></action>
        </intent-filter>
    </receiver>
    <meta-data android:name="com.google.android.gms.version"
                   android:value="@integer/google_play_services_version" />

    ...

  </application>
</manifest>
```
## Usage

Here is an example code in C# to retreive google campaign referrer:

```c#

using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.Collections.Generic;

public class GoogleCampaignTracking : MonoBehaviour {

	AndroidJavaClass jc;
	string javaMessage = "";

	// Use this for initialization
	void Start () {
		#if UNITY_EDITOR
		#elif UNITY_ANDROID
		// Acces the android java receiver
		jc = new AndroidJavaClass("com.divampo.googlecampaigntracking.CampaignTrackingReceiver");
		jc.CallStatic("createInstance");
		#elif UNITY_IPHONE
		#else
		#endif
	}

	void Update() {
		#if UNITY_EDITOR
		// doesn't work in unity editor
		#elif UNITY_ANDROID
		// condition to send message only once
		if (string.IsNullOrEmpty(PlayerPrefs.GetString("CAMPAIGN_CODE", ""))) {
			javaMessage = jc.GetStatic<string>("text");

			// if message exist
			if (!string.IsNullOrEmpty(javaMessage)) {
				PlayerPrefs.SetString ("CAMPAIGN_CODE", javaMessage);

                // `javaMessage` contains your referrer AS IS

			}
		}
		#elif UNITY_IPHONE
		#else
		// unknown platform
		#endif
	}
}

```

## License

This code is licensed under the MIT License