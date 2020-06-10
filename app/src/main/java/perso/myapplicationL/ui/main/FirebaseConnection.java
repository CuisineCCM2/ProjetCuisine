package perso.myapplicationL.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import perso.myapplicationL.MainActivity;

public class FirebaseConnection {
    public static FirebaseFirestore db;
    public static DocumentReference dbUser;
    public static String userMail;
    public static String userPseudo;
    public static double currentRecipe;
    public static ArrayList<Object> listRecipes;
    public static Map<String, String> mapNotes;
    private static String currentRecipeString = "";

    public static void init() {
        db = FirebaseFirestore.getInstance();
        userMail = MainActivity.email;
        dbUser = db.collection("utilisateurs").document(userMail);
        listRecipes = new ArrayList<>();
        mapNotes = new HashMap<>();
        getDataFirebase();
        getRecipe("poulet curry");
        getAllRecipes();
    }

    private static void getDataFirebase() {
        dbUser.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                userPseudo = (String) document.get("pseudo");
                                Log.d("PSEUDO", userPseudo);
                                Log.d("USERDATA", "Document exists");
                            } else {
                                Log.d("USERDATA", "No such document");
                            }
                        } else {
                            Log.d("USERDATA", "get failed with ", task.getException());
                        }
                    }
                });
    }

    private static void getRecipe(String recipeName) {
        dbUser.collection("recettes").document(recipeName).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                currentRecipe = (double) document.get("note");
                                Log.d("NOTE", "" + currentRecipe);
                                Log.d("USERDATA", "Document exists");
                            } else {
                                Log.d("USERDATA", "No such document");
                            }
                        } else {
                            Log.d("USERDATA", "get failed with ", task.getException());
                        }
                    }
                });
    }

    private static void getAllRecipes() {
        dbUser.collection("recettes").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("decasc9", "getAllRecipes: " + document.toString());
                        listRecipes.add(document.getId());
                        if(document.getData().get("note") != null) {
                            mapNotes.put(document.getId(), document.getData().get("note").toString());
                        }
                    }
                    Log.d("GETALLRECIPES", ""+ listRecipes, task.getException());
                } else {
                    Log.d("GETALLRECIPES", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public static void addRecipe(String name, String note) {
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("date", new Date());
        if(note != null) {
            recipe.put("note", note);
        }
        currentRecipeString = name;
        dbUser.collection("recettes").document(name)
                .set(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FIREBASE", "Error writing document", e);
                    }
                });
    }


    public static void addNoteToCurrentRecipe(String note) {
        // Set the "isCapital" field of the city 'DC'
        dbUser.collection("recettes").document(currentRecipeString)
                .update("note", note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
