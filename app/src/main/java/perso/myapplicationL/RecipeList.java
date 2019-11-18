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
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.List;

public class RecipeList extends Fragment {

    ListView historyListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recipe_list, container, false);
        historyListView = root.findViewById(R.id.id_list_recipes);
        String[] recettes = new String[]{
            "Poulet Basquese", "Tartiflette", "Bo bun"
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recettes);
        historyListView.setAdapter(adapter);
        return root;
    }
}
