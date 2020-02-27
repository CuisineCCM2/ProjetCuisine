package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HorsConnexionMode extends AppCompatActivity {

    private Button btntempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_connexion_mode);

        btntempo = findViewById(R.id.id_tempo);
        btntempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HorsConnexionMode.this, HorsConnexionModeRecipe.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Votre recette est terminée !", Toast.LENGTH_LONG).show();

            }
        });

    }
}
