package com.xixis.googlecharts;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
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

public class ColumnChart extends WebView {

    private static final String ASSETS_EDITOR_HTML = "file:///android_asset/editor.html";

    private boolean isReady;
    private boolean isIncognitoModeEnabled;
    private String content;
    public ColumnChart(Context context) {
        super(context);
    }

    public ColumnChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachProgressBar(ProgressBar progressBar){
        init(progressBar);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(ProgressBar progressBar) {

        String content = "<html>"
                + "<head>"
                + "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
                + "<script type='text/javascript'>"
                + "google.load('visualization', '1', {packages:['corechart']});"
                + "google.setOnLoadCallback(drawChart);"
                + "function drawChart() {"
                + "var data = google.visualization.arrayToDataTable(["
                + "['Year', 'Sales', 'Expenses'],"
                + "['2010', 1000, 400],"
                + "['2011', 1170, 460],"
                + "['2012', 660, 1120],"
                + "['2013', 1030, 540]"
                + "]);"
                + "var options = {"
                + "title: 'Truiton Performance',"
                + "hAxis: {title: 'Year', titleTextStyle: {color: 'red'}}"
                + "};"
                + "var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
                + "chart.draw(data, options);"
                + "}"
                + "</script>"
                + "</head>"
                + "<body>"
                + "<div id='chart_div' style='width: 100%; height: 100%;'></div>"
                + "</body>"
                + "</html>";
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        requestFocusFromTouch();
        setWebViewClient(new BarChartClient(progressBar));
//        loadUrl(ASSETS_EDITOR_HTML);
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

        public BarChartClient(ProgressBar progressBar){
            this.progressBar = progressBar;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Toast.makeText(getContext(), "Just finished", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(INVISIBLE);
            isReady = url.equalsIgnoreCase(ASSETS_EDITOR_HTML);
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
}