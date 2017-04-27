package proxiad.com.commercesratp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

import proxiad.com.commercesratp.model.Commerces;

/**
 * Created by abdelali IFERDEN on 27/04/2017.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ArrayList<Commerces> listCommerces ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        listCommerces = new ArrayList<Commerces>();
        listCommerces = (ArrayList<Commerces>) getIntent().getSerializableExtra("List");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        for(int i=0;i<listCommerces.size();i++)
        {

            LatLng point = new LatLng(listCommerces.get(i).getLat(), listCommerces.get(i).getLng());
            mMap.addMarker(new MarkerOptions().position(point).title(listCommerces.get(i).getLabel()));

        }

        //Zoomer sur le premier élement de la liste
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(listCommerces.get(0).getLat(), listCommerces.get(0).getLng()), 12.0f));

    }
}
