package perso.myapplicationL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    Context myContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        myContext = this;
        historyListView = findViewById(R.id.id_list_recipes);

        ArrayList<String> listOfRecipe = new ArrayList<>();
        final JSONArray jsonArray = ApiSelectRequests.ingredientsList;
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

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(myContext, CookingRecipe.class);
                try {
                    JSONArray myarray = new JSONArray(jsonArray.get(i).toString());
                    intent.putExtra("recipe", myarray.getJSONObject(0).toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}