package com.frael.projectfindyourfood.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

    double lat, lon;

    public FragmentMaps(){}

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param viewGroup If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param bundle If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View rootView = super.onCreateView(inflater,viewGroup,bundle);

        if(getArguments() != null){
            this.lat = getArguments().getDouble("lat");
            this.lat = getArguments().getDouble("lon");
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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

    }
}
