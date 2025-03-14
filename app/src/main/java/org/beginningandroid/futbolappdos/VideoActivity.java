package org.beginningandroid.futbolappdos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class VideoActivity extends AppCompatActivity {
    // on below line creating a variable for web view.
    private WebView webView;
    ///public FrameLayout fullscreenContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ad Blocker settings
        AdBlocker.init(this);

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
        // Haciendo ajustes para que la orientación horizontal se acomode de acuerdo al sensor
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //


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
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                // Bloqueamos la creación de nuevas ventanas (pop-ups)
                return false;


            }


        });
        ///webView.setWebChromeClient(new CustomWebChromeClient(VideoActivity.this));
        // on below line getting web settings.
        WebSettings webSettings = webView.getSettings();

        // on below line setting java script enabled to true.
        webSettings.setJavaScriptEnabled(true);
        //Deshabilitar ventanas emergentes de JavaScript
        //Si el problema proviene de pop-ups, puedes evitar que se abran nuevas ventanas:

        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);

        webSettings.setSupportMultipleWindows(false);
        ///////

        webSettings.setMediaPlaybackRequiresUserGesture(false);

        // on below line setting file access to true.
        webSettings.setAllowFileAccess(true);

        Intent receiverIntent = getIntent();
        String receivedValue = receiverIntent.getStringExtra("KEY_SENDER") + "";


        //URL link = new URL("https://arenacdmexico.com/canales/dtv2b.html?id=1251&", receivedValue );


        // on below line setting url for the web page which we have to load in our web view.
        webView.loadUrl(receivedValue);
        //webView.loadUrl(link.toString());
    }

    //
    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Aunque la actividad siempre se muestra en horizontal, aquí puedes ajustar detalles de la UI
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Actualizamos la UI para que se vea correctamente en cualquier landscape (normal o invertido)
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

     */


    //

    // on below line creating a class for web chrome client.
    //static
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
        /*
        @Override
        public void onHideCustomView() {
            if (customView == null) {
                return;
            }
            ((ViewGroup) getWindow().getDecorView()).removeView(customView);
            customView = null;
            customViewCallback.onCustomViewHidden();
            webView.setVisibility(View.VISIBLE);
            // Restauramos la UI normal
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

         */

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
            FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
            decorView.addView(this.customView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }

         /*

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (customView != null) {
                callback.onCustomViewHidden();
                return;
            }
            customView = view;
            customViewCallback = callback;
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            // Ocultamos el WebView y mostramos la vista personalizada
            webView.setVisibility(View.GONE);
            ((ViewGroup) getWindow().getDecorView()).addView(customView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }

          */


    }



    // on below line creating a class for Web Client.
    static class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        ///
        // Para dispositivos con API menor a 21
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("intent://")) {
                // Bloquea la redirección no deseada
                return true;
            }
            // Para otras URLs, las carga normalmente
            view.loadUrl(url);
            return false;
        }

        // Para dispositivos con API 21 en adelante
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.startsWith("intent://")) {
                // Bloquea la redirección no deseada
                return true;
            }
            // Permite que el WebView maneje la URL normalmente
            return false;
        }

        private Map<String, Boolean> loadedUrls = new HashMap<>();

        @Nullable

        // Interceptamos solicitudes para bloquear anuncios usando AdBlocker
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            boolean isAd;
            if (!loadedUrls.containsKey(url)) {
                isAd = AdBlocker.isAd(url);
                loadedUrls.put(url, isAd);
            } else {
                isAd = loadedUrls.get(url);
            }
            if (isAd) {
                // Si se detecta un anuncio, retornamos una respuesta vacía
                return AdBlocker.createEmptyResource();
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            boolean isAd;
            if (!loadedUrls.containsKey(url)) {
                isAd = AdBlocker.isAd(url);
                loadedUrls.put(url, isAd);
            } else {
                isAd = loadedUrls.get(url);
            }
            if (isAd) {
                return AdBlocker.createEmptyResource();
            }
            return super.shouldInterceptRequest(view, request);
        }


        //Blocker settings
        /*
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }



        private Map<String, Boolean> loadedUrls = new HashMap<>();
        @Nullable

            @Override
            public WebResourceResponse shouldInterceptRequest (WebView view, String url){
                boolean ad;
                if (!loadedUrls.containsKey(url)) {
                    ad = AdBlocker.isAd(url);
                    loadedUrls.put(url, ad);
                } else {
                    ad = loadedUrls.get(url);
                }
                return ad ? AdBlocker.createEmptyResource() :
                        super.shouldInterceptRequest(view, url);
            }

         */


        //
        /*
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = "javascript:(function() {" +
                    // Oculta el elemento específico por su selector  // Selector de Devtools F12 = #vplayer > div.afs_ads.ad-placement
                    "var adSpecific = document.querySelectorAll('#vplayer > div.afs_ads.ad-placement');" +
                    "for (var i = 0; i < adSpecific.length; i++) {" +
                    "    adSpecific[i].style.display = 'none';" +
                    "}" +
                    // Oculta elementos genéricos que puedan contener anuncios
                    "var spans = document.getElementsByTagName('span');" +
                    "for (var i = 0; i < spans.length; i++) {" +
                    "    var text = spans[i].innerText.trim().toLowerCase();" +
                    "    if(text.indexOf('ad') !== -1 || text.indexOf('ads') !== -1 || " +
                    "       text.indexOf('publicidad') !== -1 || text.indexOf('sponsored') !== -1) {" +
                    "        var container = spans[i].parentNode;" +
                    "        if(container) { container.style.display = 'none'; }" +
                    "    }" +
                    "}" +
                    "})()";
            view.evaluateJavascript(js, null);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = "javascript:(function() {" +
                    "var ads = document.querySelectorAll('body > div > div:nth-child(1)');" +
                    "for (var i = 0; i < ads.length; i++) {" +
                    "    ads[i].style.display = 'none';" +
                    "}" +
                    "})()";
            view.evaluateJavascript(js, null);
        }

        ///
        @Override
public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    // Ajusta los selectores de CSS según los elementos que quieras ocultar
    String js = "javascript:(function() {" +
            "var adElements = document.querySelectorAll('.ad, .ads, .floating-ad, #ad-banner');" +
            "for(var i=0; i<adElements.length; i++) {" +
            "    adElements[i].style.display = 'none';" +
            "}" +
            "})()";
    view.evaluateJavascript(js, null);
}

        ///



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = "javascript:(function() {" +
                    // Oculta el elemento específico por su selector  // Selector de Devtools F12 = #vplayer > div.afs_ads.ad-placement
                    "var adSpecific = document.querySelectorAll('body > div > div:nth-child(1) > div');" +
                    "for (var i = 0; i < adSpecific.length; i++) {" +
                    "    adSpecific[i].style.display = 'none';" +
                    "}" +
                    // Oculta elementos genéricos que puedan contener anuncios
                    "var spans = document.getElementsByTagName('span');" +
                    "for (var i = 0; i < spans.length; i++) {" +
                    "    var text = spans[i].innerText.trim().toLowerCase();" +
                    "    if(text.indexOf('ad') !== -1 || text.indexOf('ads') !== -1 || " +
                    "       text.indexOf('!important') !== -1 || text.indexOf('!important') !== -1) {" +
                    "        var container = spans[i].parentNode;" +
                    "        if(container) { container.style.display = 'none'; }" +
                    "    }" +
                    "}" +
                    "})()";
            view.evaluateJavascript(js, null);
        }

         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = "javascript:(function() {" +
                    // Oculta el elemento específico por su selector  // Selector de Devtools F12 = #vplayer > div.afs_ads.ad-placement
                    "var adSpecific = document.querySelectorAll('body > div > div > div > span > span');" +
                    "for (var i = 0; i < adSpecific.length; i++) {" +
                    "    adSpecific[i].style.display = 'none';" +
                    "}" +
                    // Oculta elementos genéricos que puedan contener anuncios
                    "var spans = document.getElementsByTagName('span');" +
                    "for (var i = 0; i < spans.length; i++) {" +
                    "    var text = spans[i].innerText.trim().toLowerCase();" +
                    "    if(text.indexOf('ad') !== -1 || text.indexOf('ads') !== -1 || || text.indexOf('!important') !== -1 " +
                    "       text.indexOf('publicidad') !== -1 || text.indexOf('sponsored') !== -1) {" +
                    "        var container = spans[i].parentNode;" +
                    "        if(container) { container.style.display = 'none'; }" +
                    "    }" +
                    "}" +
                    "})()";
            view.evaluateJavascript(js, null);
        }
    }

}
