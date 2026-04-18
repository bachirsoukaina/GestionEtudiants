package com.example.gestionetudiants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionetudiants.beans.Etudiant;
import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private List<Etudiant> listeEtudiants;

    public EtudiantAdapter(List<Etudiant> listeEtudiants) {
        this.listeEtudiants = listeEtudiants;
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vue = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_etudiant, parent, false);
        return new EtudiantViewHolder(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int position) {
        Etudiant e = listeEtudiants.get(position);
        holder.tvNomPrenom.setText(e.getNom() + " " + e.getPrenom());
        holder.tvVilleSexe.setText("📍 " + e.getVille() + "  |  " + e.getSexe());
    }

    @Override
    public int getItemCount() {
        return listeEtudiants.size();
    }

    // ViewHolder
    public static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomPrenom, tvVilleSexe;

        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomPrenom = itemView.findViewById(R.id.tvNomPrenom);
            tvVilleSexe = itemView.findViewById(R.id.tvVilleSexe);
        }
    }

    // Mettre à jour la liste
    public void majListe(List<Etudiant> nouvelleListe) {
        this.listeEtudiants = nouvelleListe;
        notifyDataSetChanged();
    }
}