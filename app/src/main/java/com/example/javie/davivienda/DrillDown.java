package com.example.javie.davivienda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrillDown extends AppCompatActivity  {

    public static final int[] MY_COLORS = {
            Color.rgb(84, 124, 101), Color.rgb(64, 64, 64), Color.rgb(153, 19, 0),
            Color.rgb(38, 40, 53), Color.rgb(215, 60, 55)
    };

    private String[] fabulosoArr;
    private ArrayList<String> cositos;
    private ArrayList<String> tabliya;

    private String genteActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down);


        Log.e("result", "GRAFICO 2");

        Intent intent = getIntent();
        String roscon = intent.getStringExtra("TBL");

        TextView texto = (TextView) findViewById(R.id.textView);
        texto.setText(roscon.split(";")[0]);

        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Candidatos Externos");
        labels.add("Candidatos Internos");

            String[] coso = roscon.split(";");

                int asd1 = Integer.parseInt(coso[1]);
        int asd2 = Integer.parseInt(coso[2]);
                if (asd1 != 0) {
                    yVals1.add(new PieEntry((int) asd1, labels.get(0)));
                }

                if (asd2 != 0) {
                    yVals1.add(new PieEntry((int) asd2, labels.get(1)));

                }






        PieChart mChart = (PieChart) findViewById(R.id.chart1);



        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setRotationEnabled(true);


        PieDataSet dataSet = new PieDataSet(yVals1, "");
        // dataSet.setValueFormatter(new PercentFormatter());

        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);


        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {


            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(DrillDown.this,
                        "ALGO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }




        public class MyValueFormatter implements ValueFormatter {

            private DecimalFormat mFormat;

            public MyValueFormatter() {
                mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
            }

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // write your logic here
                return mFormat.format(value) + ""; // e.g. append a dollar-sign
            }
        }


}




/**
 *      myWebView.setWebViewClient(new WebViewClient());
 myWebView.addJavascriptInterface(new LoadListener(), "HTMLOUT");
 myWebView.getSettings().setBuiltInZoomControls(true);
 myWebView.getSettings().setSupportZoom(true);
 myWebView.setInitialScale(135);
 myWebView.loadUrl("http://stggrupobolivar.taleo.net");
 * myWebView.loadUrl("javascript:window.HTMLOUT.showHTML(http://stggrupobolivar.taleo.net);");
 myWebView.setWebViewClient(new WebViewClient() {

 public void onPageFinished(WebView view, String url) {

 Log.e("result",view.get);
 }
 });

 }
 class LoadListener{
@JavascriptInterface

public void showHTML(String html)
{
Log.e("result",html);
}
}**/
