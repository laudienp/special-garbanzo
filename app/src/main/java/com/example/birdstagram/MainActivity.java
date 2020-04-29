package com.example.birdstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdstagram.inscription.ProfileActivity;
import com.example.birdstagram.inscription.SignUpActivity;


public class MainActivity extends AppCompatActivity {

    TextView text;
    private final Class startingActivity = MapActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            requestPermissions(PERMISSIONS, 1);
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
            case 1: {
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
