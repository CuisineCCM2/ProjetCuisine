package perso.myapplicationL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.List;

import perso.myapplicationL.ui.main.ApiSelectRequests;

public class RecipeList extends AppCompatActivity {

    ListView historyListView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        historyListView = findViewById(R.id.id_list_recipes);
        new ApiSelectRequests().execute("https://select-service-dot-lesfuribardsdelacuisine-266513.appspot.com/selectallingredientsforandroidapp");
        String[] recettes ;
        recettes = new String[]{
            "Poulet Basquese", "Tartiflette", "Bo bun"
        };

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recettes);
        historyListView.setAdapter(adapter);
    }
}