package ru.gelin.android.getter;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GetActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);
        WebView web = (WebView)findViewById(R.id.web);
        web.setWebChromeClient(new MyWebChromeClient());
        web.setWebViewClient(new MyWebViewClient());
        setProgressBarVisibility(true);
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

    class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            setProgress(progress * 100);
        }
    }

}