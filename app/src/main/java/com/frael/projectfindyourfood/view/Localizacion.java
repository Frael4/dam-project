package com.frael.projectfindyourfood.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Localizacion implements LocationListener {


    SearchInMap principal;
    TextView mensaje;
    double lat , lon = 0;


    /**
     * Cuando la localizacion se
     * actualice, actualizamos el mapa
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {

        mapa(location.getLatitude(), location.getLongitude());
    }

    /**
     * Pintamos el mapa
     * segun coordenadas
     * @param latitude
     * @param longitude
     */
    @SuppressLint("MissingPermission")
    private void mapa(double latitude, double longitude) {
        try {
            FragmentMaps fragmentMaps = new FragmentMaps();
            //Seteamos los parametros de lat y lon para enviarlos al fragmento
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", new Double(latitude));
            bundle.putDouble("lon", new Double(longitude));
            fragmentMaps.setArguments(bundle);

            //Obtenemos la actividad principal el gestor de Fragmentos
            FragmentManager fragmentManager = getPrincipal().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //Realizamos un remplazo del fragmento
            fragmentTransaction.add(R.id.fragment1, fragmentMaps, null);
            Log.d("Fragment", "Renderizando mapaa");
            fragmentTransaction.commit();

        }catch (Exception ex)
        {
            Log.e("Error en mapa", ex.getMessage());
        }

        // Uso de places api
        PlacesClient placesClient = Places.createClient(getPrincipal());
        if(placesClient == null){
            Log.d("Places", placesClient.toString());
            return;
        }
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(getPlaceFields()).build();

        Task<FindCurrentPlaceResponse> task =  placesClient.findCurrentPlace(request);
        task.addOnCompleteListener( t -> {
           if(t.isSuccessful()){
               FindCurrentPlaceResponse result = t.getResult();

               for (PlaceLikelihood place: result.getPlaceLikelihoods()) {
                Log.i(TAG, String.format("Lugar '%s' tiene probabilidad: %f", place.getPlace().getName(), place.getLikelihood()));
               }
           } else {
               Exception exception = task.getException();
               if (exception instanceof ApiException) {
                   ApiException apiException = (ApiException) exception;
                   Log.e(TAG, "Lugar no encontrado: " + apiException.getStatusCode());
               }

           }
        });


    }

    // Método para obtener los campos de interés
    private List<Place.Field> getPlaceFields() {
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
        );
        return placeFields;
    }

    /**
     * Mostrare la disponibilidad del servicio
     * segun el estado
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

    /**
     * Establece el SearchInMap como actividad principal
     * para el mapa
     * @param principal
     */
    public void setPrincipal(SearchInMap principal) {
        this.principal = principal;
    }

    public SearchInMap getPrincipal() {
        return principal;
    }

}
