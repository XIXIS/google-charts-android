package com.xixis.googlechartsandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.xixis.googlecharts.BarChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bar Charts");
        setSupportActionBar(toolbar);

        BarChart barChart1 = findViewById(R.id.barchart1);
        BarChart barChart = findViewById(R.id.barchart);
        ProgressBar progressColumnChart = findViewById(R.id.progressbar_columnchart);
        ProgressBar progressBarChart = findViewById(R.id.progressbar_barchart);

        String[][] chartData1 = {
                new String[] { "Copper", "8.94", "#b87333" },
                new String[] { "Silver", "10.49", "silver" },
                new String[] { "Gold", "19.30", "gold" },
                new String[] { "Platinum", "21.45", "color: #e5e4e2" }
        };
        String[][] chartData = {
                new String[] { "2014", "1000", "400" },
                new String[] { "2015", "1170", "460" },
                new String[] { "2016", "660", "1120" },
                new String[] { "2017", "1030", "540" }
        };

        String[] chartHeader1 = new String[] { "Element", "Density", "{ role: 'style' }" };
        String[] chartHeader = new String[] { "Year", "Sales", "Expenses" } ;

        String chartDataString1 = "['Element', 'Density', { role: 'style' } ]," +
                "['Copper', 8.94, '#b87333']," +
                "['Silver', 10.49, 'silver']," +
                "['Gold', 19.30, 'gold']," +
                "['Platinum', 21.45, 'color: #e5e4e2']";
        String chartDataString = "['Year', 'Sales', 'Expenses']," +
                "['2014', 1000, 400]," +
                "['2015', 1170, 460]," +
                "['2016', 660, 1120]," +
                "['2017', 1030, 540]";

        barChart.attachProgressBar(progressBarChart)
//                .setChartHeaders(chartHeader)
                .setChartData(chartDataString)
                .loadChart();
        barChart1.attachProgressBar(progressColumnChart)
//                .setChartHeaders(chartHeader1)
                .setChartData(chartDataString1)
                .loadChart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
