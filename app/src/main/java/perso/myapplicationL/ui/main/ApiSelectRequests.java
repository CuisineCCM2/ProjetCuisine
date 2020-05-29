package perso.myapplicationL.ui.main;

import android.os.AsyncTask;
import android.util.Log;

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

public class ApiSelectRequests extends AsyncTask<String, String, String> {

    public ApiSelectRequests() {}
    public String ingredientsList;

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
                    Log.d("DECASC2", "doInBackground: " + chunks);
                }
                ingredientsList = chunks;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}