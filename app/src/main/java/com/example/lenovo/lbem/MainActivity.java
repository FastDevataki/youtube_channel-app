package com.example.lenovo.lbem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    WebView wb;
    ProgressBar pb;
    ProgressBar hp;
    String url="https://m.youtube.com/channel/UChAnNC-4jdoIE9HdJvjRN-Q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        wb=(WebView) findViewById(R.id.bweb);
        pb=(ProgressBar) findViewById(R.id.progressBar);
        hp=(ProgressBar) findViewById(R.id.progressBar1);

        if(isNetworkAvailable()==true){
            wb.loadUrl(url);
        }
        else{
            pb.setVisibility(View.GONE);
            Snackbar snackbar=Snackbar.make(toolbar,"No Internet Connection..!!",Snackbar.LENGTH_LONG)
                    .setAction("Retry",new View.OnClickListener(){

                        @Override
                        public void onClick(View view) {
                            Intent i= new Intent(MainActivity.this,MainActivity.class);
                            finish();
                            startActivity(i);
                        }
                    });

            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        }
        WebSettings webSettings=wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setWebViewClient(new MyWebViewClient());

        wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                hp.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                    hp.setVisibility(View.GONE);
                }
            }
        });
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
        if (id == R.id.action_open) {
            boolean isAppExists;
            isAppExists=isInstalled("com.google.android.youtube");
            if(isAppExists==true){
                Intent i=new Intent("android.intent.action.VIEW", Uri.parse(url));
                startActivity(i);
            }
            else{
                Toast.makeText(this,"YouTube App not Installed",Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK )&& wb.canGoBack()){
            wb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isInstalled(String uri){
        PackageManager pm= getPackageManager();
        boolean isIn;
        try
        {
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            isIn=true;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            isIn=false;
        }
        return isIn;
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Exit")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert=builder.create();
        alert.setTitle("Exit ?");
        alert.show();
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo= connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }


}
