package com.nino.fullstudyguide;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
public class MainActivity extends Activity {
  WebView web;
  @Override protected void onCreate(Bundle b){
    super.onCreate(b);
    web = new WebView(this);
    setContentView(web);
    WebSettings s = web.getSettings();
    s.setJavaScriptEnabled(true);
    s.setAllowFileAccess(true);
    s.setAllowFileAccessFromFileURLs(true);
    s.setAllowUniversalAccessFromFileURLs(true);
    s.setDomStorageEnabled(true);
    web.addJavascriptInterface(new NinoBridge(this), "NINO");
    web.setWebViewClient(new WebViewClient());
    web.loadUrl("file:///android_asset/index.html");
  }
}
