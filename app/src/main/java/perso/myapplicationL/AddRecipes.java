package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddRecipes extends AppCompatActivity {

    private Button btn_validation_recipe, btn_pic;
    private ImageButton btnadd;
    private EditText ingredient, quantity;
    private RecyclerView myRecyclerView;
    List<String> items;

    private static final int CAMERA_PIC_REQUEST = 1337 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);

        btnadd = findViewById(R.id.id_add);
        myRecyclerView = findViewById(R.id.id_recyclerView);
        ingredient = findViewById(R.id.id_ingredients);
        quantity = findViewById(R.id.id_quantity);
        btn_pic = findViewById(R.id.id_add_pic);
        items = new ArrayList<>();


        btn_pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        btn_validation_recipe = findViewById(R.id.id_btn_validation_recipe);
        btn_validation_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipes.this, Settings.class);
                Toast.makeText(getApplicationContext(), "Recette enregistrée !", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ingredientcontent = ingredient.getText().toString();
                String quantitycontent = quantity.getText().toString();

                if (TextUtils.isEmpty(ingredientcontent)) {
                    Toast.makeText(getApplicationContext(), "Merci de rentrer un ingrédient", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(quantitycontent)) {
                    Toast.makeText(getApplicationContext(), "Merci de rentrer une quantité", Toast.LENGTH_LONG).show();
                    return;
                }


                items.add(ingredientcontent + " : " + quantitycontent  );

                RecyclerSimpleViewAdapter adapter = new RecyclerSimpleViewAdapter(items, android.R.layout.simple_list_item_1);
                myRecyclerView.setAdapter(adapter);
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "Ligne ajoutée : " + ingredientcontent + " " + quantitycontent, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = findViewById(R.id.id_imageview_recipe); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
            imageview.setMinimumHeight(600);
        }
    }
}
