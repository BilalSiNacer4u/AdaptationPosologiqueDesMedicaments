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

public class AdapterPatients extends RecyclerView.Adapter implements Filterable {

    private final List list;
    private final List<Patients> listFull;
    private AdapterPatients.ItemClickListener mClickListener;

    public AdapterPatients(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patients,parent,false);
        return new AdapterPatients.PatientssHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Patients patients = (Patients) list.get(position);
        AdapterPatients.PatientssHolder patientssHolder = (AdapterPatients.PatientssHolder) holder;
        patientssHolder.name.setText(patients.getNom_Patient().toUpperCase()+" "+patients.getPrenom_Patient().toLowerCase());

        if(patients.getSexe().equals("Femme") && patients.getEnceinte()==1){

            patientssHolder.otherInformation.setText("Sexe : "+patients.getSexe() + " | " + "Enceinte : Oui | Age : " + patients.getAge()+" | Poid : "+patients.getPoid()+" Kg");

        }else{

            if(patients.getSexe().equals("Femme") && patients.getEnceinte()!=1){

                patientssHolder.otherInformation.setText("Sexe : "+patients.getSexe() + " | " + "Enceinte : Non | Age : " + patients.getAge()+" | Poid : "+patients.getPoid()+" Kg");

            }else{

                patientssHolder.otherInformation.setText("Sexe : "+patients.getSexe() + " | Age : " + patients.getAge()+" | Poid : "+patients.getPoid()+" Kg");

            }

        }

        patientssHolder.id.setText(Integer.toString(patients.getID_Patient()));

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

                    if(((Patients) o).getFull_Information_Patient().toLowerCase().contains(charSequence.toString().toLowerCase())){

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

    public class PatientssHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView otherInformation;
        TextView id;

        PatientssHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            otherInformation = itemView.findViewById(R.id.txt_other_information);
            id = itemView.findViewById(R.id.txt_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterPatients.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
