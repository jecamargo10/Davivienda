package com.example.javie.davivienda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Graficas extends AppCompatActivity {




    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otro);






        Log.e("result","GRAFICO");

        Intent intent = getIntent();
        String chochorramo = intent.getStringExtra("HTM");
        String id = "<tr>";
        id += chochorramo;
        id += "<tr>";
        ArrayList<String> labels = new ArrayList<String>();

        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        PieChart  mChart = (PieChart) findViewById(R.id.chart1);

        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setRotationEnabled(true);



        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader ( id) );
            int eventType = xpp.getEventType();
            String tag="";
            boolean bonice =false;
            int posicion = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
               if(eventType == XmlPullParser.END_TAG)
                {
                    if(xpp.getName().equals("tr"))
                    {
                        bonice=true;
                        Log.e("result","End tag "+xpp.getName());

                    }
                    Log.e("result","End tag "+xpp.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    if(!bonice )
                    {
                        if (xpp.getText().equals("Solicitudes cubiertas") || xpp.getText().equals("Solicitudes por cubrir")) {
                            labels.add(xpp.getText());
                        }

                    }
                    else
                    {
                        Log.e("result","TextOOOOOO "+xpp.getText());

                        if(xpp.getText().equals("")||xpp.getText().equals(" ")||xpp.getText().equals("Davivienda"))
                        {


                        }
                        else
                        {
                            Log.e("result","TextICO "+xpp.getText());
                            posicion ++;
                            try
                            {

                                if (posicion == 2) {
                                    double numero=   Double.parseDouble(xpp.getText());
                                    yVals1.add(new PieEntry((int) numero, 0));
                                }
                                else if (posicion == 3)
                                {
                                    Double.parseDouble(xpp.getText());
                                    double numero=   Double.parseDouble(xpp.getText());
                                    yVals1.add(new PieEntry((int) numero, 1));
                                }
                            }
                            catch (Exception e)
                            {
                                Log.wtf("texto", "Error de parsing");
                                e.printStackTrace();
                            }
                        //   AÑADIR NUMEROS
                        }
                               // entries.add(new BarEntry(Float.parseFloat(xpp.getText()), entries.size()));

                    }
                }
                eventType = xpp.next();
            }

        }
        catch (Exception e)
        {
            Log.e("result",e.getMessage());

        }


        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData( dataSet);
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




/**

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));




        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(labels, dataSet);
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







        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Unidad de Negocio");
        labels.add("Solicitudes de Personal");
        labels.add("Solictudes Cubiertas");
        labels.add("Solicitudes por Cubrir");
        labels.add("Dias promedio de los Procesos");
        labels.add("Procentaje de cubrimiento");


        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "Reporte de Solicitudes de Personal");
        BarChart chart = new BarChart(this.getApplicationContext());
        setContentView(chart);

        BarData data = new BarData( dataset);

        chart.setData(data);
        chart.setDescription("Reporte de Solicitudes de Personal");
*/
   //    TextView papitas= (TextView) findViewById(R.id.textView);
     //   papitas.setText(id);


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

