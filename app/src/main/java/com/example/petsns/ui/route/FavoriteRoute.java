package com.example.petsns.ui.route;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class FavoriteRoute {
    LatLng origin;
    LatLng point1;
    LatLng point2;
    LatLng point3;
    String name;

    public void setLatLng(List<Double> origin, List<Double> point1, List<Double> point2, List<Double> point3){
//        this.origin=origin;
//        this.point1=point1;
//        this.point2=point2;
//        this.point3=point3;
    }

    public void setOrigin(Double Latitude,Double Longitude){
        origin=new LatLng(Latitude,Longitude);
    }
    public void setPoint1(Double Latitude,Double Longitude){
        point1=new LatLng(Latitude,Longitude);
    }
    public void setPoint2(Double Latitude,Double Longitude){
        point2=new LatLng(Latitude,Longitude);
    }
    public void setPoint3(Double Latitude,Double Longitude){
        point3=new LatLng(Latitude,Longitude);
    }

    public LatLng getOrigin(){
        return origin;
    }
    public LatLng getPoint1(){
        return point1;
    }
    public LatLng getPoint2(){
        return point2;
    }
    public LatLng getPoint3(){
        return point3;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

}
