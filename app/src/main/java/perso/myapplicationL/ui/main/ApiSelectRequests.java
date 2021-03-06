package perso.myapplicationL.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import perso.myapplicationL.R;

public final class ApiSelectRequests extends AsyncTask<String, String, String> {

    public ApiSelectRequests() {}
    public static JSONArray ingredientsList;
    public static JSONArray recipesList;

    @Override
    protected String doInBackground(String... uri) {
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(uri[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int statusCode = urlConnection.getResponseCode();
            if (statusCode ==  200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                    //Log.d("DECASC2", "doInBackground: " + dta.toString());
                }
                ingredientsList = new JSONArray(dta.toString());
                Log.d("DECASC2", "doInBackground: " + ingredientsList.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //do stuff
     //   returnMethod(result);
    }
}