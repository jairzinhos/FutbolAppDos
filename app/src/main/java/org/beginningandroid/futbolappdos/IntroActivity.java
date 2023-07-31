package org.beginningandroid.futbolappdos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    Button button;
    TextView text3;
    int x = 5;
    int y = 6;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        text3 = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        getHtmlFromWeb();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    //getHtmlFromWeb();

                goToMainActivity();
            }
        });
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
                    doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol").get();

                    //String title = doc.title();
                    //String title = doc.selectFirst("div[class");
                    //JSONObject jsonParentObject = new JSONObject();
                    //*****JSONArray list = new JSONArray();
                    ///Elements links = doc.select("a[href]");
                    //Elements links = doc.select("table");
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
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //text3.setText(Html.fromHtml(stringBuilder.toString()));
                        ///title = listFutbolHeaders.get(0);
                        //text3.setText("" + listFutbolHeaders1.get(1) + listFutbolHeaders1LinkMatches.get(8));
                        text3.setText("" + listFutbolTitles.get(0));
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
                    }
                });
            }
        }).start();
    }

    private void goToMainActivity() {

        text3.setText("" + listFutbolTitles.get(0));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", listFutbolHeaders1);
        intent.putExtra("keyUno", listFutbolTitles);

        startActivity(intent);

    }
}