package com.frael.projectfindyourfood.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {
    double lat, lon;


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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng latLng = new LatLng(lat, lon);

        float zoom = 17;

        //googleMap.getUiSettings().setZoomControlsEnabled(true);
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("You're here!");
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        googleMap.addMarker(markerOptions);


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomBy(12));

    }
}
