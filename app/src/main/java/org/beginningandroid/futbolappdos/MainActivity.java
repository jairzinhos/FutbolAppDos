package org.beginningandroid.futbolappdos;

import static java.lang.Character.toLowerCase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<MatchModel> matchmodels = new ArrayList<>();
    ArrayList<String> numberList;
    ArrayList<String> numberTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        ArrayList<String> numberList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        ArrayList<String> numberTitle = (ArrayList<String>) getIntent().getSerializableExtra("keyUno");

        setUpMatchmodels();

        Match_RecyclerViewAdapter adapter = new Match_RecyclerViewAdapter(this, matchmodels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpMatchmodels() {

        ArrayList<String> numberList = (ArrayList<String>) getIntent().getSerializableExtra("key");

        MatchModel movie = new MatchModel(numberList.get(0), numberList.get(1), numberList.get(2), numberList.get(3));
        matchmodels.add(movie);

        for (int i = 0; i < numberList.size(); i = i + 4) {
            MatchModel matchU = new MatchModel(numberList.get(i), numberList.get(i + 1), numberList.get(i + 2), numberList.get(i + 3));
            matchmodels.add(matchU);


        }
    }

    @Override
    public void onItemClick(int position) {
        String[] words = {"word1", "win sports +", "dsports", "word4", "word5", "espn 2", "tyc sports", "goltv", "espn"};
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        dictionary.put("win sports +", 1251);
        dictionary.put("dsports", 1071);
        dictionary.put("espn 2", 1100);
        dictionary.put("tyc sports", 1240);
        dictionary.put("goltv", 1134);
        dictionary.put("espn", 1107);


        String linkRoot = "https://arenacdmexico.com/canales/dtv2b.html?id=";

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
    }
}