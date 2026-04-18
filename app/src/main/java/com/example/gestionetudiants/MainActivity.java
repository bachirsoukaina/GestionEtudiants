package com.example.gestionetudiants;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionetudiants.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNom, etPrenom;
    private Spinner spVille;
    private RadioButton rbHomme, rbFemme;
    private Button btnAjouter, btnVoirListe;
    private RequestQueue requestQueue;

    private static final String URL_AJOUTER =
            "http://192.168.1.111/projetws/ws/ajouterEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNom        = findViewById(R.id.etNom);
        etPrenom     = findViewById(R.id.etPrenom);
        spVille      = findViewById(R.id.spVille);
        rbHomme      = findViewById(R.id.rbHomme);
        rbFemme      = findViewById(R.id.rbFemme);
        btnAjouter   = findViewById(R.id.btnAjouter);
        btnVoirListe = findViewById(R.id.btnVoirListe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.villes,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVille.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        btnAjouter.setOnClickListener(this);
        btnVoirListe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAjouter) {
            ajouterEtudiant();
        } else if (v == btnVoirListe) {
            startActivity(new Intent(this, ListeEtudiantsActivity.class));
        }
    }

    private void ajouterEtudiant() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_AJOUTER,
                response -> {
                    Log.d("REPONSE", response);
                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                    for (Etudiant e : etudiants) {
                        Log.d("ETUDIANT", e.toString());
                    }
                    Toast.makeText(this, "Étudiant ajouté !", Toast.LENGTH_SHORT).show();
                    etNom.setText("");
                    etPrenom.setText("");
                },
                error -> {
                    Log.e("ERREUR", "Erreur : " + error.getMessage());
                    Toast.makeText(this, "Erreur de connexion !", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String sexe = rbHomme.isChecked() ? "homme" : "femme";
                Map<String, String> params = new HashMap<>();
                params.put("nom",    etNom.getText().toString());
                params.put("prenom", etPrenom.getText().toString());
                params.put("ville",  spVille.getSelectedItem().toString());
                params.put("sexe",   sexe);
                return params;
            }
        };
        requestQueue.add(request);
    }
}