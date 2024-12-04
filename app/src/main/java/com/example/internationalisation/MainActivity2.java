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
import org.json.JSONException;
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

    String jsonResponseString;


    // Méthode onCreate : initialise l'interface, configure le SwipeRefreshLayout,
    // le RecyclerView et l'adaptateur, puis charge les données en ligne.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOnlineCountryData();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rvCountries);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryAdapter(showCountries, Glide.with(this));
        recyclerView.setAdapter(adapter);

        getOnlineCountryData();
    }


    // Méthode refresh : arrête l'animation de rafraîchissement et met à jour l'adaptateur.
    private void refresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }


    // Méthode getOnlineCountryData : effectue une requête HTTP pour récupérer des données JSON sur un pays.
    // Traite la réponse, convertit en JSON, met à jour la liste des pays et rafraîchit l'affichage.

    private void getOnlineCountryData()
    {
        showCountries.clear();

        Runnable myRunnable = () -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    // https://restcountries.com/v3.1/all
                    // https://restcountries.com/#endpoints-region
                    .url("https://restcountries.com/v3.1/name/france")
                    .get()
                    .build();

            Log.d("response", "going to make the call");
            Response response = null;
            String test = "";
            try {
                response = client.newCall(request).execute();
                jsonResponseString = response.body().string();
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
        inflateDataFromJSON(jsonResponseString);
        refresh();
    }


    // Méthode inflateDataFromJSON : extrait et traite les données JSON pour créer une liste d'objets Country.
    // Gère les exceptions JSON et extrait les informations telles que nom, capitale, monnaie, coordonnées, et drapeau.
    private void inflateDataFromJSON(String jsonResponse)
    {
        JSONArray arr = null;

        try {
            arr = new JSONArray(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } if (arr != null) {
            // Pour chaque Pays
            for (int i = 0; i < arr.length(); i++) {
                String commonName = "Unknown";
                String officialName = "Unknown";
                String capitalCity = "Unknown";
                String currencyTrigram = "???";
                String currencyName = "Unknown";
                String currencySymbol = "?";
                Double lat = 0.0;
                Double lng = 0.0;
                String flagUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/No_flag.svg/338px-No_flag.svg.png";
                // Je récupère l'objet pays
                JSONObject obj = null;
                try {
                    obj = arr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                } if (obj != null) {
                    // on récupère les informations de nom
                    JSONObject nameObject = null;
                    try {
                        nameObject = obj.getJSONObject("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } try {
                        commonName = nameObject.getString("common");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } try {
                        officialName = nameObject.getString("official");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } //On récupère la capitale
                    try {
                        capitalCity = obj.getJSONArray("capital").getString(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } //TODO : On récupère les informations de monnaie
                    try {
                        JSONObject currencyObject = obj.getJSONObject("currencies");
                        currencyTrigram = currencyObject.keys().next();
                        JSONObject currencyObject2 = currencyObject.getJSONObject(currencyTrigram);
                        currencyName = currencyObject2.getString("name");
                        currencySymbol = currencyObject2.getString("symbol");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //String currencyString = currencyObject.toString();

                    // On récupère le code à deux lettres pour avoir l'adresse du drapeau
                    String cca2 = null;
                    try {
                        cca2 = obj.getString("cca2").toLowerCase();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    flagUrl = "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png100px/" + cca2 + ".png";
                    JSONArray latlng = null;
                    try {
                        latlng = obj.getJSONArray("latlng");
                        lat = latlng.getDouble(0); lng = latlng.getDouble(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showCountries.add(new Country(commonName, officialName, capitalCity, currencyTrigram, currencyName, currencySymbol, lat, lng, flagUrl));
            }
        }
    }
}