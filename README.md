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

## 3. Initialise view in onCreate method 
```java
BarChart barChart = findViewById(R.id.barchart);
```

## 4. Set up chart by calling appropriate methods with required params

### 4[a]. Optional - Attach Progress Bar 
```java
ProgressBar progressbar = findViewById(R.id.progressbar);

barChart.attachProgressBar(progressbar);
```

### 4[b]. Set chart header and data methods (using strings or arrays)
```java
String[] chartHeader = new String[] { "Year", "Sales", "Expenses" } ;
String[][] chartData = {
                new String[] { "2014", "1000", "400" },
                new String[] { "2015", "1170", "460" },
                new String[] { "2016", "660", "1120" },
                new String[] { "2017", "1030", "540" }
        };

barChart.setChartAxisHeaders(chartHeader)
        .setChartData(chartData)
```

### OR
```java
String chartHeaderString = "['Year', 'Sales', 'Expenses'],";
String chartDataString = "['2014', 1000, 400]," +
                "['2015', 1170, 460]," +
                "['2016', 660, 1120]," +
                "['2017', 1030, 540]";

barChart.setChartAxisHeaders(chartHeaderString)
        .setChartData(chartDataString)
```

## 5. Load chart 
```java
barChart.loadChart();
```

