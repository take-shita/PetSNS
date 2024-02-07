package com.example.petsns.ui.route;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class RouteViewModel extends ViewModel {
    int distance=500;
    int time=0;
    boolean favoriteCheck=false;
    LatLng origin;
    LatLng point1;
    LatLng point2;
    LatLng point3;

    public void setDistance(int distance){
        this.distance=distance;
    }
    public int getDistance(){
        return distance;
    }

    public void setTime(int time){
        this.time=time;
    }
    public int getTime(){
        return time;
    }

    public void FalseFavoriteCheck(){
        favoriteCheck=false;
    }
    public void TrueFavoriteCheck(){favoriteCheck=true;}
    public boolean getFavoriteCheck(){
        return favoriteCheck;
    }


    public void setOrigin(LatLng origin){
        this.origin=origin;
    }
    public LatLng getOrigin(){
        return origin;
    }

    public void setPoint1(LatLng point1){
        this.point1=point1;
    }
    public LatLng getPoint1(){
        return point1;
    }

    public void setPoint2(LatLng point2){
        this.point2=point2;
    }
    public LatLng getPoint2(){
        return point2;
    }

    public void setPoint3(LatLng point3){
        this.point3=point3;
    }
    public LatLng getPoint3(){
        return point3;
    }
    // TODO: Implement the ViewModel
}