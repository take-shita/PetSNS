package com.example.petsns.ui.route;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;


import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.Manifest;
import android.widget.TextView;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.ui.dashboard.DashboardFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static com.example.petsns.LocationUtils.calculateDestinationLatLng;
import com.google.maps.model.DirectionsLeg;

public class routeFragment extends DashboardFragment{


    private RouteViewModel viewModel;
    TextView txtSmp;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);

        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedRouteViewModel();
        } else {
            // エラーハンドリング
        }

        return view;
//        return inflater.inflate(R.layout.fragment_route, container, false);
    }






    @Override
    //    画面遷移---------------------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Button btn1 = view.findViewById(R.id.route1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_routepopup2);
            }
        });


        EditText txtDis=view.findViewById(R.id.textDistance);
        Button btn = view.findViewById(R.id.start1);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

//                    txtDis.setText("3000");
                    try {
                        viewModel.setDistance(Integer.parseInt(txtDis.getText().toString()));
                        Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route_view);
                    }catch (Exception e){

                    }

                }
            });
        }



//        Button bt1 = view.findViewById(R.id.setButton);
//        bt1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_routepopup);
//            }
//        });



        Button changeButton = view.findViewById(R.id.setButton);//投稿削除確認ポップアップ画面
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_routepopup);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 400; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button a = dialog.findViewById(R.id.distance1);
                Button b = dialog.findViewById(R.id.time);

                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
//                dialog.show();
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).navigate(R.id.action_navigation_routepopup_to_navigation_routetime);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        Button ru = view.findViewById(R.id.route1);//投稿削除確認ポップアップ画面
        ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_routepopup2);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button c = dialog.findViewById(R.id.place);
                Button d = dialog.findViewById(R.id.favorite);

                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
//                dialog.show();
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).navigate(R.id.action_navigation_routepopup2_to_navigation_routefavorite);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });



    }




}



