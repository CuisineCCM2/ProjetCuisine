package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private Button btnaddrecipes, btndeconnexion, btnaddnumber;
    private EditText editnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editnumber = findViewById(R.id.id_phone_number);
        btnaddrecipes = findViewById(R.id.btnaddrecipe);
        btndeconnexion = findViewById(R.id.btn_deconnexion);
        btnaddnumber = findViewById(R.id.id_btn_add_number);


        btnaddrecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, AddRecipes.class);
                startActivity(intent);
            }
        });

        btndeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);            }
        });

        btnaddnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editnumbercontent = editnumber.getText().toString();
                if (TextUtils.isEmpty(editnumbercontent)) {
                    Toast.makeText(getApplicationContext(), "Merci de reseigner votre numéro de téléphone", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Numéro ajouté !", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DashBoardActivity.class);
        startActivityForResult(myIntent, 0);
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
