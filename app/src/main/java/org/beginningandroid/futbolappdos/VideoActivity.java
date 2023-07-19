package org.beginningandroid.futbolappdos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.net.URL;

public class VideoActivity extends AppCompatActivity {
    // on below line creating a variable for web view.
    private WebView webView;
    ///public FrameLayout fullscreenContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //// Setting Full screen from Futbolero plus App.
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().getDecorView().setSystemUiVisibility(5894);
        if (Build.VERSION.SDK_INT >= 30) {
            getWindow().getInsetsController().setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
            /*
            getWindow().getInsetsController().setSystemBarsBehavior(2);
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());

             */
        }


        ///
        setContentView(R.layout.activity_video);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // on below line initializing web view with id.
        webView = findViewById(R.id.webview);

        ///fullscreenContainer = findViewById(R.id.fullscreenContainer);
        //Activating Javascript
        //WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);

        // on below line setting web view client.
        webView.setWebViewClient(new WebClient());

        // on below line setting web chrome client for web view.
        webView.setWebChromeClient(new WebChromeClient());
        ///webView.setWebChromeClient(new CustomWebChromeClient(VideoActivity.this));
        // on below line getting web settings.
        WebSettings webSettings = webView.getSettings();

        // on below line setting java script enabled to true.
        webSettings.setJavaScriptEnabled(true);
        //
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        // on below line setting file access to true.
        webSettings.setAllowFileAccess(true);

        Intent receiverIntent = getIntent();
        String receivedValue =  receiverIntent.getStringExtra("KEY_SENDER") + "&id=1251MYGhjUuoYj&id=1181&ram=4765$%&m2001HTgj";


        //URL link = new URL("https://arenacdmexico.com/canales/dtv2b.html?id=1251&", receivedValue );




        // on below line setting url for the web page which we have to load in our web view.
        webView.loadUrl(receivedValue);
        //webView.loadUrl(link.toString());
    }

    // on below line creating a class for web chrome client.
    class WebChromeClient extends android.webkit.WebChromeClient {
        // on below line creating variables.
        private View customView;
        private android.webkit.WebChromeClient.CustomViewCallback customViewCallback;
        private int originalOrientation;
        private int originalSystemVisibility;

        WebChromeClient() {
        }

        @Nullable
        @Override
        public Bitmap getDefaultVideoPoster() {

            // on below line returning our resource from bitmap factory.
            if (customView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        @Override
        public void onHideCustomView() {

            // on below line removing our custom view and setting it to null.
            ((FrameLayout) getWindow().getDecorView()).removeView(customView);
            this.customView = null;

            // on below line setting system ui visibility to original one and setting orientation for it.
            getWindow().getDecorView().setSystemUiVisibility(this.originalSystemVisibility);
            setRequestedOrientation(this.originalOrientation);

            // on below line setting custom view call back to null.
            this.customViewCallback.onCustomViewHidden();
            this.customViewCallback = null;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (this.customView != null) {
                onHideCustomView();
                return;
            }
            // on below line initializing all variables.
            this.customView = view;
            this.originalSystemVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.originalOrientation = getRequestedOrientation();
            this.customViewCallback = callback;
            FrameLayout decorView = (FrameLayout) getWindow().getDecorView();decorView.addView(this.customView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
    // on below line creating a class for Web Client.
    class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}