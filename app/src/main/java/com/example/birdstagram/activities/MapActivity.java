package com.example.birdstagram.activities;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.birdstagram.R;
import com.example.birdstagram.data.tools.Post;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.osmdroid.config.Configuration;

import java.util.Date;
import java.util.List;

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
    private String provider;

    private boolean putMarkerOnClick = false;
    private boolean takePosition = false;

    private final String CHANNEL_ID = "New Bird";
    private final int NOTIFICATION_ID = 001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        try {
            loadUserPosts();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initComponents();
        initMap();
        initAddBirdEvent();
        refreshMapOverlay();
         // A utiliser //sendNotificationChannel("Un nouveau like ", "Quelq'un a aimé votre post", CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    private void loadUserPosts() throws ParseException {
        MainActivity.dataBundle.setUserPosts(MainActivity.dataRetriever.retrieveUserPosts(MainActivity.dataBundle.getUserSession().getId()));
    }

    private void initComponents(){
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
                putMarkerOnClick = true;
                validateButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
                lastMarker = null;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putMarkerOnClick = false;
                removeLastMarker();
                validateButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
                if (takePosition){
                    Intent intent = new Intent(getApplicationContext(), LocateBirdActivity.class);
                    startActivity(intent);
                }
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastMarker != null) {
                    putMarkerOnClick = false;
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
                    startActivity(intent);
                }
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    private void initMap(){
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
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
    }

    private void initAddBirdEvent(){
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if (putMarkerOnClick){
                    removeLastMarker();
                    lastMarker = new Marker(map, getApplicationContext());
                    lastMarker.setPosition(p);
                    Drawable otherPin = getResources().getDrawable( R.drawable.other_pin );
                    lastMarker.setIcon(otherPin);
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

    private void refreshMapOverlay(){
        try {
            List<Post> posts = MainActivity.dataRetriever.retrievePosts();
            ArrayList<OverlayItem> items = new ArrayList<>();
            for (Post post : posts){
                GeoPoint position = new GeoPoint(post.getLatitude(), post.getLongitude());
                OverlayItem item = new OverlayItem(post.getSpecie().getEnglishName(), post.getDescription(), position);
                Drawable defaultPin = getResources().getDrawable( R.drawable.default_pin );
                item.setMarker(defaultPin);
                items.add(item);
            }
            ItemizedOverlayWithFocus<OverlayItem> overlays = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),
                    items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>(){

                @Override
                public boolean onItemSingleTapUp(int index, OverlayItem item) {
                    return true;
                }

                @Override
                public boolean onItemLongPress(int index, OverlayItem item) {
                    return false;
                }
            });
            overlays.setFocusItemsOnTap(true);
            map.getOverlays().add(overlays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    protected void onNewIntent(Intent intent) {
        takePosition = intent.getBooleanExtra("takePosition", false);
        if (takePosition){
            putMarkerOnClick = true;
            validateButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
            if (lastMarker != null){
                map.getOverlays().add(lastMarker);
            }
        }
        super.onNewIntent(intent);
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
        refreshMapOverlay();
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

    public void sendNotificationChannel(String title, String message, String channelId, int priority, Bitmap image){
        Intent landingIntent = new Intent(getApplicationContext(), MapActivity.class);

        if (title.equals("Nouvel oiseau découvert")){
            landingIntent = new Intent(getApplicationContext(), MapActivity.class);
            landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (title.equals("Un nouveau like")){
           // landingIntent = new Intent(getApplicationContext(), SocialActivity.class);
            landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);
        Date currentTime = Calendar.getInstance().getTime();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.bird)
                .setContentTitle(title +
                        "                                               "
                        + currentTime.getHours() + ":" + currentTime.getMinutes())
                .setContentText(message)
                .setPriority(priority)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (title.equals("Nouvel oiseau découvert") && image == null){
            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bird_big))
                    .bigLargeIcon(null) );
        } else if (image != null){
            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null) );
        }

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(this);
        notificationCompat.notify(NOTIFICATION_ID, builder.build());
    }

}
