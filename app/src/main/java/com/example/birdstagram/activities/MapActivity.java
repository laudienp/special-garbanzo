package com.example.birdstagram.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.birdstagram.R;
import com.example.birdstagram.data.tools.DataBundle;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.User;
import com.example.birdstagram.tools.DataRetriever;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.sql.Date;
import java.util.ArrayList;
import org.osmdroid.config.Configuration;

import java.util.ArrayList;

import static com.example.birdstagram.activities.MainActivity.myDb;

public class MapActivity extends AppCompatActivity implements LocationListener {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
    private Marker lastMarker;
    private ImageButton menuButton;
    private ImageButton cameraButton;
    private ImageButton addButton;
    private ImageButton cancelButton;
    private ImageButton validateButton;
    LocationManager locationManager = null;
    Location location;
    private String provider;
    private DataBundle dataBundle;
    private DataRetriever dataRetriever;

    private boolean displayClick = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        dataRetriever = new DataRetriever();

        Intent intent = getIntent();
        dataBundle = intent.getParcelableExtra("Data Bundle");
        fillDataBundle();

        menuButton = findViewById(R.id.menu_button);
        addButton = findViewById(R.id.addBird_button);
        cancelButton = findViewById(R.id.cancel_button);
        validateButton = findViewById(R.id.validate_button);
        cameraButton = findViewById(R.id.camera_button);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayClick = true;
                validateButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayClick = false;
                removeLastMarker();
                validateButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayClick = false;
                removeLastMarker();
                validateButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), LocateBirdActivity.class);
                //On transmet la longitude et la latitude
                GeoPoint position = lastMarker.getPosition();
                double longitude = position.getLongitude();
                double latitude = position.getLatitude();
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", longitude);
                bundle.putDouble("latitude", latitude);
                intent.putExtras(bundle);
                intent.putExtra("user", dataBundle);
                startActivity(intent);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        });

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        requestPermissionsIfNecessary(new String[]
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });


        GeoPoint startPoint = new GeoPoint(43.130190, 5.923105);
        IMapController mapController = map.getController();
        mapController.setZoom(16.0);
        mapController.setCenter(startPoint);
        initLoc();

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if (displayClick){
                    removeLastMarker();
                    lastMarker = new Marker(map, getApplicationContext());
                    lastMarker.setPosition(p);
                    map.getOverlays().add(lastMarker);
                }
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getApplicationContext(), mReceive);
        map.getOverlays().add(mapEventsOverlay);
    }

    private void fillDataBundle() {

        dataBundle.setAppUsers(dataRetriever.retrieveUsers());
        dataBundle.setAppSpecies(dataRetriever.retrieveSpecies());


        /*dataBundle.setUserPosts();
        dataBundle.setAppPosts();
        dataBundle.setAppLikes();
        dataBundle.setAppComments();
        dataBundle.setAppViewers();*/

    }

    private void removeLastMarker(){
        map.getOverlays().remove(lastMarker);
        map.invalidate();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            LocateBirdActivity.lastImage = (Bitmap) extras.get("data");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
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
            Log.d("GPS", "provider : " + provider);
        }

        if (provider != null) {
            requestPermissionsIfNecessary(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE});
            Location location = locationManager.getLastKnownLocation(provider);

            if(location != null)
                this.onLocationChanged(location);

            locationManager.requestLocationUpdates(provider, 15000, 10, this);
        }
    }

    public void onLocationChanged(Location localisation)
    {
        Log.d("GPS", "localisation: " + localisation.toString());
        IMapController mapController = map.getController();
        mapController.setCenter(new GeoPoint(localisation.getLatitude(), localisation.getLongitude()));
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
}
