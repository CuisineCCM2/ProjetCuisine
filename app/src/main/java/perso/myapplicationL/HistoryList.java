package perso.myapplicationL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

import perso.myapplicationL.ui.main.FirebaseConnection;

public class HistoryList extends Fragment {
    Thread thread = null;

    ListView historyListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_list, container, false);
        historyListView = root.findViewById(R.id.id_list_history);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                boolean b = true;
//                while(b) {
//                    if(FirebaseConnection.listRecipes.size() != 0) {
//                        String[] recettes = new String[FirebaseConnection.listRecipes.size()];
//                        recettes = FirebaseConnection.listRecipes.toArray(recettes);
//                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recettes);
//                        historyListView.setAdapter(adapter);
//                        b = false;
//                    }
//                }
//            }
//        });
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

        return root;
    }
}
