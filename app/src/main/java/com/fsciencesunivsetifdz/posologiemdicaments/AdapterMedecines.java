package com.fsciencesunivsetifdz.posologiemdicaments;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterMedecines extends RecyclerView.Adapter implements Filterable {

    private final List list;
    private final List<Patients> listFull;
    private AdapterMedecines.ItemClickListener mClickListener;

    public AdapterMedecines(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medecines,parent,false);
        return new AdapterMedecines.MedecinessHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Medecines medecines = (Medecines) list.get(position);
        AdapterMedecines.MedecinessHolder medecinessHolder = (AdapterMedecines.MedecinessHolder) holder;
        medecinessHolder.name.setText(medecines.getNom_Medecine().toUpperCase()+" "+medecines.getPrenom_Medecine().toLowerCase());
        medecinessHolder.otherInformation.setText("#"+medecines.getCompte());

    }

    @Override
    public int getItemCount() {
        return (list.size());
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {

            List<Object> listfiltered = new ArrayList<>();

            if(charSequence.toString().isEmpty()){

                listfiltered.addAll(listFull);

            }else{

                for(Object o :listFull){

                    if(((Medecines) o).getFullName_Account_Medecine().toLowerCase().contains(charSequence.toString().toLowerCase())){

                        listfiltered.add(o);

                    }

                }

            }

            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = listfiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {

            list.clear();
            list.addAll((Collection) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MedecinessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView otherInformation;

        MedecinessHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.txt_name_medecines);
            otherInformation = itemView.findViewById(R.id.txt_other_information_medecines);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterMedecines.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
