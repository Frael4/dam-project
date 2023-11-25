package com.frael.projectfindyourfood.view;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.frael.projectfindyourfood.MainActivity;
import com.frael.projectfindyourfood.R;

public class Localizacion implements LocationListener {

    SearchInMap principal;
    TextView mensaje;

    /**
     * Cuando la localizacion se
     * actualice, actualizamos el mapa
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        String text = "Mi ubicacion es :" +
                "Latitud = " + location.getLatitude() +
                " Longitud = "+ location.getLongitude();
        
        //mensaje.setText(text);
        
        mapa(location.getLatitude(), location.getLongitude());
    }

    /**
     * Pintamos el mapa
     * segun coordenadas
     * @param latitude
     * @param longitude
     */
    private void mapa(double latitude, double longitude) {
        try {
            FragmentMaps fragmentMaps = new FragmentMaps();

            Bundle bundle = new Bundle();
            bundle.putDouble("lat", new Double(latitude));
            bundle.putDouble("lon", new Double(longitude));
            fragmentMaps.setArguments(bundle);

            FragmentManager fragmentManager = getPrincipal().getSupportFragmentManager();
            if(!fragmentManager.isStateSaved()){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mapView, fragmentMaps, null);

                fragmentTransaction.commit();
            }
        }catch (Exception ex)
        {
            Log.e("Error en mapa", ex.getMessage());
        }
    }

    /**
     *
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
        switch (status){
            case LocationProvider.AVAILABLE:
                Log.d("debug: ", "Location Provider disponible");
                break;
            case  LocationProvider.OUT_OF_SERVICE:
                Log.d("debug: ", "Location Provider fuera de servicio");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug: ", "Location Provider temporalmente indisponible");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider){
        Log.d("GPS STATE", "GPS ACTIVDo");
        //mensaje.setText("GPS Activado");
    }

    @Override
    public void onProviderDisabled(String provider){
        Log.d("GPS STATE", "GPS Desactivado");
        //mensaje.setText("GPS Desactivado");
    }

    public void setPrincipal(SearchInMap principal) {
        this.principal = principal;
    }

    public SearchInMap getPrincipal() {
        return principal;
    }

}
