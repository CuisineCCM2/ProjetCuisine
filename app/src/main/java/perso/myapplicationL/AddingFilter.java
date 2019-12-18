package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddingFilter extends AppCompatActivity {

    private Button btnvalidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_filter);


        btnvalidation = findViewById(R.id.id_button_validation);
        btnvalidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddingFilter.this, DashBoardActivity.class);
                Toast.makeText(getApplicationContext(), "Validation de vos filtres", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }
}
