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
import android.os.PersistableBundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    static boolean isPublic;
    static double longitude;
    static double latitude;
    LocationManager locationManager = null;
    private String provider;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_a_bird);

        if(savedInstanceState != null){
            int index = getIndex(specieView, savedInstanceState.getString("specie"));
            specieView.setSelection(index);
            description = savedInstanceState.getString("description");
            descriptionView.setText(description);
        }

        initComponents();
        fillSpinner();
        checkIntent();
    }

    private void initComponents(){
        picture = findViewById(R.id.imageButton);
        Button takePositionOnMapButton = findViewById(R.id.buttonPositionOnMap);
        Button takeCurrentPositionWithGpsButton = findViewById(R.id.buttonCurrentGPSLocation);
        validate = findViewById(R.id.buttonValidate);
        descriptionView = findViewById(R.id.inputDescription);
        specieView = findViewById(R.id.spinner);
        isPublicView = findViewById(R.id.switchImageGallery);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        });

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

        takeCurrentPositionWithGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoc();
            }
        });

        //Envoi des données à la BDD
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = descriptionView.getText().toString();

                /*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date current = Calendar.getInstance().getTime();
                String date = dateFormat.format(current);*/

                Date date = new Date();

                specie = findSpecieAssociatedToThisString(specieView.getSelectedItem().toString());

                Post post = new Post(description, date, longitude, latitude, isPublic, specie, user);
                MainActivity.BDD.insertDataPost(post);

                resetValues();

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);

            }
        });

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
    }

    private void resetValues(){
        lastImage = null;
        longitude = 0;
        latitude = 0;
    }


    private void checkIntent(){
        Intent intent = getIntent();

        if(intent != null){
            longitude = intent.getDoubleExtra("longitude", longitude);
            latitude = intent.getDoubleExtra("latitude", latitude);
            user = MainActivity.dataBundle.getUserSession();
        }
        currentLocation = findViewById(R.id.currentLocation);
        currentLocation.setText("Longitude : " + longitude + "\nLatitude : " + latitude);
    }

    @Override
    public void onResume() {
        if (lastImage!=null){
            picture.setImageBitmap(lastImage);
        }
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("specie", specie.getEnglishName());
        outState.putString("description", descriptionView.getText().toString());
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

    private int getIndex(Spinner spinner, String myString){
        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                return i;
            }
        }
        return 0;
    }

    Specie findSpecieAssociatedToThisString(String specieEnglishName){
        List<Specie> listSpecie = MainActivity.dataBundle.getAppSpecies();
        for(Specie specie : listSpecie){
            if(specie.getEnglishName() == specieEnglishName) return specie;
        }
        return null;
    }
}
