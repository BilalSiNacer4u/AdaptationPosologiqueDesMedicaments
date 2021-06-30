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

public class AdapterMedicaments extends RecyclerView.Adapter implements Filterable {

    private final List list;
    private final List<Medicaments> listFull;
    private AdapterMedicaments.ItemClickListener mClickListener;

    public AdapterMedicaments(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicaments,parent,false);
        return new AdapterMedicaments.MedicamentssHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Medicaments medicaments = (Medicaments) list.get(position);
        AdapterMedicaments.MedicamentssHolder medicamentssHolder = (AdapterMedicaments.MedicamentssHolder) holder;
        medicamentssHolder.designation.setText(medicaments.getDesignation());

        medicamentssHolder.otherInformation.setText("ID Médicament : "+medicaments.getID_Medicament());

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
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Object> listfiltered = new ArrayList<>();

            if(charSequence.toString().isEmpty()){

                listfiltered.addAll(listFull);

            }else{

                for(Object o :listFull){

                    if(((Medicaments) o).getFull_Informations().toLowerCase().contains(charSequence.toString().toLowerCase())){

                        listfiltered.add(o);

                    }

                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = listfiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((Collection) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MedicamentssHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView designation;
        TextView otherInformation;

        MedicamentssHolder(View itemView) {

            super(itemView);
            designation = itemView.findViewById(R.id.txt_designation_medicaments);
            otherInformation = itemView.findViewById(R.id.txt_other_information_medicaments);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterMedicaments.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
