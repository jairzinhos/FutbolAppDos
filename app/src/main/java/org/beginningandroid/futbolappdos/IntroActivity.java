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
    ArrayList<String> numbers = new ArrayList<>();
    String title;
    String singleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        text3 = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getHtmlFromWeb();
                //goToMainActivity();
            }
        });
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
                try {
                    doc = Jsoup.connect("https://www.futbolred.com/parrilla-de-futbol").get();

                    //String title = doc.title();
                    //String title = doc.selectFirst("div[class");
                    JSONObject jsonParentObject = new JSONObject();
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



                    //Elements headers = links.select("th");
                    titulos.append(ths.text());


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
                        title = listFutbolHeaders.get(0);
                        //text3.setText("" + listFutbolHeaders1.get(1));
                        text3.setText("" + listFutbolTitles.get(0));
                        singleText = listFutbolHeaders1.get(2);

                        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                        intent.putExtra("key", listFutbolHeaders1);
                        intent.putExtra("keyUno", listFutbolTitles);
                        startActivity(intent);
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

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }
}