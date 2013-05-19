package ru.gelin.android.getter;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.webkit.*;

public class GetActivity extends Activity {

    Preferences preferences;

    void requestFeatures() {
        requestWindowFeature(Window.FEATURE_PROGRESS);
        if (Build.VERSION.SDK_INT < 11) {
            requestWindowFeature(Window.FEATURE_LEFT_ICON);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        requestFeatures();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);

        this.preferences = new Preferences(this);

        String title = this.preferences.getTitle();
        if (title != null && title.length() > 0) {
            setTitle(this.preferences.getTitle());
        }

        setIcon(this.preferences.getIcon());

        //http://stackoverflow.com/questions/3462582/display-the-android-webviews-favicon
        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());

        WebView web = (WebView)findViewById(R.id.web);
        web.setWebChromeClient(new MyWebChromeClient());
        web.setWebViewClient(new MyWebViewClient());
        setProgressBarVisibility(true);
        web.loadUrl(getIntent().getDataString());
    }

    void setIcon(Drawable icon) {
        if (Build.VERSION.SDK_INT < 11) {
            setFeatureDrawable(Window.FEATURE_LEFT_ICON, icon);
        } else if (Build.VERSION.SDK_INT >= 14) {
            getActionBar().setIcon(icon);
        }
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

        @Override
        public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            setProgress(progress * 100);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTitle(title);
            preferences.setTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            preferences.setIcon(icon);
            setIcon(preferences.getIcon());
        }

    }

}