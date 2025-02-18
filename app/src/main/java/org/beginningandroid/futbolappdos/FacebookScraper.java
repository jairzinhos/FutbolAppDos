package org.beginningandroid.futbolappdos;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FacebookScraper {

    public interface ScrapeCallback {
        void onSuccess(String postContent);
        void onError(String error);
    }

    public static void scrapeLatestPost(ScrapeCallback callback) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://m.facebook.com/JuezCentral") // Versión móvil
                        .header("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                        .build();

                Response response = client.newCall(request).execute();
                String html = response.body().string();

                // Log del HTML obtenido
                Log.d("Scraping", "HTML: " + html); // 👈 ¡Nuevo!

                Document doc = Jsoup.parse(html);
                Element post = doc.selectFirst("div[data-ft]"); // Selector del post

                if (post != null) {
                    String content = post.text();
                    callback.onSuccess(content);
                } else {
                    callback.onError("No se encontró el post");
                }
            } catch (Exception e) {
                callback.onError("Error: " + e.getMessage());
                Log.e("Scraping", "Error: " + e.getMessage());
            }
        }).start();
    }
}
