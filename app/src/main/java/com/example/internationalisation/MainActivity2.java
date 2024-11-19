package com.example.internationalisation;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.internationalisation.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Country> showCountries = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCountryData();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rvCountries);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryAdapter(showCountries, Glide.with(this));
        recyclerView.setAdapter(adapter);

        getCountryData();
        getOnlineCountryData();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    private void getCountryData()
    {
        int number = new Random().nextInt(4)+1;

        List<Country> countries = new ArrayList<Country>();

        countries.add(new Country("Japan", "Japan", "Tokyo", "JPY", "Japanese Yen", "¥", 36.0, 138.0, ""));
        countries.add(new Country("France", "French Republic", "Paris", "EUR", "Euro", "€", 46.0, 2.0, ""));
        countries.add(new Country("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", "London", "GBP", "British Pound", "£", 54.0, -2.0, ""));
        countries.add(new Country("United States", "United States of America", "Washington, D.C.", "USD", "United States Dollar", "$", 38.0, -97.0, ""));

        showCountries.clear();
        for (int i = 0; i < number; i++)
        {
            showCountries.add(countries.get(0));
            showCountries.add(countries.get(1));
            showCountries.add(countries.get(2));
            showCountries.add(countries.get(3));
        }

        refresh();
    }

    private void refresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    private void getOnlineCountryData()
    {
        Runnable myRunnable = () -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://restcountries.com/v3.1/all")
                    .get()
                    .build();

            Log.d("response", "going to make the call");
            Response response = null;
            String test = "";
            try {
                response = client.newCall(request).execute();
                test = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("response", test);
        };

        Thread t = (new Thread(myRunnable));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}