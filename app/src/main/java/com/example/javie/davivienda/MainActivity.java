package com.example.javie.davivienda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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




    // Use a unique request code for each use case
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an Intent to start LoginActivity
        final Intent intent = new Intent(this, LoginActivity.class);

        // Start LoginActivity with the request code
        startActivityForResult(intent, REQUEST_CODE_EXAMPLE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf("texto","Llega a OnActivityResult");
        // First we need to check if the requestCode matches the one we used.
        if(requestCode == REQUEST_CODE_EXAMPLE) {
            Log.wtf("texto","requestCode == REQUEST_CODE_EXAMPLE");
            // The resultCode is set by the DetailActivity
            // By convention RESULT_OK means that what ever
            // Activity did was successful
            if(resultCode == Activity.RESULT_OK) {
                // Get the result from the returned Intent
                //String username = data.getStringExtra(LoginActivity.USERNAME);
                //String password = data.getStringExtra(LoginActivity.PASSWORD);
                Log.wtf("texto","Activity.RESULT_OK");



                all();


            } else {
                // Activity was not successful. No data to retrieve.
                Log.wtf("texto","Activity.RESULT NOT OK");
            }
        }
    }

    private void all(){

        SharedPreferences prefs = getSharedPreferences("papas",Context.MODE_PRIVATE);
        final String username = prefs.getString(LoginActivity.USERNAME, "%");
        final String password = prefs.getString(LoginActivity.PASSWORD, "%");
        Log.wtf("texto","Username: " + username + " - Password: " + password);
        if(!username.equals("%") && !password.equals("%")){
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            final WebView myWebView = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            myWebView.setWebViewClient(new WebViewClient());
            myWebView.getSettings().setBuiltInZoomControls(true);
            myWebView.getSettings().setSupportZoom(true);
            myWebView.setInitialScale(135);
            myWebView.loadUrl("http://stggrupobolivar.taleo.net");


            myWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
            myWebView.setWebViewClient(new WebViewClient() {
                                           @Override
                                           public void onPageFinished(WebView view, String url) {
                                               Log.e("result", url);

                                               if (url.contains("https://reporting-bichm05.taleo.net/analytics/saw.dll?Dashboard")) {
                                                   myWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                                                           "('&lt;html&gt;'+document.getElementsByTagName('html')[0].innerHTML+'&lt;/html&gt;');");

                                                   if (dialog.isShowing()) {
                                                       dialog.dismiss();
                                                   }
                                               } else if (url.contains("stggrupobolivar.taleo.net/smartorg/smartorg/common/toc.jsf")) {
                                                   Log.e("result", "ENTRO BI");

                                                   //  myWebView.loadUrl("javascript:clickFunction(){var form = document.getElementById(\"menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\"); form.onclick(); })() ");
                                                   //     myWebView.loadUrl("javascript:document.getElementById(\"menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\").click();");

                                                   myWebView.loadUrl("javascript:document.getElementById('menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink').click();");


                                                   // myWebView.loadUrl("$(\"#menuTemplate-menuForm-globalHeader-pageRibbonSubView-j_id_jsp_1407348119_29pc12-1-ribbonItemLink\").trigger(\"onclick\");");

                                                   //myWebView.loadUrl("$(\"#menuTemplate-menuForm-gotoSubView-quickAccessBlock\").trigger(\"onclick\");");


                                                   //    myWebView.loadUrl("javascript:document.getElementById(\"menuTemplate-menuForm-gotoSubView-quickAccessBlock\").onclick();");

                                               } else if (url.contains("https://stggrupobolivar.taleo.net/smartorg/iam/accessmanagement")) {
                                                   Log.e("result", "Me logeo");
                                                   myWebView.loadUrl("javascript:document.getElementById('dialogTemplate-dialogForm-content-login-name1').value='"+username+"';" +
                                                           "javascript:document.getElementById('dialogTemplate-dialogForm-content-login-password').value='"+password+"';" +
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
        }else{
            Toast.makeText(getBaseContext(), "username or pasword couldn't be retrieved", Toast.LENGTH_LONG).show();
        }


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

