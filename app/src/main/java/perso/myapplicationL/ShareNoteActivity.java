package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class ShareNoteActivity extends AppCompatActivity {

    private Button shareButton;
    private RatingBar ratingbarNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);

        ratingbarNote = findViewById(R.id.id_ratingbar);
        ratingbarNote.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf("Votre note est de : " + rating),Toast.LENGTH_LONG).show();
            }
        });

        // Share button
        shareButton = findViewById(R.id.id_share);
        shareButton.setOnClickListener(new View.OnClickListener() {

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
    }
}
