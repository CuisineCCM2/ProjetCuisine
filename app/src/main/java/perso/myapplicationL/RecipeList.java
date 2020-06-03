package perso.myapplicationL;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import perso.myapplicationL.ui.main.ApiSelectRequests;

public class RecipeList extends AppCompatActivity {

    ListView historyListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        historyListView = findViewById(R.id.id_list_recipes);

        ArrayList<String> listOfRecipe = new ArrayList<>();
        JSONArray jsonArray = ApiSelectRequests.ingredientsList;
        if (jsonArray != null) {
            int len = jsonArray.length();
          for (int i=0;i<len;i++){
                try {

                    String element = "";
                    JSONArray myarray = new JSONArray(jsonArray.get(i).toString());
                    JSONObject myobject;
                    myobject = myarray.getJSONObject(0);
                    int j = i + 1;
                    element +=  j + "\n" + "Title : " + myobject.getString("name") + "\n" +  "Category : " + myobject.getString("category") + "\n";
                    element += "Time : " + myobject.getString("time")+ "\n";
                    element += "Calories : " + myobject.getString("calories") + "\n";
                    element += "Picture : " + myobject.getString("picture") + "\n";

                    listOfRecipe.add(element);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfRecipe);
        historyListView.setAdapter(adapter);
    }
}