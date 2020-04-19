package perso.myapplicationL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddingIngredient extends Fragment {

    private EditText ingredient, quantity;
    private Button btnsearch, btnfilter ;
    private ImageButton btnadd;
    private RecyclerView myRecyclerView;
    List<String> items;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_adding_ingredient, container, false);

        btnadd = root.findViewById(R.id.id_add);
        btnsearch = root.findViewById(R.id.id_search);
        btnfilter = root.findViewById(R.id.id_button_filter);
        myRecyclerView = root.findViewById(R.id.id_recyclerView);
        ingredient = root.findViewById(R.id.id_ingredients);
        quantity = root.findViewById(R.id.id_quantity);
        items = new ArrayList<>();


        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddingFilter.class);
                startActivity(intent);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CookingRecipe.class);
                startActivity(intent);
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ingredientcontent = ingredient.getText().toString();
                String quantitycontent = quantity.getText().toString();

                if (TextUtils.isEmpty(ingredientcontent)) {
                    Toast.makeText(getContext(), getString(R.string.EnterIngredient), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(quantitycontent)) {
                    Toast.makeText(getContext(), getString(R.string.EnterQuantity), Toast.LENGTH_LONG).show();
                    return;
                }


                items.add(ingredientcontent + " : " + quantitycontent  );

                RecyclerSimpleViewAdapter adapter = new RecyclerSimpleViewAdapter(items, android.R.layout.simple_list_item_1);
                myRecyclerView.setAdapter(adapter);
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                Toast.makeText(getContext(), "Ligne ajoutée : " + ingredientcontent + " " + quantitycontent, Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

}
