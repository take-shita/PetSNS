package com.example.petsns.ui.route;

import androidx.lifecycle.ViewModel;

public class RouteViewModel extends ViewModel {
    int distance=500;

    int time=0;

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


    // TODO: Implement the ViewModel
}