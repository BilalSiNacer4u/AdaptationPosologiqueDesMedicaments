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

public class AdapterContient extends RecyclerView.Adapter {

    private final List list;
    private final List<Contient> listFull;
    private AdapterContient.ItemClickListener mClickListener;

    public AdapterContient(List list) {

        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contient,parent,false);
        return new AdapterContient.ContientsHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Contient contient = (Contient) list.get(position);
        AdapterContient.ContientsHolder medecinessHolder = (AdapterContient.ContientsHolder) holder;
        medecinessHolder.designation_dose.setText(contient.getDesignation().toUpperCase()+" | Dose : "+contient.getDose());

    }

    @Override
    public int getItemCount() {
        return (list.size());
    }

    public class ContientsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView designation_dose;

        ContientsHolder(View itemView) {

            super(itemView);
            designation_dose = itemView.findViewById(R.id.txt_designation_dose);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(AdapterContient.ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }

}
