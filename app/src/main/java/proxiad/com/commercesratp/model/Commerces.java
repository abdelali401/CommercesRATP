package proxiad.com.commercesratp.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Abdelali IFERDEN on 27/04/2017.
 */

public class Commerces implements Parcelable {

    String label;
    String city;
    String codePostal;
    Double lat;
    Double lng;

    public Commerces(String label, String city,String codePostal, Double lat, Double lng) {
        this.label = label;
        this.city = city;
        this.codePostal = codePostal;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLabel() {
        return label;
    }

    public String getCity() {
        return city;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Commerces(Parcel in){

        label = in.readString();
        city = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.city);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Commerces createFromParcel(Parcel in) {
            return new Commerces(in);
        }

        public Commerces[] newArray(int size) {
            return new Commerces[size];
        }
    };
}
