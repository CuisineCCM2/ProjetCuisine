package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import perso.myapplicationL.ui.main.FirebaseConnection;

public class Settings extends AppCompatActivity {

    private Button btnaddrecipes, btndeconnexion, btnaddnumber, btnsendform;
    private EditText editnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editnumber = findViewById(R.id.id_phone_number);
        btnaddrecipes = findViewById(R.id.btnaddrecipe);
        btnsendform = findViewById(R.id.btn_send_form);
        btndeconnexion = findViewById(R.id.btn_deconnexion);
        btnaddnumber = findViewById(R.id.id_btn_add_number);


        btnaddrecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.google.fr");
                Intent i = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(i);
            }
        });

        btnsendform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                String destinationMail = FirebaseConnection.userMail;
                String subject = "Les Furibards de la cuisine - Add recipe form";
                String body = "Hi fellow culinary artist !";
                       body += "\n\nHere is the link to form to add a recipe: https://www.google.fr";
                       body += "\n\nBon Appétit !";
                Uri data = Uri.parse("mailto:?subject=" + subject + "&body=" + body + "&to=" + destinationMail);
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            }
        });

        btndeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });

        btnaddnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editnumbercontent = editnumber.getText().toString();
                if (TextUtils.isEmpty(editnumbercontent)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.EnterPhoneNumber), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), getString(R.string.NumberAdded), Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DashBoardActivity.class);
        startActivityForResult(myIntent, 0);
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
