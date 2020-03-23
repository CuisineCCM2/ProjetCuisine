package perso.myapplicationL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button loginBtn, registerBtn;
    private ProgressBar progressBar;
    public static String email;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_enter_email), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_enter_password), Toast.LENGTH_LONG).show();
            return;
        }

        if (isNetworkAvailable() == true) {
        //    Toast.makeText(getApplicationContext(),"Connecté",Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        } else {

            AlertDialog.Builder monBuilder = new AlertDialog.Builder(this);
            TextView textView = new TextView(this);
            textView.setText(getString(R.string.NoConnection) + "\n" +
                    getString(R.string.NoConnection2));
            monBuilder.setCustomTitle(textView);
            monBuilder.setPositiveButton(getString(R.string.LoginContinuer), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new GenericSqliteDatabase(getApplicationContext());
                    Toast.makeText(MainActivity.this, getString(R.string.ContinueChoiceLogin), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HorsConnexionMode.class);
                    startActivity(intent);
                }
            });

            monBuilder.setNegativeButton(getString(R.string.QuitLogin), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, getString(R.string.QuitChoiceLogin), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            AlertDialog maDialogBox=monBuilder.create();
            maDialogBox.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        registerBtn = findViewById(R.id.id_register);

    }
}
