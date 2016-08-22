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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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

public class Graficas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };

private  String [] fabulosoArr;
    Spinner     spinnerOsversions;
private ArrayList<String> cositos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otro);






        Log.e("result","GRAFICO");

        Intent intent = getIntent();
        String chochorramo = intent.getStringExtra("HTM");

        String id = "<tr>";
        id += chochorramo;
        id += "\"></td></tr>";

        cositos =new ArrayList<String>();




        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader ( id) );
            int eventType = xpp.getEventType();
            String tag="";
            boolean bonice =false;
            int posicion = 0;
            String anadidura= "";
            boolean contando = false;
            boolean primeraVez = false;
            String tagActual = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.END_TAG)
                {
                //    Log.e("result","End tag "+xpp.getName());

                    if(xpp.getName().equals("tr"))
                    {
                        bonice=true;


                        //    Log.e("result","End tag "+xpp.getName());

                    }

                }

                else if(eventType == XmlPullParser.START_TAG)
                {
                    tagActual =xpp.getName();
               //     Log.e("result","START TAG "+xpp.getName());
                    if(!primeraVez && xpp.getName().equals("td")&& bonice)
                    {

                        primeraVez = true;
                    }
                    else if(xpp.getName().equals("td")&& bonice)
                    {
                     //   Log.wtf("HABER",tagActual  );

                        posicion ++;


                    }





                }

                else if(eventType == XmlPullParser.TEXT) {


                    if(!bonice )
                    {

                        if (!xpp.getText().equals("")) {
                            if(!xpp.getText().equals(" "))
                            {
                             //   Log.e("result","FUERA TR "+xpp.getText());

                            }}

                    }
                    else
                    {
                  //   Log.wtf("TITULO",xpp.getText()  );
                    //    Log.wtf("POSICION",posicion+""  );


                        if(xpp.getText().isEmpty()||xpp.getText().equals("")||xpp.getText().equals(" "))
                        {

                        //    Log.wtf("ENTROOOOOOOOOOOOo",posicion+""  );


                        }
                        else
                        {

                            try
                            {

                                if (posicion==0 & tagActual.equals("td") )
                                {
                                  //  Log.wtf("POS0",xpp.getText()  );

                                 //   Log.wtf("TAG0",tagActual  );

                                    String temp = xpp.getText() ;
                                    if(temp.length()>3) {
                                        temp +=";";
                                        anadidura = temp;

                                     //   Log.wtf("POS0", anadidura);
                                    }

                                }

                                else  if (posicion == 1) {
                                 //   Log.wtf("POS1",xpp.getText()  );
                              //      Log.wtf("TAG1",tagActual  );



                                    String temp =   xpp.getText().replace(",",".");

                                    if(temp.length()>3) {

                                        anadidura = anadidura + (temp + ";");

                                     //   Log.wtf("POS1", anadidura);

                                    //    double numero = Double.parseDouble(temp);
                                      //  yVals1.add(new PieEntry((int) numero, 0));
                                    }
                                }
                                else if (posicion == 2)
                                {
                              //      Log.wtf("POS2",xpp.getText()  );
                               //     Log.wtf("TAG2",tagActual  );

                                    posicion = -1 ;
                                    String temp =   xpp.getText().replace(",",".");
                                    anadidura += temp;
                                //    Log.wtf("ARR",anadidura  );
                                    cositos.add(anadidura);
                                    anadidura = "";

                                //    double numero=   Double.parseDouble(temp);
                                  //  yVals1.add(new PieEntry((int) numero, 1));
                                }
                            }
                            catch (Exception e)
                            {
                                Log.wtf("texto", "Error de parsing" + e.getMessage());
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

        Log.wtf("ARREGLO", "Fabuloso" + cositos.size());
        fabulosoArr= new String[cositos.size()] ;
    for(int j = 0; j < cositos.size(); j++)
        {
            String coso = cositos.get(j);
            fabulosoArr[j]=coso.split(";")[0];


        }
             spinnerOsversions = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, fabulosoArr);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
        spinnerOsversions.setOnItemSelectedListener(this);

/**
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


    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinnerOsversions.setSelection(position);
        String selState = (String) spinnerOsversions.getSelectedItem();
        Log.e("result",selState);



        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Solicitudes cubiertas");
        labels.add("Solicitudes por cubrir");
        for(int j = 0; j < cositos.size(); j++)
        {

            String [] coso = cositos.get(j).split(";");

            if(coso[0].equals(selState)) {
                double asd1 = Double.parseDouble(coso[1]);
                double asd2 = Double.parseDouble(coso[2]);

                yVals1.add(new PieEntry((int) asd1, 0));
                yVals1.add(new PieEntry((int) asd2, 1));
                break;
            }





        }


        PieChart  mChart = (PieChart) findViewById(R.id.chart1);

        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setRotationEnabled(true);






        PieDataSet dataSet = new PieDataSet(yVals1,"Reporte de solicitudes");
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


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {



            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(Graficas.this,
                      e.getData().toString()+ " = " + e.getX() + e.getY() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
