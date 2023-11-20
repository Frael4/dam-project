package com.frael.projectfindyourfood.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.Manifest;
import android.util.Log;
//import com.frael.projectfindyourfood.Manifest;
import com.frael.projectfindyourfood.R;

public class SearchInMap extends AppCompatActivity {

    /**
     * TIEMPO MINIMO ESPERA
     */
    final static long MIN_TIME = 10000; // 10 SEC
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_in_map);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        else {
            inicializarLocalizacion();
        }
        //SupportMapFragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.mapa);
    }

    /**
     *
     */
    @SuppressLint("ServiceCast")
    private void inicializarLocalizacion() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(locationManager == null ){
            Log.d("Location Manager", "ESNULLL");
        }
        Localizacion local =  new Localizacion();

        local.setPrincipal(this);
        boolean gpsEnabled = true;

        if (locationManager != null) {
            // Llama al método isProviderEnabled solo si locationManager no es nulo
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }


        if(!gpsEnabled){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            return;
        }

        if(locationManager != null ){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, 0 , local);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0 , local);
        }

    }

    /**
     *
     * @param requestCode The request code passed in
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                inicializarLocalizacion();
                return;
            }
        }
    }
}