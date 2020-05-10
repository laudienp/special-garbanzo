package com.example.birdstagram.tools;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.util.Log;

public class LocationGPS
{
    LocationManager locationManager = null;
    private String fournisseur;

    private void initialiserLocalisation()
    {
        if(locationManager == null)
        {
            locationManager = null;//(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            criteria.setAccuracy(Criteria.ACCURACY_FINE);

            //l'altitude
            criteria.setAltitudeRequired(true);
            criteria.setBearingRequired(true);
            criteria.setCostAllowed(true);

            criteria.setPowerRequirement(Criteria.POWER_HIGH);

            fournisseur = locationManager.getBestProvider(criteria, true);
            Log.d("GPS", "fournisseur : " + fournisseur);
        }

        if(fournisseur != null)
        {
            //derniere position connue
            //Location localisation = locationManager.getLastKnownLocation(fournisseur);

        }
    }
}
