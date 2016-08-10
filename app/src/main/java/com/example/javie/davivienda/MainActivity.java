package com.example.javie.davivienda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      final   ProgressDialog dialog = new ProgressDialog(MainActivity.this);




        final    WebView myWebView = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.setInitialScale(135);
        myWebView.loadUrl("http://stggrupobolivar.taleo.net");


        myWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        myWebView.setWebViewClient(new WebViewClient()
                                 {
                                     @Override
                                     public void onPageFinished(WebView view, String url)
                                     {
                                         Log.e("result", url);

                                         if(url.contains("https://reporting-bichm05.taleo.net/analytics/saw.dll?Dashboard")) {
                                             myWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                                                     "('&lt;html&gt;'+document.getElementsByTagName('html')[0].innerHTML+'&lt;/html&gt;');");

                                             if (dialog.isShowing()) {
                                                 dialog.dismiss();
                                             }
                                          }
                                         else if (url.contains("stggrupobolivar.taleo.net/smartorg/smartorg/common/toc.jsf"))
                                         {
                                             Log.e("result","ENTRO BI");

                                           //  myWebView.loadUrl("javascript:clickFunction(){var form = document.getElementById(\"menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\"); form.onclick(); })() ");
                                        //     myWebView.loadUrl("javascript:document.getElementById(\"menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\").click();");

                                             myWebView.loadUrl("javascript:document.getElementById('menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink').click();");


                                             // myWebView.loadUrl("$(\"#menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\").trigger(\"onclick\");");

                                             //myWebView.loadUrl("$(\"#menuTemplate-menuForm-gotoSubView-quickAccessBlock\").trigger(\"onclick\");");


           //    myWebView.loadUrl("javascript:document.getElementById(\"menuTemplate-menuForm-gotoSubView-quickAccessBlock\").onclick();");

                                         }
                                        else if(url.contains("https://stggrupobolivar.taleo.net/smartorg/iam/accessmanagement")) {
                                             Log.e("result", "Me logeo");
                                             myWebView.loadUrl("javascript:document.getElementById('dialogTemplate-dialogForm-content-login-name1').value='Admin';" +
                                                     "javascript:document.getElementById('dialogTemplate-dialogForm-content-login-password').value='Welcome1';" +
                                                     "javascript:document.getElementById('dialogTemplate-dialogForm-content-login-defaultCmd').click();");


                                         }
//                                         myWebView.loadUrl("javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                                     }
                                 }

        );
        dialog.setMessage("Loading..Please wait.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        myWebView.loadUrl("http://stggrupobolivar.taleo.net");
    }
    class MyJavaScriptInterface
    {
        private Context ctx;
        MyJavaScriptInterface(Context ctx)
        {
            this.ctx = ctx;
        }
        @JavascriptInterface

        public void showHTML(String html)
        {

            if(html.contains("686")&&html.contains("146")&&html.contains("540"))
            {

                Intent intent = new Intent(MainActivity.this,Graficas.class);
                String substr = html.substring(html.indexOf("<td class=\"PTCHC0\""),html.indexOf("%<"));

                intent.putExtra("HTM",substr);

                startActivity(intent);

                finish();

               //

            }
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


    }





























     File data = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator);
     File logFile = new File(data, "highscore.txt");
     if (!logFile.exists())
     {
     Log.e("result","BUSCO");

     try
     {
     logFile.createNewFile();
     }
     catch (IOException e)
     {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
     }
     try
     {
     Log.e("result","Escribo");

     //BufferedWriter for performance, true to set append to file flag
     BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
     Log.e("result","asd");

     buf.append(html);
     Log.e("result","xxx");

     buf.newLine();
     Log.e("result","zzz");

     buf.close();
     Log.e("result","YEAH BITCHES");

     }
     catch (IOException e)
     {
     Log.e("result",e.getLocalizedMessage());
     Log.e("result",e.getMessage());


     // TODO Auto-generated catch block
     e.printStackTrace();
     }

     **/

