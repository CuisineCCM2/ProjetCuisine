package perso.myapplicationL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.GeoPoint;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import perso.myapplicationL.ui.main.ApiSelectRequests;
import perso.myapplicationL.ui.main.FirebaseConnection;

public class HistoryList extends Fragment {
    Thread thread = null;

    ListView historyListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_list, container, false);
        historyListView = root.findViewById(R.id.id_list_history);
        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 1500;
        handler.postDelayed(new Runnable() {
            public void run() {
                ArrayAdapter<String> adapter = null;
                boolean b = true;
                while(b) {
                    if(FirebaseConnection.listRecipes.size() != 0) {
                        String[] recettes = new String[FirebaseConnection.listRecipes.size()];
                        recettes = FirebaseConnection.listRecipes.toArray(recettes);
                        for(int i = 0; i < recettes.length; i++) {
                            String note = "";
                            if(FirebaseConnection.mapNotes.get(recettes[i]) != null) {
                                note = "\nNote : " + FirebaseConnection.mapNotes.get(recettes[i]);
                            }
                            recettes[i] = recettes[i].split("\\*")[0];
                            recettes[i] += note;
                        }
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recettes);
                        historyListView.setAdapter(adapter);
                        b = false;
                    } else {
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[0]);
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ApiSelectRequests().execute("https://select-service-dot-lesfuribardsdelacuisine-266513.appspot.com/selecthistoricrecipeforuserandroidapp?id_recipe=" + FirebaseConnection.listRecipes.get(i).toString().split("\\*")[1], "UTF-8");
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(1000);
                                Intent i = new Intent(getActivity(), CookingRecipe.class);
                                JSONArray jsonArray = ApiSelectRequests.ingredientsList;
                                i.putExtra("recipe", jsonArray.getJSONObject(0).toString());
                                startActivity(i);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
            }
        });
        return root;
    }
}
