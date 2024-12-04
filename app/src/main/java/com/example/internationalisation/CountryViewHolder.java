package com.example.internationalisation;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.internationalisation.model.Country;

public class CountryViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView capital;
    private final ImageView flag;
    private final TextView currency;

    private Country country;

    public CountryViewHolder(final View itemView)
    {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //Un intent à besoin d'être envoyé par un objet héritant du type Context
                // (généralement une activité). Ici nous ne somme pas dans un tel objet.
                // Par contre notre ViewHolder sera contenu dans l'itemView passé
                // en paramètre; cet itemView sera nécessairement inscrit dans un Context.
                // C'est ce Context que l'on récupère ici, et qu'on utilise pour
                // la méthode startActivity().
                Intent intent = new Intent(itemView.getContext(), MapsActivity.class);
                intent.putExtra("lat", country.getLatitude());
                intent.putExtra("lng", country.getLongitude());
                intent.putExtra("name", country.getCommonName());
                itemView.getContext().startActivity(intent);
            }
        });

        name = ((TextView) itemView.findViewById(R.id.tvc_name));
        capital = ((TextView) itemView.findViewById(R.id.tvc_capital));
        flag = ((ImageView) itemView.findViewById(R.id.tvc_flag));
        currency = ((TextView) itemView.findViewById(R.id.tvc_currency));
    }

    public void afficher(Country country, RequestManager glide) {
        this.country = country;

        if (country != null) {
            name.setText(country.getCommonName() + " (" + country.getOfficialName() + ")");
            capital.setText(country.getCapitalCity());
            currency.setText(country.getCurrencyName() + " ( " +
                    country.getCurrencyTrigram() + ", " +
                    country.getCurrencySymbol() + ")");
            if (glide != null) {
                glide.load(country.getFlagURL()).apply(RequestOptions.circleCropTransform()).override(150).into(flag);
            } else {
                flag.setImageResource(R.drawable.hsr);
            }
        }
    }
}
