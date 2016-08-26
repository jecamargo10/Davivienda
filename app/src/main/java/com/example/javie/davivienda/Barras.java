package com.example.javie.davivienda;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Barras extends AppCompatActivity  {

    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };

private  String [] fabulosoArr;
private ArrayList<String> cositos;
    private ArrayList<String> tabliya;

    private String genteActual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barras);


        Log.e("result", "GRAFICO");

        Intent intent = getIntent();
        String chochorramo = intent.getStringExtra("HTM");
        String choclito = intent.getStringExtra("TBL");
        String id = "<tr>";
        id += chochorramo;
        id += "\"></td></tr>";

        String tbl2 = "<tr>";
        tbl2 += choclito;
        tbl2 += "\"></td></tr>";


        cositos = new ArrayList<String>();
        tabliya = new ArrayList<String>();


        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(id));
            int eventType = xpp.getEventType();
            String tag = "";
            boolean bonice = false;
            int posicion = 0;
            String anadidura = "";
            boolean contando = false;
            boolean primeraVez = false;
            String tagActual = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.END_TAG) {
                    //    Log.e("result","End tag "+xpp.getName());

                    if (xpp.getName().equals("tr")) {
                        bonice = true;


                        //    Log.e("result","End tag "+xpp.getName());

                    }

                } else if (eventType == XmlPullParser.START_TAG) {
                    tagActual = xpp.getName();
                    //     Log.e("result","START TAG "+xpp.getName());
                    if (!primeraVez && xpp.getName().equals("td") && bonice) {

                        primeraVez = true;
                    } else if (xpp.getName().equals("td") && bonice) {
                        //   Log.wtf("HABER",tagActual  );

                        posicion++;


                    }


                } else if (eventType == XmlPullParser.TEXT) {


                    if (!bonice) {

                        if (!xpp.getText().equals("")) {
                            if (!xpp.getText().equals(" ")) {
                                //   Log.e("result","FUERA TR "+xpp.getText());

                            }
                        }

                    } else {
                        //   Log.wtf("TITULO",xpp.getText()  );
                        //    Log.wtf("POSICION",posicion+""  );


                        if (xpp.getText().isEmpty() || xpp.getText().equals("") || xpp.getText().equals(" ")) {

                            //    Log.wtf("ENTROOOOOOOOOOOOo",posicion+""  );


                        } else {

                            try {

                                if (posicion == 0 & tagActual.equals("td")) {
                                    //  Log.wtf("POS0",xpp.getText()  );

                                    //   Log.wtf("TAG0",tagActual  );

                                    String temp = xpp.getText();
                                    if (temp.length() > 3) {
                                        temp += ";";
                                        anadidura = temp;

                                        //   Log.wtf("POS0", anadidura);
                                    }

                                } else if (posicion == 1) {
                                    //   Log.wtf("POS1",xpp.getText()  );
                                    //      Log.wtf("TAG1",tagActual  );


                                    String temp = xpp.getText().replace(",", ".");

                                    if (temp.length() > 3) {

                                        anadidura = anadidura + (temp + ";");

                                        //   Log.wtf("POS1", anadidura);

                                        //    double numero = Double.parseDouble(temp);
                                        //  yVals1.add(new PieEntry((int) numero, 0));
                                    }
                                } else if (posicion == 2) {
                                    //      Log.wtf("POS2",xpp.getText()  );
                                    //     Log.wtf("TAG2",tagActual  );

                                    posicion = -1;
                                    String temp = xpp.getText().replace(",", ".");
                                    anadidura += temp;
                                    //    Log.wtf("ARR",anadidura  );
                                    cositos.add(anadidura);
                                    anadidura = "";

                                    //    double numero=   Double.parseDouble(temp);
                                    //  yVals1.add(new PieEntry((int) numero, 1));
                                }
                            } catch (Exception e) {
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

        } catch (Exception e) {
            Log.e("result", e.getMessage());

        }

        Log.wtf("ARREGLO", "Fabuloso" + cositos.size());
        fabulosoArr = new String[cositos.size()];
        for (int j = 0; j < cositos.size(); j++) {
            String coso = cositos.get(j);
            fabulosoArr[j] = coso.split(";")[0];


        }


        //SEGUNDAS GRAFICAS
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(tbl2));
            int eventType = xpp.getEventType();
            String tag = "";
            boolean bonice = false;
            int posicion = 0;
            String anadidura = "";
            boolean contando = false;
            boolean primeraVez = false;
            String tagActual = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.END_TAG) {
                    //    Log.e("result","End tag "+xpp.getName());

                    if (xpp.getName().equals("tr")) {
                        bonice = true;


                        //    Log.e("result","End tag "+xpp.getName());

                    }

                } else if (eventType == XmlPullParser.START_TAG) {
                    tagActual = xpp.getName();
                    //     Log.e("result","START TAG "+xpp.getName());
                    if (!primeraVez && xpp.getName().equals("td") && bonice) {

                        primeraVez = true;
                    } else if (xpp.getName().equals("td") && bonice) {
                        //   Log.wtf("HABER",tagActual  );

                        posicion++;


                    }


                } else if (eventType == XmlPullParser.TEXT) {


                    if (!bonice) {

                        if (!xpp.getText().equals("")) {
                            if (!xpp.getText().equals(" ")) {
                                //   Log.e("result","FUERA TR "+xpp.getText());

                            }
                        }

                    } else {
                        //   Log.wtf("TITULO",xpp.getText()  );
                        //    Log.wtf("POSICION",posicion+""  );


                        if (xpp.getText().isEmpty() || xpp.getText().equals("") || xpp.getText().equals(" ")) {

                            //    Log.wtf("ENTROOOOOOOOOOOOo",posicion+""  );


                        } else {

                            try {

                                if (posicion == 0 & tagActual.equals("td")) {
                                    //  Log.wtf("POS0",xpp.getText()  );

                                    //   Log.wtf("TAG0",tagActual  );

                                    String temp = xpp.getText();
                                    if (temp.length() > 3) {
                                        temp += ";";
                                        anadidura = temp;

                                        //   Log.wtf("POS0", anadidura);
                                    }

                                } else if (posicion == 1) {
                                    //   Log.wtf("POS1",xpp.getText()  );
                                    //      Log.wtf("TAG1",tagActual  );


                                    String temp = xpp.getText().replace(",", ".");

                                    if (isNumeric(temp)) {

                                        anadidura = anadidura + (temp + ";");

                                        //   Log.wtf("POS1", anadidura);

                                        //    double numero = Double.parseDouble(temp);
                                        //  yVals1.add(new PieEntry((int) numero, 0));
                                    }
                                } else if (posicion == 2) {
                                    //      Log.wtf("POS2",xpp.getText()  );
                                    //     Log.wtf("TAG2",tagActual  );

                                    posicion = -1;
                                    String temp = xpp.getText().replace(",", ".");
                                    anadidura += temp;
                                    //    Log.wtf("ARR",anadidura  );
                                    tabliya.add(anadidura);
                                    anadidura = "";

                                    //    double numero=   Double.parseDouble(temp);
                                    //  yVals1.add(new PieEntry((int) numero, 1));
                                }
                            } catch (Exception e) {
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

        } catch (Exception e) {
            Log.e("result", e.getMessage());

        }

        Log.wtf("ARREGLO2", "HIPER" + tabliya.size());


        BarChart  barChart = (BarChart ) findViewById(R.id.chart1);
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();


        for (int j = 0; j < cositos.size(); j++) {

            String[] coso = cositos.get(j).split(";");
            double asd1 = Double.parseDouble(coso[1]);
            double asd2 = Double.parseDouble(coso[2]);
            if (asd1 < 1) {
                asd1 = 1 + (double) (Math.random() * (((asd1-1) -1) + 1));
            }

            double porcentaje = (asd1 * 100) / asd2;


            entries.add(new BarEntry( j, (float) porcentaje));
        }

        ArrayList<String> colors = new ArrayList<String>();
                BarDataSet dataset = new BarDataSet(entries, "JESUS");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData data = new BarData(dataset);
        data.setDrawValues(true);
        data.setBarWidth(0.9f);

        barChart.setData(data);

        barChart.setVisibleXRangeMaximum(10f);


        //      barChart.setFitBars(true);
        final String[] quarters = new String[cositos.size()] ;


        for (int j = 0; j < cositos.size(); j++) {

            String[] coso = cositos.get(j).split(";");
            quarters[j]=coso[0];

        }


        AxisValueFormatter formatter = new AxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
            @Override
            public int getDecimalDigits() {  return 0; }
        };


        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);








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


    public static boolean isNumeric(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(Exception nfe)
        {
            return false;
        }
        return true;
    }


}

