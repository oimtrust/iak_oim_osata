package com.oimtrust.osata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oimtrust.osata.R;

public class FragmentMaps extends Fragment implements OnMapReadyCallback{

    private GoogleMap mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootMaps = inflater.inflate(R.layout.activity_fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync( this);

        return rootMaps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng malang = new LatLng(-7.9784695, 112.561742);
        mapView.addMarker(new MarkerOptions().position(malang).title("Osata in Malang"));
        mapView.moveCamera(CameraUpdateFactory.newLatLng(malang));
    }

}
