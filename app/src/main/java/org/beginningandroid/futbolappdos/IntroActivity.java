package org.beginningandroid.futbolappdos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.facebook.share.widget.EmbedPostView;


public class IntroActivity extends AppCompatActivity {

    private WebView webView;

    private DatabaseReference timelinesRef;

    Context context;

    Button button;
    TextView text3;
    int x = 5;
    int y = 6;
    //Para botón invisible
    private int tapCount = 0;

    //Para botón secreto administración
    public void secretAdminLogin(View view) {
        tapCount++;
        if (tapCount >= 5) {
            startActivity(new Intent(this, LoginAdminActivity.class));
            tapCount = 0;
        }
    }


    ///List<String> listFutbolHeaders1;
    ArrayList<String> listFutbolHeaders = new ArrayList<>();
    ArrayList<String> listFutbolHeaders1 = new ArrayList<>();
    ArrayList<String> listFutbolTitles = new ArrayList<>();

    ArrayList<String> listFutbolHeaders1Agenda = new ArrayList<>();
    ArrayList<String> listFutbolHeadersAgenda = new ArrayList<>();

    ArrayList<String> listFutbolTitlesAgenda = new ArrayList<>();

    ArrayList<String> listFutbolHeadersLinkMatches = new ArrayList<>();
    ArrayList<String> listFutbolHeaders1LinkMatches = new ArrayList<>();

    ArrayList<String> listFutbolTitlesLinkMatches = new ArrayList<>();


    ArrayList<String> numbers = new ArrayList<>();
    String title;
    String singleText;

    private DatabaseReference userRef;

    //private EmbedPostView embedPostView;



// Habilitar scroll vertical
    //webView.getSettings().setJavaScriptEnabled(true);
    //webView.getSettings().setDomStorageEnabled(true);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        text3 = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        timelinesRef = FirebaseDatabase.getInstance().getReference("timelineEmbeddedXs");
        //String embeddedCode= timelinesRef.child("embeddedCode").toString();

        obtenerDato();

        //String embeddedCode;
        /*
        timelinesRef.child("embeddedCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Si el dato es de tipo String. Si es otro tipo, cambia el método getValue.
                    String embeddedCode = snapshot.getValue(String.class);
                    Log.d("IntroActivity", "Dato obtenido: " + embeddedCode);
                    // Aquí puedes utilizar el dato (por ejemplo, asignarlo a un TextView)
                } else {
                    Log.d("IntroActivity", "El dato especificado no existe.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("IntroActivity", "Error al leer el dato: " + error.getMessage());
            }
        });

         */

        //String embeddedCode = timelinesRef.child("embeddedCode").getKey();


        //String storedHash = timelinesRef.child("password").getValue(String.class);

        ///

        //WebView webView = findViewById(R.id.webViewTweet);
        webView = findViewById(R.id.webViewTweet);

        // on below line setting web view client.
        webView.setWebViewClient(new VideoActivity.WebClient());

        // on below line setting web chrome client for web view.
        webView.setWebChromeClient(new WebChromeClient());

        // on below line getting web settings.
        WebSettings webSettings = webView.getSettings();

        // on below line setting java script enabled to true.
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // Necesario para el widget
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);


        //
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        // on below line setting file access to true.
        webSettings.setAllowFileAccess(true);


        /*

        //String tweetUrl = "https://streamtp2.com/global1.php?stream=winplus2";
        //webView.loadUrl("https://platform.twitter.com/embed/index.html?url=" + tweetUrl);
        //String tweetUrl ="https://x.com/PizarraCrema/status/1874202956905799855";
        String tweetUrl ="https://t.co/e0CuuWQZgf";

        //String twitterEmbedCode = "<blockquote class=\"twitter-tweet\"><p lang=\"es\" dir=\"ltr\">Para cerrar el año, les regalo un hilo con los MEJORES MOMENTOS del 2024, DESDE LA TRIBUNA.<br><br>1. Campeones del torneo Apertura, ganándole 4-0 a Chankas en el Monumental y superando a Cristal en diferencia de goles.<br><br>Créditos: /UNORTE1924 en YouTube<a href=\"https://twitter.com/hashtag/Universitario?src=hash&amp;ref_src=twsrc%5Etfw\">#Universitario</a> <a href=\"https://twitter.com/SoyHinchaDeLaU?ref_src=twsrc%5Etfw\">@SoyHinchaDeLaU</a> <a href=\"https://t.co/e0CuuWQZgf\">pic.twitter.com/e0CuuWQZgf</a></p>&mdash; Pizarra Crema (@PizarraCrema) <a href=\"https://twitter.com/PizarraCrema/status/1874202956905799855?ref_src=twsrc%5Etfw\">December 31, 2024</a></blockquote>\n<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
        //String twitterEmbedCode = "<blockquote class=\"twitter-tweet\"><p lang=\"es\" dir=\"ltr\">Para cerrar el año, les regalo un hilo con los MEJORES MOMENTOS del 2024, DESDE LA TRIBUNA.<br><br>1. Campeones del torneo Apertura, ganándole 4-0 a Chankas en el Monumental y superando a Cristal en diferencia de goles.<br><br>Créditos: /UNORTE1924 en YouTube<a href=\"https://twitter.com/hashtag/Universitario?src=hash&amp;ref_src=twsrc%5Etfw\">#Universitario</a> <a href=\"https://twitter.com/SoyHinchaDeLaU?ref_src=twsrc%5Etfw\">@SoyHinchaDeLaU</a> <a href=\"https://t.co/e0CuuWQZgf\">pic.twitter.com/e0CuuWQZgf</a></p>&mdash; Pizarra Crema (@PizarraCrema) <a href=\"https://twitter.com/PizarraCrema/status/1874202956905799855?ref_src=twsrc%5Etfw\">December 31, 2024</a></blockquote> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

        //String twitterEmbedCode ="<a class=\"twitter-timeline\" href=\"https://twitter.com/PizarraCrema?ref_src=twsrc%5Etfw\">Tweets by PizarraCrema</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
        String timelineEmbedCode="<a class=\"twitter-timeline\" href=\"https://twitter.com/PizarraCrema?ref_src=twsrc%5Etfw\">Tweets by PizarraCrema</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

        int desiredHeight = 600;  // altura en píxeles
        int tweetsToShow = 1;     // cantidad de tweets a mostrar

        // Transforma el código copiando el timeline para agregar los atributos
        String modifiedTimelineCode = transformTimelineEmbedCode(embeddedCode, desiredHeight, tweetsToShow);

        // Envuelve el código en una estructura HTML completa
        String finalHtml = wrapEmbedTweet(modifiedTimelineCode);
        //Si quiero cargar los úlimos tweets pero hay demora en cargar
        loadTimeline(finalHtml);
        //loadTweet(finalHtml);

         */

        ///////

        //loadTweet(twitterEmbedCode);



        //loadTweet(tweetUrl);
        //webView.loadUrl(tweetUrl);

        // on below line setting url for the web page which we have to load in our web view.
        ///webView.loadUrl(receivedValue);

        //configureWebView();

        // URL del tweet (ej: https://twitter.com/JuezCentral/status/123456789)
        //String tweetUrl = "https://t.co/lErFqqSjyW";
        //loadTweet(tweetUrl);

        /*
        // on below line setting web view client.
        webView.setWebViewClient(new VideoActivity.WebClient());

        // on below line setting web chrome client for web view.
        webView.setWebChromeClient(new WebChromeClient());
        ///webView.setWebChromeClient(new CustomWebChromeClient(VideoActivity.this));
        // on below line getting web settings.
        WebSettings webSettings = webView.getSettings();

        // on below line setting java script enabled to true.
        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);

         */

        /*

        FacebookScraper.scrapeLatestPost(new FacebookScraper.ScrapeCallback() {
            @Override
            public void onSuccess(String postContent) {
                runOnUiThread(() -> {
                    webView.loadData(postContent, "text/html", "UTF-8");
                });
            }

            @Override
            public void onError(String error) {
                Log.e("Scraping", error);
            }
        });

         */
        ///



        if (!isConnected()){
            Toast.makeText(IntroActivity.this, " No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(IntroActivity.this, " Welcome to our App", Toast.LENGTH_SHORT).show();

        }

        Util.keyHashes(this);

        //getHtmlFromWeb();
        //Just using Json method for dynamic webs
        //getHtmlFromWebJson();
        getJsonWithWebView();

        /*
        //Approval Verification Section (6 Months)
        String hybridId = DeviceUtils.getHybridId(this);
        userRef = FirebaseDatabase.getInstance().getReference("users").child(hybridId);

        // Escuchar cambios en la aprobación
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean isApproved = snapshot.child("isApproved").getValue(Boolean.class);
                    Long expiresAt = snapshot.child("expiresAt").getValue(Long.class);

                    if (isApproved != null && isApproved && expiresAt != null) {
                        Date expirationDate = new Date(expiresAt);
                        Date today = new Date();
                        //Boolean decision = new BooleanDate().before(expirationDate);
                        if (new Date().before(expirationDate)) {
                            String Dia = "viernes";
                            Log.d("Datos de E y A", "expiración: " + expirationDate);
                            Log.d("Datos de E y A", "aprobación: " + today);
                            Log.d("Datos de E y A", "Dia " + Dia);


                            Toast.makeText(IntroActivity.this, " Uregistrado", Toast.LENGTH_SHORT).show();
                            //goToMainActivity();
                            //startActivity(new Intent(IntroActivity.this, MainActivity.class));
                            finish();
                            //return;
                        }
                    }
                }
                //Here create a blockedActivity instead of SplashActivity
                startActivity(new Intent(IntroActivity.this, RegistroActivity.class));
                String Dia = "jueves";
                Log.d("Datos de E y A", "Dia " + Dia);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RTDB", "Error: " + error.getMessage());
            }
        });

         */

        ////

        ///

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    //getHtmlFromWeb();

                goToMainActivity();
            }
        });
        //Initializing the Facebook SDK
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        // Initialize EmbedPostView
        //embedPostView = findViewById(R.id.embedPostView);

        // Load Facebook post URL
        //String postUrl = "https://www.facebook.com/Juezcentral/posts/pfbid034XmncYbLXEJjQviHqPUT7Mip9pYoMpfWd9DXPxAFDfx2bucma3GTQp2bz9MwsmhMl";
        //ShareLinkContent content = new ShareLinkContent.Builder()
                //.setContentUrl(Uri.parse(postUrl))
                //.build();

        //embedPostView.setShareContent(content);



    }
    private void configureWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // Obligatorio
        settings.setDomStorageEnabled(true); // Necesario para el widget
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        // Evitar redirecciones a navegador externo
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true; // Manejar todo en el WebView
            }
        });
    }
    private void loadTweet(String tweetUrl) {
        // Cargar el widget oficial de Twitter
        String embedHtml = "<html>" +
                "<head>" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "</head>" +
                "<body>" +
                "  <blockquote class='twitter-tweet'>" +
                "    <p lang='es' dir='ltr'>Para cerrar el año, les regalo un hilo con los MEJORES MOMENTOS del 2024, DESDE LA TRIBUNA.<br><br>" +
                "    1. Campeones del torneo Apertura, ganándole 4-0 a Chankas en el Monumental y superando a Cristal en diferencia de goles.<br><br>" +
                "    Créditos: /UNORTE1924 en YouTube " +
                "    <a href='https://twitter.com/hashtag/Universitario?src=hash&amp;ref_src=twsrc%5Etfw'>#Universitario</a> " +
                "    <a href='https://twitter.com/SoyHinchaDeLaU?ref_src=twsrc%5Etfw'>@SoyHinchaDeLaU</a> " +
                "    <a href='" + tweetUrl + "'>pic.twitter.com/e0CuuWQZgf</a></p>&mdash; Pizarra Crema " +
                "    <a href='https://twitter.com/PizarraCrema/status/1874202956905799855?ref_src=twsrc%5Etfw'>December 31, 2024</a>" +
                "  </blockquote>" +
                "  <script async src='https://platform.twitter.com/widgets.js' charset='utf-8'></script>" +
                "</body>" +
                "</html>";

        webView.loadDataWithBaseURL(
                "https://platform.twitter.com",
                embedHtml,
                "text/html",
                "UTF-8",
                null
        );
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void getHtmlFromWebUno() throws IOException {
        //Document doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol").get();
        Document doc = null;
        try {
        doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol").get();

        Element links = doc.select("table").first();
        //Elements linksUno = doc.selectFirst("links");
        Elements ths = links.select("td");
        Element titles = links.select("th").first();

        //listFutbolHeaders.add(ths.text());
        listFutbolTitles.clear();
        listFutbolTitles.add(titles.text());

        listFutbolHeaders.clear();
        listFutbolHeaders1.clear();
        for(Element titulo : ths) {
            listFutbolHeaders.add(titulo.text());
        }


        for( int i = 0; i < listFutbolHeaders.size(); i++) {
            listFutbolHeaders1.add(listFutbolHeaders.get(i));
        }

        } catch (IOException e) {
        e.printStackTrace();
        }






    }
    private String formatFecha(String fechaRaw) throws Exception {

        SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = input.parse(fechaRaw);

        SimpleDateFormat output =
                new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));

        String fecha = output.format(date);

        // Capitalizar primera letra
        return fecha.substring(0,1).toUpperCase() + fecha.substring(1);
    }

    private void getHtmlFromWeb () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder titulos = new StringBuilder();
                ///ArrayList<String> listFutbolHeaders = new ArrayList<String>();

                ArrayList<String> listFutbolData = new ArrayList<String>();

                //Document doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol").get();
                Document doc = null;
                Document agen = null;
                Document linkMatch = null;
                try {

                    doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol")
                            .userAgent("Mozilla/5.0")
                            .timeout(15000)
                            .get();
                    if (doc == null) {
                        runOnUiThread(() ->
                                text3.setText("❌ Error: doc es null")
                        );
                        return;
                    }

                    System.out.println(doc.html());
                    Log.d("SCRAPING_DEBUG", doc.html());

                    //String title = doc.title();
                    //String title = doc.selectFirst("div[class");
                    //JSONObject jsonParentObject = new JSONObject();
                    //*****JSONArray list = new JSONArray();
                    ///Elements links = doc.select("a[href]");
                    //Elements links = doc.select("table");
                    Element links = doc.select("table").first();

                    //Para que no se bloquee la app sino que aparezca el mensaje

                    if (links == null) {
                        runOnUiThread(() ->
                                text3.setText("⚠️ No se encontró la tabla (JS o bloqueo)")
                        );
                        return;
                    }
                    //Elements linksUno = doc.selectFirst("links");
                    Elements ths = links.select("td");

                    Element titles = links.select("th").first();

                    if (titles != null) {
                        listFutbolTitles.clear();
                        listFutbolTitles.add(titles.text());
                    }

                    //listFutbolHeaders.add(ths.text());
                    listFutbolTitles.clear();
                    listFutbolTitles.add(titles.text());

                    listFutbolHeaders.clear();
                    listFutbolHeaders1.clear();
                    for(Element titulo : ths) {
                        listFutbolHeaders.add(titulo.text());
                    }


                    for( int i = 0; i < listFutbolHeaders.size(); i++) {
                        listFutbolHeaders1.add(listFutbolHeaders.get(i));
                    }

                    /*

                    agen = Jsoup.connect("https://docs.google.com/spreadsheets/d/e/2PACX-1vQmtmvyVhA_nZ4mRYOMPyXs2-jyHozuogbeURAAQ5Hfa0ITOJ9TPAnpBjpCiYG7bp83l8rmWmeejt1g/pubhtml#").get();

                    Element agenda = agen.select("table").first();
                    //Elements linksUno = doc.selectFirst("links");
                    Elements datos = agenda.select("td");
                    Element tituloUno = agenda.select("th").first();

                    listFutbolTitlesAgenda.clear();
                    listFutbolTitlesAgenda.add(tituloUno.text());

                    listFutbolHeadersAgenda.clear();
                    listFutbolHeaders1Agenda.clear();
                    for(Element titulo : datos) {
                        listFutbolHeadersAgenda.add(titulo.text());
                    }


                    for( int i = 0; i < listFutbolHeadersAgenda.size(); i++) {
                        listFutbolHeaders1Agenda.add(listFutbolHeadersAgenda.get(i));
                    }

                    linkMatch = Jsoup.connect("https://docs.google.com/spreadsheets/d/e/2PACX-1vTfbxVW1fzSj79nyIGv2Anp9GsrZ8Vp1UxLO7_sas-AI4iogGQQR8xywH1kVa-ZCoYNRCDoy_ZTuyhY/pubhtml").get();

                    Element linkMatchUno = linkMatch.select("table").first();
                    //Elements linksUno = doc.selectFirst("links");
                    Elements datosUno = linkMatchUno.select("td");
                    Element tituloDos = linkMatchUno.select("th").first();

                    listFutbolTitlesLinkMatches.clear();
                    listFutbolTitlesLinkMatches.add(tituloDos.text());

                    listFutbolHeadersLinkMatches.clear();
                    listFutbolHeaders1LinkMatches.clear();
                    for(Element titulo : datosUno) {
                        listFutbolHeadersLinkMatches.add(titulo.text());
                    }


                    for( int i = 0; i < listFutbolHeadersLinkMatches.size(); i++) {
                        listFutbolHeaders1LinkMatches.add(listFutbolHeadersLinkMatches.get(i));
                    }

                     */


                    //Elements headers = links.select("th");
                    //titulos.append(ths.text());


                    //for (Element header : ths) {
                    //titulos.append(header.text()).append(", ");
                    //}

                    //Elements tables = links.select("tr");
                    //stringBuilder.append(links).append("");

                    ///stringBuilder.append(links).append("");
                    //for (Element link : links) {
                    //stringBuilder.append("").append("Link : ").append(link.attr("href")).append(" ").append("Text : ").append(link.text());
                    //}
                } catch (Exception e) {   // 👈 cambia IOException por Exception
                    e.printStackTrace();

                    runOnUiThread(() ->
                            text3.setText("💥 Crash: " + e.getMessage())
                    );
                }
                runOnUiThread(() -> {

                    if (listFutbolTitles == null || listFutbolTitles.isEmpty()) {
                        text3.setText("⚠️ No se obtuvieron datos del scraping");
                        return;
                    }

                    text3.setText(listFutbolTitles.get(0));
                });
                //runOnUiThread(new Runnable() {
                    //@Override
                    //public void run() {
                        //text3.setText(Html.fromHtml(stringBuilder.toString()));
                        ///title = listFutbolHeaders.get(0);
                        //text3.setText("" + listFutbolHeaders1.get(1) + listFutbolHeaders1LinkMatches.get(8));
                        //text3.setText("En pruebas");
                        //text3.setText("" + listFutbolTitles.get(0));
                        //singleText = listFutbolHeaders1.get(2);

                        /*

                        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                        intent.putExtra("key", listFutbolHeaders1);
                        intent.putExtra("keyUno", listFutbolTitles);
                        ///intent.putExtra("agenda",listFutbolHeaders1Agenda);
                        ///intent.putExtra("matchesLinks", listFutbolHeaders1LinkMatches);
                        startActivity(intent);

                         */
                        //List<String> listFutbolHeaders1 = listFutbolHeaders;
                        ///for(String titulo: listFutbolHeaders){

                        //}
                        //String headDos = listFutbolHeaders1.get(0).toString();
                    //}
                //});
            }
        }).start();
    }
    private void getJsonWithWebView() {

        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void processJSON(String json) {

                try {

                    JSONObject root = new JSONObject(json);

                    listFutbolHeaders1.clear();
                    listFutbolTitles.clear();

                    JSONObject partidos = root.getJSONObject("partidos");

                    Iterator<String> fechasKeys = partidos.keys();

                    if (fechasKeys.hasNext()) {

                        String fechaKey = fechasKeys.next();

                        String fechaBonita = formatFecha(fechaKey);
                        listFutbolTitles.add(fechaBonita);

                        JSONArray ligasArray = partidos.getJSONArray(fechaKey);

                        for (int i = 0; i < ligasArray.length(); i++) {

                            JSONObject ligaObj = ligasArray.getJSONObject(i);
                            Iterator<String> ligaKeys = ligaObj.keys();

                            while (ligaKeys.hasNext()) {

                                String nombreLiga = ligaKeys.next();
                                JSONArray partidosArray = ligaObj.getJSONArray(nombreLiga);

                                for (int j = 0; j < partidosArray.length(); j++) {

                                    JSONObject partido = partidosArray.getJSONObject(j);

                                    JSONObject local = partido.getJSONObject("equipolocal");
                                    JSONObject visitante = partido.getJSONObject("equipovisitante");

                                    String nombreLocal = local.getString("nombre");
                                    String nombreVisitante = visitante.getString("nombre");

                                    listFutbolHeaders1.add(
                                            nombreLocal + " vs " + nombreVisitante
                                    );

                                    listFutbolHeaders1.add(nombreLiga);

                                    long timestamp =
                                            partido.getLong("fechainicio") * 1000L;

                                    SimpleDateFormat horaFormat =
                                            new SimpleDateFormat("HH:mm", Locale.getDefault());

                                    String hora =
                                            horaFormat.format(new Date(timestamp));

                                    listFutbolHeaders1.add(hora);

                                    String canales =
                                            partido.optString("canales", "Por confirmar");

                                    listFutbolHeaders1.add(canales);
                                }
                            }
                        }
                    }

                    // 👉 IMPORTANTE: navegar cuando termine
                    //runOnUiThread(() -> goToMainActivity());

                } catch (Exception e) {

                    runOnUiThread(() ->
                            text3.setText("💥 Error parseando JSON: " + e.getMessage())
                    );
                }
            }
        }, "Android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                String js =
                        "fetch('https://www.futbolred.com/files/feeds/marcadoresenlineaoptimizado.json')" +
                                ".then(function(r){ return r.text(); })" +
                                ".then(function(data){ Android.processJSON(data); });";

                view.evaluateJavascript(js, null);
            }
        });

        webView.loadUrl("https://www.futbolred.com/parrilla-de-futbol");
    }
    private void getHtmlFromWebJson() {

        new Thread(() -> {

            try {

                Connection.Response response = Jsoup.connect(
                                "https://www.futbolred.com/files/feeds/marcadoresenlineaoptimizado.json"
                        )
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Referer", "https://www.futbolred.com/parrilla-de-futbol")
                        .header("Origin", "https://www.futbolred.com")
                        .header("Connection", "keep-alive")
                        .timeout(20000)
                        .execute();

                String json = response.body();

                Log.d("JSON_DEBUG", json.substring(0, Math.min(500, json.length())));

                JSONObject root = new JSONObject(json);

                runOnUiThread(() ->
                        text3.setText("✅ JSON recibido")
                );

            } catch (Exception e) {

                runOnUiThread(() ->
                        text3.setText("💥 Error: " + e.getMessage())
                );
            }

        }).start();
    }

    private void goToMainActivity() {

        text3.setText("" + listFutbolTitles.get(0));
        Log.e("Testing FutbolRed", "goToMainActivity: "+ listFutbolTitles.get(0));

        //text3.setText("" + listFutbolTitles.get(0));
        //Log.e("Testing FutbolRed", "goToMainActivity: "+ listFutbolTitles.get(0));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", listFutbolHeaders1);
        intent.putExtra("keyUno", listFutbolTitles);

        startActivity(intent);

    }
    private String wrapEmbedTweet(String modifiedTimelineCode) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <meta charset='utf-8'>" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "</head>" +
                "<body>" +
                modifiedTimelineCode +
                "</body>" +
                "</html>";
    }

    private String transformTimelineEmbedCode(String timelineEmbedCode, int height, int tweetLimit) {
        // Utilizamos replaceFirst para insertar los atributos en la etiqueta <a>
        String modifiedCode = timelineEmbedCode.replaceFirst(
                "<a\\s+class=\"twitter-timeline\"",
                "<a class=\"twitter-timeline\" data-height=\"" + height + "\" data-tweet-limit=\"" + tweetLimit + "\""
        );
        return modifiedCode;
    }

    private void loadTimeline(String timelineHtml) {
        webView.loadDataWithBaseURL(
                "https://platform.twitter.com",
                timelineHtml,
                "text/html",
                "UTF-8",
                null
        );
    }

    private void obtenerDato(){

        timelinesRef = FirebaseDatabase.getInstance().getReference("timelineEmbeddedXs");
        //String embeddedCode= timelinesRef.child("embeddedCode").toString();

        //String embeddedCode;

        timelinesRef.child("embeddedCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Si el dato es de tipo String. Si es otro tipo, cambia el método getValue.
                    String embeddedCode = snapshot.getValue(String.class);
                    Log.d("IntroActivity", "Dato obtenido: " + embeddedCode);
                    // Aquí puedes utilizar el dato (por ejemplo, asignarlo a un TextView)
                    // Una vez obtenido el dato, se llama a otro método que lo utiliza
                    utilizarDato(embeddedCode);
                } else {
                    Log.d("IntroActivity", "El dato especificado no existe.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("IntroActivity", "Error al leer el dato: " + error.getMessage());
            }
        });
    }

    private void utilizarDato(String dato) {



        // Aquí puedes realizar las acciones que dependan del dato obtenido,
        // por ejemplo, actualizar la interfaz o iniciar otra actividad.
        // Recuerda que este método se invoca solo después de obtener el dato.

        int desiredHeight = 600;  // altura en píxeles
        int tweetsToShow = 1;     // cantidad de tweets a mostrar

        // Transforma el código copiando el timeline para agregar los atributos
        String modifiedTimelineCode = transformTimelineEmbedCode(dato, desiredHeight, tweetsToShow);

        // Envuelve el código en una estructura HTML completa
        String finalHtml = wrapEmbedTweet(modifiedTimelineCode);
        //Si quiero cargar los úlimos tweets pero hay demora en cargar

        loadTimeline(finalHtml);
    }

}