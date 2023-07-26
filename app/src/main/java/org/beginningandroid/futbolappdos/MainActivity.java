package org.beginningandroid.futbolappdos;

///import static java.lang.Character.toLowerCase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
///import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
///import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
///import java.util.*;

/*
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
*/


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<MatchModel> matchmodels = new ArrayList<>();

    /*
    ArrayList<String> numberList;
    ArrayList<String> numberTitle;
    private ProgressBar loadingPB;

    ArrayList<String> arrayListName;
    ArrayList<String> arrayListLastName;
    ArrayList<String> arrayListEmail;
    ArrayList<String> arrayListAvatar;

     */



    ArrayList<String> keyWords = new ArrayList<>();

    ///ArrayList<String> numberMatchesLinks = new ArrayList<>();

    ArrayList<String> listFutbolLinkMatchesUno = new ArrayList<>();

    ArrayList<String> listFutbolLinkMatchesDos = new ArrayList<>();

    ////TextView textViewUno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textViewUnoD = findViewById(R.id.textViewUno);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        /*
        ArrayList<String> numberList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        ArrayList<String> numberTitle = (ArrayList<String>) getIntent().getSerializableExtra("keyUno");

        ArrayList<String> numberAgenda = (ArrayList<String>) getIntent().getSerializableExtra("agenda");
        ArrayList<String> numberMatchesLinks = (ArrayList<String>) getIntent().getSerializableExtra("matchesLinks");


         */

        setUpMatchmodels();

        Match_RecyclerViewAdapter adapter = new Match_RecyclerViewAdapter(this, matchmodels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setTextViewUno ();


    }

        /*
    private void setTextViewUno (){

        ArrayList<String> numberAgenda = (ArrayList<String>) getIntent().getSerializableExtra("agenda");
        ArrayList<String> numberMatchesLinks = (ArrayList<String>) getIntent().getSerializableExtra("matchesLinks");

        //keyWords.clear();
        /*

        for (int i=11;i<numberMatchesLinks.size(); i = i + 8) {
            keyWords.add(numberMatchesLinks.get(i));



        }


        textViewUno.setText(keyWords.size());
    }
    */


    private void setUpMatchmodels() {

        ArrayList<String> numberList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        ArrayList<String> numberTitle = (ArrayList<String>) getIntent().getSerializableExtra("keyUno");


        ArrayList<String> numberAgenda = (ArrayList<String>) getIntent().getSerializableExtra("agenda");
        ArrayList<String> numberMatchesLinks = (ArrayList<String>) getIntent().getSerializableExtra("matchesLinks");

        listFutbolLinkMatchesUno.clear();
        listFutbolLinkMatchesDos.clear();
        for (String titulo : numberMatchesLinks) {
            listFutbolLinkMatchesUno.add(titulo);
        }
        for (int i = 11; i < listFutbolLinkMatchesUno.size(); i = i + 8) {
            listFutbolLinkMatchesDos.add(listFutbolLinkMatchesUno.get(i));
        }
        /*
        getDataFromAPI();

        String[] newmatches = new String[arrayListName.size()];

        for (int i = 0; i < arrayListName.size(); i++){
            newmatches[i] =arrayListName.get(i);
        }
        MatchModel movieUno = new MatchModel(newmatches[0], newmatches[1], newmatches[0], "holi" );
        matchmodels.add(movieUno);

         */
        MatchModel movieUn = new MatchModel(numberAgenda.get(0), numberMatchesLinks.get(11), numberMatchesLinks.get(11 + 8), listFutbolLinkMatchesDos.get(2));
        matchmodels.add(movieUn);

        MatchModel movie = new MatchModel(numberList.get(0), numberList.get(1), numberList.get(2), numberList.get(3));
        matchmodels.add(movie);

        for (int i = 0; i < numberList.size(); i = i + 4) {
            MatchModel matchU = new MatchModel(numberList.get(i), numberList.get(i + 1), numberList.get(i + 2), numberList.get(i + 3));
            matchmodels.add(matchU);


        }
        for (int i = 11; i < numberMatchesLinks.size(); i = i + 8) {
            keyWords.add(numberMatchesLinks.get(i));
        }





        /*

        for (int i = 11; i < numberMatchesLinks.size(); i = i + 8) {
            keyWords.add(numberMatchesLinks.get(i));
        }
        textViewUno.setText(keyWords.size());

         */
    }
    // Linear-search function to find the index of an element

    /*


    public static int findIndex(int arr[], int t)
    {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

     */



    @Override
    public void onItemClick(int position) {
        /*
        getDataFromAPI();

        String[] newmatches = new String[arrayListName.size()];

        for (int i = 0; i < arrayListName.size(); i++){
            newmatches[i] =arrayListName.get(i);
        }

         */

        //// https://stackoverflow.com/questions/60690324/read-a-google-spreadsheet-from-an-android-app-and-list-out-that-data
        /// Review code for conncetion with google sheets..

        /// Documento para canales adicionales: Futbol para todos DB. Google Sheets
        /// https://docs.google.com/spreadsheets/d/e/2PACX-1vQmtmvyVhA_nZ4mRYOMPyXs2-jyHozuogbeURAAQ5Hfa0ITOJ9TPAnpBjpCiYG7bp83l8rmWmeejt1g/pubhtml
        /// json link https://spreadsheets.google.com/feeds/list/158x_7C-U19e9R4EuYL4tTUSBatelIJ2WPhzCdKrM_Qg/od6/public/values?alt=json

        /// Creating Array lists to connect directly to DB
        ArrayList<String> numberAgenda = (ArrayList<String>) getIntent().getSerializableExtra("agenda");

        //ArrayList<String> keyWords = new ArrayList<>();
        ArrayList<String> numberMatchesLinks = (ArrayList<String>) getIntent().getSerializableExtra("matchesLinks");

        listFutbolLinkMatchesUno.clear();
        listFutbolLinkMatchesDos.clear();
        for (String titulo : numberMatchesLinks) {
            listFutbolLinkMatchesUno.add(titulo.toLowerCase(Locale.ROOT));
        }
        for (int i = 11; i < listFutbolLinkMatchesUno.size(); i = i + 8) {
            listFutbolLinkMatchesDos.add(listFutbolLinkMatchesUno.get(i));
        }

        String[] strWords = new String[listFutbolLinkMatchesDos.size()];

        for (int i = 0; i < listFutbolLinkMatchesDos.size(); i++) {
            strWords[i] = listFutbolLinkMatchesDos.get(i).toLowerCase();
        }

        String[] strWordsMatchesUno = new String[listFutbolLinkMatchesUno.size()];

        for (int i = 0; i < listFutbolLinkMatchesUno.size(); i++) {
            strWordsMatchesUno[i] = listFutbolLinkMatchesUno.get(i).toLowerCase();
        }



        ///String k[] = listFutbolLinkMatchesDos.toArray(new String[listFutbolLinkMatchesDos.size()]);

        ArrayList<String> channelKeys;
        for (int i = 11; i < listFutbolLinkMatchesUno.size(); i = i + 8) {
            listFutbolLinkMatchesDos.add(listFutbolLinkMatchesUno.get(i));
        }
        /*
        String channelsStringUno = matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT);
        String keyMasterUno = null;

        for (String keyWord : strWords) {
            if (channelsStringUno.contains(keyWord)) {
                keyMasterUno = keyWord;
            }

         */


            /// https://www.geeksforgeeks.org/how-to-read-data-from-google-spreadsheet-in-android/  wEBSITE TUTORIAL
            /*
            String[] words = {"word1", "win sports +", "dsports", "word4", "word5", "espn 2", "tyc sports", "goltv", "espn", "directv 610"};
            Map<String, Integer> dictionary = new HashMap<String, Integer>();
            dictionary.put("win sports +", 1251);
            dictionary.put("dsports", 1071);
            dictionary.put("espn 2", 1100);
            dictionary.put("tyc sports", 1240);
            dictionary.put("goltv", 1134);
            dictionary.put("espn", 1107);
            dictionary.put("directv 610", 1057);

             */

            Map<String, String> dictionaryChannels = new HashMap<String, String>();
            ///for (String element : strWords){
            for (int i = 11; i < listFutbolLinkMatchesUno.size(); i = i + 8) {
                dictionaryChannels.put(listFutbolLinkMatchesUno.get(i), listFutbolLinkMatchesUno.get(i-1));
            }

        /*

        /// Creating Array lists to connect directly to DB
        ArrayList<String> numberAgenda = (ArrayList<String>) getIntent().getSerializableExtra("agenda");

        //ArrayList<String> keyWords = new ArrayList<>();
        ArrayList<String> numberMatchesLinks = (ArrayList<String>) getIntent().getSerializableExtra("matchesLinks");

        listFutbolLinkMatchesUno.clear();
        listFutbolLinkMatchesDos.clear();
        for(String titulo : numberMatchesLinks) {
            listFutbolLinkMatchesUno.add(titulo);
        }
        for( int i = 11; i < listFutbolLinkMatchesUno.size(); i = i +8) {
            listFutbolLinkMatchesDos.add(listFutbolLinkMatchesUno.get(i));
        }

         */
        /*
        keyWords.clear();

        for (int i=11;i<numberMatchesLinks.size(); i = i + 8) {
            keyWords.add(numberMatchesLinks.get(i));



        }

         */
            //textViewUno.setText(keyWords.toString());


            //String linkRoot = "https://arenacdmexico.com/canales/dtv2b.html?id=";
            String linkRoot = numberMatchesLinks.get(8);

        /*

        for (String element: words) {
            if (matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT).contains(element)) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                //intent.putExtra("KEY_SENDER", "https://arenacdmexico.com/canales/dtv2b.html?id=1251");
                //intent.putExtra("KEY_SENDER", linkRoot + dictionary.get("win sports +"));
                String channelsString = matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT);
                String keyMaster = null;
                String ran = "&HyHkUrV675E4EfvYfGKHV&&ghhgREfgTrR&id=1242&id=234&hYhUHJyegh&id=1251MYGhjUuoYj&id=1181&ram=4765&mjUJ&m2001HTgj";
                for (String key : dictionary.keySet()) {
                    if (channelsString.contains(key)) {
                        keyMaster = key;
                    }
                }
                intent.putExtra("KEY_SENDER", linkRoot + dictionary.get(keyMaster) + ran);
                startActivity(intent);

            }
        }

         */

            for (String element : strWords) {
                if (matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT).contains(element)) {
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    //intent.putExtra("KEY_SENDER", "https://arenacdmexico.com/canales/dtv2b.html?id=1251");
                    //intent.putExtra("KEY_SENDER", linkRoot + dictionary.get("win sports +"));
                    String channelsString = matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT);
                    String keyMaster = null;
                    String ran = "&HyHkUrV675E4EfvYfGKHV&&ghhgREfgTrR&id=1242&id=234&hYhUHJyegh&id=1251MYGhjUuoYj&id=1181&ram=4765&mjUJ&m2001HTgj";
                    /*
                    String channelsStringUno = matchmodels.get(position).getChannel().toLowerCase(Locale.ROOT);
                    String keyMasterUno = null;

                    for (String keyWord : strWords) {
                        if (channelsStringUno.contains(keyWord)) {
                            keyMasterUno = keyWord;
                        }

                     */

                    for (String key : dictionaryChannels.keySet()) {
                        if (channelsString.contains(key)) {
                            keyMaster = key;
                        }
                    }



                    intent.putExtra("KEY_SENDER", linkRoot + dictionaryChannels.get(keyMaster) + ran);
                    startActivity(intent);

                     /*
                    //String channelName = listFutbolLinkMatchesUno.get()
                    int index = listFutbolLinkMatchesUno.indexOf(keyMasterUno);
                    intent.putExtra("KEY_SENDER", linkRoot + strWordsMatchesUno[index -1]+ ran);
                    startActivity(intent);

                }

                      */
            }
        }
    /*
    private void getDataFromAPI() {

        // creating a string variable for URL.
        String url = "https://spreadsheets.google.com/feeds/list/158x_7C-U19e9R4EuYL4tTUSBatelIJ2WPhzCdKrM_Qg/od6/public/values?alt=json";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // creating a variable for our JSON object request and passing our URL to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONObject feedObj = response.getJSONObject("feed");
                    JSONArray entryArray = feedObj.getJSONArray("entry");
                    for(int i=0; i<entryArray.length(); i++){
                        JSONObject entryObj = entryArray.getJSONObject(i);
                        String firstName = entryObj.getJSONObject("gsx$match").getString("$t");
                        String lastName = entryObj.getJSONObject("gsx$hour").getString("$t");
                        String email = entryObj.getJSONObject("gsx$cup").getString("$t");
                        String avatar = entryObj.getJSONObject("gsx$channel").getString("$t");
                        arrayListName.add(firstName);
                        arrayListLastName.add(lastName);
                        arrayListEmail.add(email);
                        arrayListAvatar.add(avatar);

                        // passing array list to our adapter class.
                        ////userRVAdapter = new UserRVAdapter(userModalArrayList, MainActivity.this);

                        // setting layout manager to our recycler view.
                        ////userRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        // setting adapter to our recycler view.
                        ////userRV.setAdapter(userRVAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // calling a request queue method
        // and passing our json object
        queue.add(jsonObjectRequest);
    }

     */

    }
    /*
    public static int getIndexOf(String[] strings, String item) {
        for (int i = 0; i < strings.length; i++) {
            if (item.equals(strings[i])) return i;
        }
        return -1;
    }

     */

}