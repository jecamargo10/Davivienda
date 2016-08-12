package com.example.javie.davivienda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.github.mikephil.charting.data.BarEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Pie extends AppCompatActivity {










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otro);






        Log.e("result","GRAFICO");

        Intent intent = getIntent();
        String chochorramo = intent.getStringExtra("HTM");
        String id = "<tr>";
        id +=chochorramo;
        id += "<tr>";
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader ( id) );
            int eventType = xpp.getEventType();
            String tag="";
            boolean bonice =false;

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
                    if(!bonice)
                    {
                        labels.add(xpp.getText());

                    }
                    else
                    {
                        if(xpp.getText().equals("")||xpp.getText().equals(" ")||xpp.getText().equals("Davivienda"))
                        {


                        }
                        else
                        {
                            Log.e("result","Text "+xpp.getText());

                            try
                            {

                            }
                            catch (Exception e)
                            {


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

        for(int i = 0; i<labels.size();i++ )
        {

            Log.e("result",labels.get(i));

        }


/**

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

