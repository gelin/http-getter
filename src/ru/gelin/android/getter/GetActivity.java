package ru.gelin.android.getter;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GetActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);
        WebView web = (WebView)findViewById(R.id.web);
        web.setWebViewClient(new MyWebViewClient());
        web.loadUrl(getIntent().getDataString());
    }

    static class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //http://stackoverflow.com/questions/4066438/android-webview-how-to-handle-redirects-in-app-instead-of-opening-a-browser
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //http://stackoverflow.com/questions/7416096/android-webview-not-loading-https-url
            // this will ignore the Ssl error and will go forward to your site
            if (handler != null) {
                handler.proceed();
            }
        }

    }

}