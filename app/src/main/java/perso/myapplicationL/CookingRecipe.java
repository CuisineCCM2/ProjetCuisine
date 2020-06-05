package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class CookingRecipe extends AppCompatActivity {
    private Button btnvalidation;
    private TextView title, category, calories, time, instruction, description;
    private ImageView imageView;
    Intent intent;
    JSONObject recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_recipe);
        intent = getIntent();
        title = findViewById(R.id.title_recipe);
        imageView = findViewById(R.id.imageView);
        category = findViewById(R.id.category_recipe);
        calories = findViewById(R.id.calories_recipe);
        description = findViewById(R.id.description_recipe);
        time = findViewById(R.id.time_recipe);
        instruction = findViewById(R.id.instruction_recipe);
        try {
            Log.d("decasc2", "" + intent.getStringExtra("recipe"));
            recipe = new JSONObject(intent.getStringExtra("recipe"));
            Log.d("decasc2", "" + recipe.toString());
            title.setText(recipe.getString("name"));
            //imageView.setImageURI(new URL(recipe.getString("picture")));
            Picasso.get().load(recipe.getString("picture")).into(imageView);
            Log.d("decasc8", recipe.getString("picture"));
            category.setText(recipe.getString("category"));
            calories.setText(recipe.getString("calories"));
            description.setText(recipe.getString("description"));
            time.setText(recipe.getString("time"));
            instruction.setText(recipe.getString("instruction"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
