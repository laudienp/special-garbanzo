package com.example.birdstagram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.connexion.LoginActivity;
import com.example.birdstagram.activities.inscription.ProfileActivity;
import com.example.birdstagram.activities.inscription.SignUpActivity;
import com.example.birdstagram.tools.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper myDb;
    TextView text;
    private final Class startingActivity = LoginActivity.class;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSIONS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("DATABASE", "ENTERING METHOD");
        myDb = new DatabaseHelper(this);

        text = findViewById(R.id.text);

        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if (hasPermissions(this, PERMISSIONS)){
            Intent intent = new Intent(getApplicationContext(), startingActivity);
            startActivity(intent);
        } else {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                boolean allPermissionsAccepted = true;
                if (grantResults.length == 0) allPermissionsAccepted = false;
                else {
                    for (int i=0; i<permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            allPermissionsAccepted = false;
                        }
                    }
                }

                if (allPermissionsAccepted) {
                    Intent intent = new Intent(getApplicationContext(), startingActivity);
                    startActivity(intent);
                } else {
                    text.setText("Dehors");
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
