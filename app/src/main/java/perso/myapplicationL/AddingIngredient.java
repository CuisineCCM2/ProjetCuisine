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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import perso.myapplicationL.ui.main.ApiSelectRequests;

public class AddingIngredient extends Fragment {

    private EditText ingredient, quantity;
    private Button btnsearch, btnfilter ;
    private ImageButton btnadd;
    private RecyclerView myRecyclerView;
    private int compteur =0;
    List<String> items;
    ApiSelectRequests ASR = new ApiSelectRequests();

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
        while(ApiSelectRequests.ingredientsList == null) {}
        String[] ing = new String[ApiSelectRequests.ingredientsList.length()];

        for(int i=0; i < ApiSelectRequests.ingredientsList.length(); i++) {
            try {
                ing[i] = ApiSelectRequests.ingredientsList.getJSONObject(i).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), layout.select_dialog_item, ing);

        AutoCompleteTextView actv = (AutoCompleteTextView) root.findViewById(R.id.id_ingredients);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setTextColor(Color.GREEN);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddingFilter.class);
                startActivity(intent);
            }
        });

        final String[] ingredientcontent = new String[1];
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientcontent[0] = ingredient.getText().toString();
                String quantitycontent = quantity.getText().toString();

                if (TextUtils.isEmpty(ingredientcontent[0])) {
                    Toast.makeText(getContext(), getString(R.string.EnterIngredient), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(quantitycontent)) {
                    Toast.makeText(getContext(), getString(R.string.EnterQuantity), Toast.LENGTH_LONG).show();
                    return;
                }

                compteur++;
                if (compteur <= 5) {
                    items.add(ingredientcontent[0]);
                    RecyclerSimpleViewAdapter adapter = new RecyclerSimpleViewAdapter(items, android.R.layout.simple_list_item_1);
                    myRecyclerView.setAdapter(adapter);
                    myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Toast.makeText(getContext(), "added line : " + ingredientcontent[0] + " " + quantitycontent, Toast.LENGTH_LONG).show();
                    ingredient.setText(null);
                    quantity.setText(null);
                    ingredient.requestFocus();
                } else {
                    ingredient.setText(null);
                    quantity.setText(null);
                    ingredient.setEnabled(false);
                    quantity.setEnabled(false);
                    Toast.makeText(getContext(), getString(R.string.limitingredient), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DECASC2", "onClick: " + Arrays.toString(items.toArray()));

                new ApiSelectRequests().execute("https://select-service-dot-lesfuribardsdelacuisine-266513.appspot.com/selectrecipesbyingredientsforandroidapp?ingredientArray=" + Uri.encode(new JSONArray(items).toString(), "UTF-8"));
                if (ingredientcontent[0] != null) {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(1000);
                                Intent i = new Intent(getActivity(), RecipeList.class);
                                startActivity(i);
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    Toast.makeText(getContext(), getString(R.string.MustAddingredient) , Toast.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }

}
