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

public class ShareNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomSheetDialog bottomSheetDialog;
    private RatingBar ratingbarNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);
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
                Toast.makeText(getApplicationContext(), String.valueOf("Votre note est de : " + rating),Toast.LENGTH_LONG).show();
            }
        });
        EmailView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                String subjet = "Le bon poulet de dragon guerrier";
                String body = "Partage de la recette d'un dragon guerrier";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, subjet);
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                myIntent.setType("Text/plain");
                startActivity(Intent.createChooser(myIntent, "Votre choix"));
            }
        });

        String textToShare = "Un Partage de recette guerrière ! #lemonstreoupas";

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
                sms_intent.putExtra("sms_body", "Je te partage une recette guerrière");
                startActivity(sms_intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        bottomSheetDialog.show();
    }
}
