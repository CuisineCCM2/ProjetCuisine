package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import perso.myapplicationL.ui.main.FirebaseConnection;

public class ShareNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomSheetDialog bottomSheetDialog;
    private RatingBar ratingbarNote;
    private Button returnButton;
    Intent intent;
    JSONObject recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);
        intent = getIntent();
        try {
            recipe = new JSONObject(intent.getStringExtra("recipe"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bottomSheetDialog = new BottomSheetDialog(ShareNoteActivity.this);
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.dial_layout, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);

        View EmailView = bottomSheetDialogView.findViewById(R.id.share_email);
        View FacebookView = bottomSheetDialogView.findViewById(R.id.share_facebook);
        View SmsView = bottomSheetDialogView.findViewById(R.id.share_sms);

        EmailView.setOnClickListener(this);
        FacebookView.setOnClickListener(this);
        SmsView.setOnClickListener(this);

        Button button = findViewById(R.id.share_button);
        button.setOnClickListener(this);

        ratingbarNote = findViewById(R.id.id_ratingbar);
        ratingbarNote.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf(getString(R.string.rating) + rating),Toast.LENGTH_LONG).show();
            }
        });
        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseConnection.addNoteToCurrentRecipe(ratingbarNote.getRating() + "");
                Intent intent = new Intent(ShareNoteActivity.this, DashBoardActivity.class);
                startActivity(intent);
            }
        });

        EmailView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                String subjet = null;
                String body = null;
                try {
                    subjet = "Les Furibards de la cuisine - " + recipe.getString("name");
                    body = "Category : " + recipe.getString("category");
                    body += "\nCalories : " + recipe.getString("calories") + "    Time : " + recipe.getString("time");
                    body += "\nDescription : " + recipe.getString("description");
                    body += "\n\nInstructions : " + recipe.getString("instruction");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myIntent.putExtra(Intent.EXTRA_SUBJECT, subjet);
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                myIntent.setType("Text/plain");
                startActivity(Intent.createChooser(myIntent, "Your choice"));
            }
        });

        String textToShare = null;
        try {
            textToShare ="I'm sharing you the recipe '" + recipe.getString("name") + "' with the app Les Furibards de la cuisine !\n\n";
            textToShare += "Category : " + recipe.getString("category");
            textToShare += "\nCalories : " + recipe.getString("calories") + "    Time : " + recipe.getString("time");
            textToShare += "\nDescription : " + recipe.getString("description");
            textToShare += "\n\nInstructions : " + recipe.getString("instruction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");

        FacebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShareNoteActivity.this.startActivity(sendIntent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        SmsView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Uri sms_uri = Uri.parse("smsto:0629318088");
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                try {
                    String text = "I'm sharing you the recipe '" + recipe.getString("name") + "' with the app Les Furibards de la cuisine !\n\n";
                    text += "Category : " + recipe.getString("category");
                    text += "\nCalories : " + recipe.getString("calories") + "    Time : " + recipe.getString("time");
                    text += "\nDescription : " + recipe.getString("description");
                    text += "\n\nInstructions : " + recipe.getString("instruction");
                    sms_intent.putExtra("sms_body", text);
                    startActivity(sms_intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        bottomSheetDialog.show();
    }
}
