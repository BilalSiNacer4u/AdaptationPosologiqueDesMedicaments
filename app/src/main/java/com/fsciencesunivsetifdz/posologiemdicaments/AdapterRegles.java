package com.fsciencesunivsetifdz.posologiemdicaments;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRegles extends RecyclerView.Adapter {

    private final List list;
    private final List<Regles> listFull;
    private AdapterRegles.ItemClickListener mClickListener;

    public AdapterRegles(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_regles,parent,false);
        return new AdapterRegles.ReglessHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Regles regles = (Regles) list.get(position);
        AdapterRegles.ReglessHolder reglessHolder = (AdapterRegles.ReglessHolder) holder;
        reglessHolder.id.setText(Integer.toString(regles.getID_Regle()));

        if(regles.getDose_Regle()!=0){

            reglessHolder.dose.setText(regles.getDose_Regle()+" "+regles.getUnite());

        }else{

            reglessHolder.dose.setText(regles.getUnite());

        }

       if((regles.getType_Analyse_Utiliser().equals("Clairance rénale") || regles.getType_Analyse_Utiliser().equals("Bilirubine") ||
               regles.getType_Analyse_Utiliser().equals("Tgo/tgp") || regles.getType_Analyse_Utiliser().equals("Age") ||
               regles.getType_Analyse_Utiliser().equals("Poid"))){

           if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null) {

               reglessHolder.condition.setText("Type : " + regles.getType_Analyse_Utiliser() + " | Valeur Min : " + regles.getValeur_Minimum() + " | Valeur Max : " + regles.getValeur_Maximum());

           }else{

               if(regles.getValeur_Minimum() == null) {

                   reglessHolder.condition.setText("Type : " + regles.getType_Analyse_Utiliser() + " | Valeur Max : " + regles.getValeur_Maximum());

               }else{

                   reglessHolder.condition.setText("Type : " + regles.getType_Analyse_Utiliser() + " | Valeur Min : " + regles.getValeur_Minimum());

               }

           }



        }else{

            if(regles.getValeur_Sexe().equals("Homme") || regles.getValeur_Sexe().equals("Femme")){

                reglessHolder.condition.setText("Type : "+regles.getType_Analyse_Utiliser()+" | Sexe : "+regles.getValeur_Sexe());

            }else{

                if(regles.getValeur_Enceinte() != null){

                    if(regles.getValeur_Enceinte()==1){

                        reglessHolder.condition.setText("Type : "+regles.getType_Analyse_Utiliser()+" | Femme enceinte");

                    }else{

                        reglessHolder.condition.setText("Type : "+regles.getType_Analyse_Utiliser()+" | Femme ñ'est pas enceinte");

                    }

                }else{

                    reglessHolder.condition.setText("Aucune condition !.");

                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return (list.size());
    }

    public class ReglessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView id;
        TextView dose;
        TextView condition;

        ReglessHolder(View itemView) {

            super(itemView);

            id = itemView.findViewById(R.id.txt_id_regle);
            dose = itemView.findViewById(R.id.txt_dose);
            condition = itemView.findViewById(R.id.txt_condition);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterRegles.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
