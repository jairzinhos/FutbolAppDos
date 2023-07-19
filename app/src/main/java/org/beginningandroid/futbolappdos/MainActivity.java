package org.beginningandroid.futbolappdos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

        Match_RecyclerViewAdapter adapter = new Match_RecyclerViewAdapter(this, matchmodels);

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
}