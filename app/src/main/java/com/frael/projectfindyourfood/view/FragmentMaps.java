package com.frael.projectfindyourfood.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {
    double lat, lon;
    Localizacion local = new Localizacion();


    public FragmentMaps(){}
    /**
     * Obtiene la latitud y longitud enviadas desde Localizacion
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param viewGroup If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param bundle If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View rootView = super.onCreateView(inflater,viewGroup,bundle);

        if(getArguments() != null){
            this.lat = getArguments().getDouble("lat");
            this.lon = getArguments().getDouble("lon"); /// mi error estaba lat
            Log.d("Latitude de mapa es", Double.toString(this.lat));
            Log.d("Longitud de mapa es", Double.toString(this.lon));
        }

        getMapAsync(this);


        return rootView;
    }

    /**
     * Inicializa el mapa con sus
     * respectivas configuraciones
     * @param googleMap
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {

        LatLng latLng = new LatLng(lat, lon);

        float zoom = 17;

        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("You're here!");
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        googleMap.addMarker(markerOptions);


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomBy(zoom));


        // USO DE API PLACES
        //
        PlacesClient placesClient = Places.createClient(local.getPrincipal());

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
                    //place.getPlace().getPlaceTypes()
                    Log.i("LUGARES ENCONTRADOS", String.format("Lugar '%s' tiene probabilidad: %f", place.getPlace().getName(), place.getLikelihood()));
                    Log.i("LUGARES datos", String.format("Lugar price level '%f' ", place.getPlace().getPriceLevel()));
                    LatLng latLng1 = new LatLng(place.getPlace().getLatLng().latitude, place.getPlace().getLatLng().longitude);
                    markerOptions.title(place.getPlace().getName());
                    markerOptions.position(latLng1);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                    googleMap.addMarker(markerOptions);


                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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


    /**
     *
     * @return
     */
    private List<Place.Field> getPlaceFields() {
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.PRICE_LEVEL,
                Place.Field.TYPES
        );
        return placeFields;
    }
}
