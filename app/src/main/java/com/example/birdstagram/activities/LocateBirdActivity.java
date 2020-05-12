package com.example.birdstagram.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.birdstagram.R;
import com.example.birdstagram.data.tools.DataBundle;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.Specie;
import com.example.birdstagram.data.tools.User;

import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocateBirdActivity extends AppCompatActivity implements LocationListener{

    public static Bitmap lastImage;
    private ImageButton picture;
    TextView currentLocation;
    Button validate;
    Spinner specieView;
    Specie specie;
    String description;
    EditText descriptionView;
    User user;
    Switch isPublicView;
    boolean isPublic;
    double longitude = 0;
    double latitude = 0;
    LocationManager locationManager = null;
    private String provider;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_a_bird);
        //On récupère la longitude et la latitude
        Intent intent = getIntent();

        fillSpinner();

        if(intent != null){
            longitude = intent.getDoubleExtra("longitude", longitude);
            latitude = intent.getDoubleExtra("latitude", latitude);
            user = intent.getParcelableExtra("user");
        }
        currentLocation = findViewById(R.id.currentLocation);
        currentLocation.setText("Longitude : " + longitude + "\nLatitude : " + latitude);

        picture = findViewById(R.id.imageButton);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        });

        Button takePositionOnMapButton = findViewById(R.id.buttonPositionOnMap);
        takePositionOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("takePosition", true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button takeCurrentPositionWithGpsButton = findViewById(R.id.buttonCurrentGPSLocation);
        takeCurrentPositionWithGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoc();
            }
        });

        //Envoi des données à la BDD
        validate = findViewById(R.id.buttonValidate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionView = findViewById(R.id.inputDescription);
                description = descriptionView.getText().toString();

                /*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date current = Calendar.getInstance().getTime();
                String date = dateFormat.format(current);*/

                Date date = new Date();

                specieView = findViewById(R.id.spinner);
                specie = (Specie) specieView.getSelectedItem();

                isPublicView = findViewById(R.id.switchImageGallery);
                isPublicView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            setIsPublic(true);
                            Toast.makeText(getApplicationContext(),"Publication will be public.",Toast.LENGTH_LONG).show();
                        }
                        else{
                            setIsPublic(false);
                            Toast.makeText(getApplicationContext(),"Publication won't be public.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Post post = new Post(description, date, longitude, latitude, isPublic, specie, user);
                MainActivity.BDD.insertDataPost(post);

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lastImage!=null){
            picture.setImageBitmap(lastImage);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            lastImage = (Bitmap) extras.get("data");
            picture.setImageBitmap(lastImage);
        }
    }

    void setIsPublic(boolean bool){
        isPublic = bool;
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
                permissionsToRequest.add(permission);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }
    }

    @SuppressLint("MissingPermission")
    private void initLoc() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(true);
            criteria.setCostAllowed(true);

            criteria.setPowerRequirement(Criteria.POWER_HIGH);

            provider = locationManager.getBestProvider(criteria, true);
        }

        if (provider != null) {
            requestPermissionsIfNecessary(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE});
            Location location = locationManager.getLastKnownLocation(provider);

            if(location != null)
                this.onLocationChanged(location);


        }
    }

    public void onLocationChanged(Location localisation)
    {
        currentLocation.setText("Longitude : " + localisation.getLongitude() + "\nLatitude : " + localisation.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, provider+ " state: " + status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String fournisseur)
    {
        Toast.makeText( getApplicationContext(),fournisseur + " enabled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String prov)
    {
        Toast.makeText(this, prov+" disabled!", Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    void fillSpinner(){
        specieView = findViewById(R.id.spinner);
        List<String> specieArrayString = new ArrayList<String>();
        for(Specie specie : MainActivity.dataBundle.getAppSpecies()){
            specieArrayString.add(specie.getEnglishName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, specieArrayString);
        specieView.setAdapter(adapter);

    }
}
