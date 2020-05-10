package com.example.birdstagram.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;

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

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    private MapView map;
    private Marker lastMarker;
    private ImageButton menuButton;
    private ImageButton cameraButton;
    private ImageButton addButton;
    private ImageButton cancelButton;
    private ImageButton validateButton;

    private boolean displayClick = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

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
                //TODO TRANSMIT LONGITUDE AND LATITUDE TO ACTIVITY
                GeoPoint position = lastMarker.getPosition();
                position.getLongitude();
                position.getLatitude();
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

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(43.130190, 5.923105);
        IMapController mapController = map.getController();
        mapController.setZoom(16.0);
        mapController.setCenter(startPoint);

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
}
