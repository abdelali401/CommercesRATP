package proxiad.com.commercesratp.model;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Abdelali IFERDEN on 27/04/2017.
 */

public class Commerces {

    String label, city;
    LatLng point;

    public Commerces(String label, String city, LatLng point) {
        this.label = label;
        this.city = city;
        this.point = point;
    }

    public String getLabel() {
        return label;
    }

    public String getCity() {
        return city;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }
}
