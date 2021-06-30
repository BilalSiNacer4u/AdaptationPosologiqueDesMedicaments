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

public class AdapterOrdonnances extends RecyclerView.Adapter {

    private final List list;
    private final List<Ordonnances_Patients_Medecines> listFull;
    private AdapterOrdonnances.ItemClickListener mClickListener;

    public AdapterOrdonnances(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordonnances,parent,false);
        return new AdapterOrdonnances.OrdonnancessHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Ordonnances_Patients_Medecines ordonnances_patients_medecines = (Ordonnances_Patients_Medecines) list.get(position);
        AdapterOrdonnances.OrdonnancessHolder ordonnancessHolder = (AdapterOrdonnances.OrdonnancessHolder) holder;

        ordonnancessHolder.name.setText(ordonnances_patients_medecines.getNom_Patient().toUpperCase()+" "+ordonnances_patients_medecines.getPrenom_Patient());

        ordonnancessHolder.otherInformation.setText("Créé par le médecin : "+ordonnances_patients_medecines.getNom_Medecine().toUpperCase()+" "+ordonnances_patients_medecines.getPrenom_Medecine() + " | " + ordonnances_patients_medecines.getDate_Ordonnance());

        ordonnancessHolder.id.setText(Integer.toString(ordonnances_patients_medecines.getID_Ordonnance()));

    }

    @Override
    public int getItemCount() {
        return (list.size());
    }

    public class OrdonnancessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView otherInformation;
        TextView id;

        OrdonnancessHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.txt_patients_name);
            otherInformation = itemView.findViewById(R.id.txt_information_ordonnaces);
            id = itemView.findViewById(R.id.txt_id_ordonnaces);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterOrdonnances.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
