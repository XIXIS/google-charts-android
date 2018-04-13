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

public class BarChart extends WebView {

    private static final int CLASSIC_CHART = 0;
    private static final int MATERIAL_CHART = 1;
    private static final String oHtmlOHeadChartScriptAPIUriOScript =
            "<html><head><script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script><script type='text/javascript'>";
    private static final String cScriptCHeadOBodyChartDivCBodyCHtml =
            "</script></head><body><div id='barchart' style='width: 100%; height: 100%;'></div ></body></html>";
    private static final String classicChartPackageAndCallback =
            "google.charts.load('current', {packages:['corechart']});google.charts.setOnLoadCallback(drawChart);";
    private static final String materialChartPackageAndCallback =
            "google.charts.load('current', {'packages':['bar']});google.charts.setOnLoadCallback(drawChart);";
    private static final String functionToVisualisation = "function drawChart() { var data = google.visualization.arrayToDataTable([";


    private boolean isIncognitoModeEnabled;
    private String content;
    private String chartTitle = "Chart Title";
    private String chartSubtitle = "Chart Subtitle";
    private int chartType;
    private ProgressBar progressBar;
    private String[][] chartData;
    private String chartDataString="";


    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.barchart_preview));
        TypedArray nAttrs = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BarChart,
                0, 0);

        try {
            chartTitle = nAttrs.getString(R.styleable.BarChart_chartTitle);
            chartSubtitle = nAttrs.getString(R.styleable.BarChart_chartSubtitle);
            chartType = nAttrs.getInteger(R.styleable.BarChart_chartType, CLASSIC_CHART);
        } finally {
            nAttrs.recycle();
        }

    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(getResources().getDrawable(R.drawable.barchart_preview));
        TypedArray nAttrs = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BarChart,
                0, 0);

        try {
            chartTitle = nAttrs.getString(R.styleable.BarChart_chartTitle);
            chartSubtitle = nAttrs.getString(R.styleable.BarChart_chartSubtitle);
            chartType = nAttrs.getInteger(R.styleable.BarChart_chartType, CLASSIC_CHART);
        } finally {
            nAttrs.recycle();
        }
    }

    public BarChart attachProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public BarChart setChartData(String chartDataString) {
        this.chartDataString = chartDataString;
        return this;
    }

    public BarChart setChartData(String[][] chartData){
        return this;
    }

    public BarChart setChartHeaders(String[] chartDataHeader){
        StringBuilder builder =  new StringBuilder();
        builder.append("[");
        for(String s: chartDataHeader) {
            if(s.startsWith("{")) builder.append(s).append(",");
            else builder.append("'").append(s).append("',");
        }
        this.chartDataString+=(builder.toString()+"]");
        return this;
    }

    public BarChart setChartHeaders(String chartDataHeader){
        this.chartDataString+=(chartDataHeader);
        return this;
    }

    public void loadChart(){
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        setBackground(null);
        String content = "";
        switch (chartType) {
            case CLASSIC_CHART:

                content += (oHtmlOHeadChartScriptAPIUriOScript + classicChartPackageAndCallback +
                        functionToVisualisation + chartDataString +
                        "]);" +
                        "var view = new google.visualization.DataView(data);" +
                        "view.setColumns([0, 1, {calc:'stringify', sourceColumn:1," +
                        "type:'string',  role: 'annotation' }, 2]);" +
                        "var options = { title:'"+ chartTitle +"'," +
                        "bar:{ groupWidth: '95%' }," +
                        "legend: { position: 'none' },  };" +
                        "var chart = new google.visualization.BarChart(document.getElementById('barchart'));" +
                        "chart.draw(view, options); } "+ cScriptCHeadOBodyChartDivCBodyCHtml);
                break;
            case MATERIAL_CHART:
                content += (oHtmlOHeadChartScriptAPIUriOScript + materialChartPackageAndCallback +
                        functionToVisualisation + chartDataString +
                        "]);" +
                        "var options = {" +
                        "chart:{" +
                        "title: '"+ chartTitle +"'," +
                        "subtitle:'"+ chartSubtitle +"'," +
                        "}," +
                        "bars: 'horizontal' " +
                        "};" +
                        "var chart = new google.charts.Bar(document.getElementById('barchart'));" +
                        "chart.draw(data, google.charts.Bar.convertOptions(options));" +
                        "}" + cScriptCHeadOBodyChartDivCBodyCHtml);
                break;
        }


        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        requestFocusFromTouch();

        setWebViewClient(new BarChartClient());
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

        private BarChartClient() {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(progressBar!=null) progressBar.setVisibility(INVISIBLE);
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
