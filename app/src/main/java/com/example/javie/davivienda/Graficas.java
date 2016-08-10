package com.example.javie.davivienda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Graficas extends AppCompatActivity {









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otro);

        Log.e("result","GRAFICO");

        Intent intent = getIntent();
        String id = intent.getStringExtra("HTM");

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader ( id) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.e("result","Empiezo");

                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    Log.e("result","Finalizo");
                } else if(eventType == XmlPullParser.START_TAG) {
                    Log.e("result",xpp.getName());

                    System.out.println("Start tag "+xpp.getName());
                } else if(eventType == XmlPullParser.END_TAG)
                {
                    Log.e("result","End tag "+xpp.getName());

                    System.out.println();
                } else if(eventType == XmlPullParser.TEXT) {
                    Log.e("result","Text "+xpp.getText());
                }
                eventType = xpp.next();
            }

        }
        catch (Exception e)
        {
            Log.e("result",e.getMessage());

        }



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

     //   TextView papitas= (TextView) findViewById(R.id.textView);
       // papitas.setText(id);

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

