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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import perso.myapplicationL.ui.main.ApiSelectRequests;
import perso.myapplicationL.ui.main.FirebaseConnection;

public class CookingRecipe extends AppCompatActivity {
    private Button btnvalidation;
    private TextView title, category, calories, time, instruction, description, ingredients;
    private ImageView imageView;
    private JSONArray arrayIngredients;
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
        ingredients = findViewById(R.id.textview_ingredients);
        try {
            recipe = new JSONObject(intent.getStringExtra("recipe"));
            recipe.getString("ID_recipe");
            // requete ingredients de la recette
            new ApiSelectRequests().execute("https://select-service-dot-lesfuribardsdelacuisine-266513.appspot.com/selectingredientsforrecipeuser?id_recipe=" + recipe.getString("ID_recipe"));
            title.setText(recipe.getString("name"));
            Picasso.get().load(recipe.getString("picture")).into(imageView);
            category.setText(recipe.getString("category"));
            calories.setText(recipe.getString("calories"));
            description.setText(recipe.getString("description"));
            time.setText(recipe.getString("time"));
            instruction.setText(recipe.getString("instruction"));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try { Thread.sleep(1000); }
                        catch (InterruptedException e) { e.printStackTrace(); }
                        arrayIngredients = ApiSelectRequests.ingredientsList;
                        Log.d("decasc6", "on sleep: " + arrayIngredients.toString());
                        String ingredientsText = "";
                        for(int i = 0; i < arrayIngredients.length(); i++) {
                            try {
                                if(arrayIngredients.getJSONObject(i).getString("unitname") == "null") {
                                    ingredientsText += arrayIngredients.getJSONObject(i).getString("name") + "\n";
                                }
                                else{
                                    ingredientsText += arrayIngredients.getJSONObject(i).getString("name") + " " + arrayIngredients.getJSONObject(i).getString("quantity") + " " + arrayIngredients.getJSONObject(i).getString("unitname") + "\n";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ingredients.setText(ingredientsText);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnvalidation = findViewById(R.id.id_recipe_validation);
        btnvalidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseConnection.addRecipe(recipe.getString("name") + "*" + recipe.getString("ID_recipe"), null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(CookingRecipe.this, ShareNoteActivity.class);
                intent.putExtra("recipe", recipe.toString());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), getString(R.string.RecipeFinished), Toast.LENGTH_LONG).show();
            }
        });
    }
}
