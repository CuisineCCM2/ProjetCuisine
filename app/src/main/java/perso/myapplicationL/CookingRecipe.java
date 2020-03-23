package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CookingRecipe extends AppCompatActivity {

    private Button btnvalidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_recipe);

       btnvalidation = findViewById(R.id.id_recipe_validation);
        btnvalidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CookingRecipe.this, ShareNoteActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), getString(R.string.RecipeFinished), Toast.LENGTH_LONG).show();

            }
        });
    }
}
