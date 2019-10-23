package perso.myapplicationL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeList extends AppCompatActivity {

  /*  ListView historyListView;
    private String[] prenoms = new String[]{
            "Polo", "VUPF", "Jérome", "Bastien"
    }; */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
       /* historyListView = this.findViewById(R.id.id_list_history);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecipeList.this, android.R.layout.simple_list_item_1, prenoms);
        historyListView.setAdapter(adapter); */
    }

    /*public void onClickSearchButton(View view) {
        Intent myIntent = new Intent(RecipeList.this, RecipeList.class);
        startActivity(myIntent);
    }*/
}
