package com.xixis.googlecharts;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BarChart extends WebView {

    private boolean isIncognitoModeEnabled;
    private String content;
    private String chartTitle = "Chart Title";
    private String chartSubtitle = "Chart Subtitle";


    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray nAttrs = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BarChart,
                0, 0);

        try {
            chartTitle = nAttrs.getString(R.styleable.BarChart_chartTitle);
            chartSubtitle = nAttrs.getString(R.styleable.BarChart_chartSubtitle);
        } finally {
            nAttrs.recycle();
        }

    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray nAttrs = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BarChart,
                0, 0);

        try {
            chartTitle = nAttrs.getString(R.styleable.BarChart_chartTitle);
            chartSubtitle = nAttrs.getString(R.styleable.BarChart_chartSubtitle);
        } finally {
            nAttrs.recycle();
        }
    }

    public void attachProgressBar(ProgressBar progressBar) {
        init(progressBar);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(ProgressBar progressBar) {

        String content = "<html>" +
                "<head>" +
                "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>" +
                "<script type='text/javascript'>" +
                "google.charts.load('current', {'packages':['bar']});" +
                "google.charts.setOnLoadCallback(drawChart);" +
                "function drawChart () {" +
                "var data = google.visualization.arrayToDataTable([" +
                "['Year', 'Sales', 'Expenses']," +
                "['2014', 1000, 400]," +
                "['2015', 1170, 460]," +
                "['2016', 660, 1120]," +
                "['2017', 1030, 540]]);" +
                "var options = {" +
                "chart:{" +
                "title: '"+ chartTitle +"'," +
                "subtitle:'"+ chartSubtitle +"'," +
                "}," +
                "bars: 'horizontal' " +
                "};" +
                "var chart = new google.charts.Bar(document.getElementById('barchart_material'));" +
                "chart.draw(data, google.charts.Bar.convertOptions(options));" +
                "}" +
                "</script >" +
                "</head>" +
                "<body>" +
                "<div id='barchart_material' style='width: 100%; height: 100%;'></div >"+
                "</body>"+
                "</html>";


        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        requestFocusFromTouch();

        setWebViewClient(new BarChartClient(progressBar));
        loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);

    }

    @Override
    public void destroy() {

        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        super.destroy();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection = super.onCreateInputConnection(outAttrs);
        if (isIncognitoModeEnabled) {
            outAttrs.imeOptions = outAttrs.imeOptions
                    | EditorInfoCompat.IME_FLAG_NO_PERSONALIZED_LEARNING;
        }
        return inputConnection;
    }

    private class BarChartClient extends WebViewClient {

        ProgressBar progressBar;

        public BarChartClient(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(INVISIBLE);
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    public void setTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getTitle() {
        return chartTitle;
    }

    public void setChartSubtitle(String chartSubtitle) {
        this.chartSubtitle = chartSubtitle;
    }

    public String getChartSubtitle() {
        return chartSubtitle;
    }
}
