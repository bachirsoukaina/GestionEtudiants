package com.example.gestionetudiants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionetudiants.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListeEtudiantsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EtudiantAdapter adapter;
    private RequestQueue requestQueue;

    private static final String URL_LISTE =
            "http://192.168.1.111/projetws/ws/listeEtudiants.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etudiants);

        // Initialiser RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser adapter avec liste vide
        adapter = new EtudiantAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialiser Volley
        requestQueue = Volley.newRequestQueue(this);

        // Charger les étudiants
        chargerEtudiants();
    }

    private void chargerEtudiants() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL_LISTE,
                response -> {
                    Log.d("LISTE", response);

                    // Parser le JSON
                    Type type = new TypeToken<List<Etudiant>>(){}.getType();
                    List<Etudiant> etudiants = new Gson().fromJson(response, type);

                    // Mettre à jour le RecyclerView
                    adapter.majListe(etudiants);

                    Toast.makeText(this,
                            etudiants.size() + " étudiant(s) chargé(s)",
                            Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.e("ERREUR", "Erreur : " + error.getMessage());
                    Toast.makeText(this,
                            "Erreur de connexion !",
                            Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(request);
    }
}