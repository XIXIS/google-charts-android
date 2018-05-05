# google-charts-android
A google charts library for android

### A quick overview of features
- Declare chart in xml
- Pass Chart **Title, Subtitle, Type (Classic or Material)** as attributes  

## Screenshots
![Image](https://github.com/XIXIS/google-charts-android/blob/master/screenshots/device-2018-05-05-095742.png)

# Set Up
## 1. Add INTERNET permission in your manifest
```Manifest 
<uses-permission android:name="android.permission.INTERNET" />
```

## 2. Add Chart View in xml 
```xml
<com.xixis.googlecharts.BarChart
    android:id="@+id/barchart"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:chartTitle="Classic Chart Title"
    app:chartSubtitle="Classic Chart Subtitle"
    app:chartType="Classic"/>
```
