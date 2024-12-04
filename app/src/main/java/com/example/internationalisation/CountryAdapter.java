package com.example.internationalisation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.internationalisation.model.Country;

import java.util.List;

public class CountryAdapter  extends RecyclerView.Adapter<CountryViewHolder> {

    private List<Country> countries = null;
    private RequestManager glide;

    public CountryAdapter(List<Country> countries, RequestManager glide) {
        if (countries != null)
        {
            this.countries = countries;
        }
        this.glide = glide;
    }

    // Méthode onCreateViewHolder : initialise un ViewHolder en utilisant le layout
    // country_layout pour définir l'apparence d'un élément du RecyclerView.
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.country_layout, parent, false);
        return new CountryViewHolder(view);
    }

    // Méthode onBindViewHolder : lie les données d'un objet Country à un ViewHolder
    // pour afficher les informations d'un pays à la position donnée.
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.afficher(country, this.glide);
    }

    // Méthode getItemCount : retourne le nombre d'éléments dans la liste des pays,
    // ou 0 si la liste est nulle.
    @Override
    public int getItemCount() {
        if (countries != null)
            return countries.size();
        return 0;
    }
}
