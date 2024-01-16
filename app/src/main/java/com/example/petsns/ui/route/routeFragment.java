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
import android.widget.ImageButton;

import android.Manifest;
import android.widget.TextView;

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
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class routeFragment extends DashboardFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private GeoApiContext geoApiContext;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private RouteViewModel mViewModel;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;


    LatLng currentLatLng;
    TextView txtSmp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_samplemap, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        geoApiContext = new GeoApiContext.Builder().apiKey("AIzaSyB7PVa9P1isPm5kkSEDlXuaVXepW7v17Fw").build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() != null) {
                    currentLatLng = new LatLng(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude());

                }
            }
        };


        Button centerButton = view.findViewById(R.id.centerButton);
//        centerButton.setOnClickListener(v -> centerMapOnMyLocation());

        Button generateRouteButton = view.findViewById(R.id.routeButton);
        generateRouteButton.setOnClickListener(v -> {
            // 権限の確認とリクエスト

            checkLocationPermission();
            generateRouteAsync();
//            generateRandomRoute();
        });
        return view;
//        return inflater.inflate(R.layout.fragment_route, container, false);
    }
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // 位置情報権限の確認とリクエスト
        checkLocationPermission();
        getCurrentLocationAndMoveMap();
    }

    private LocationRequest createLocationRequest() {
        return new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);
    }
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 権限が許可されていない場合、ユーザーにリクエストする
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 権限が許可されている場合、位置情報を取得するための処理を開始
            startLocationUpdates();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ユーザーが権限を許可した場合、位置情報を取得するための処理を開始
                startLocationUpdates();
            } else {
                // ユーザーが権限を拒否した場合、適切にハンドリングする
                // 例: ユーザーに権限が必要である旨のメッセージを表示
            }
        }
    }
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 位置情報の取得処理を実装
            // ...
            fusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), locationCallback, null);
        }
    }
    private void generateRouteAsync() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // バックグラウンドで非同期にルートを生成する処理
            generateRandomRoute();
            Log.d("RouteDebug", "Route generation complete");
        });
    }
    private void generateRandomRoute() {
        if (googleMap != null && currentLatLng != null) {
            try {
                LatLng origin = currentLatLng;
                LatLng pointA = generateNextPoint(origin);
                LatLng pointB = generateNextPoint(pointA);
                LatLng pointC = generateNextPoint(pointB);

                Log.d("RouteDebug", "Origin: " + origin.toString());
                Log.d("RouteDebug", "PointA: " + pointA.toString());
                Log.d("RouteDebug", "PointB: " + pointB.toString());
                Log.d("RouteDebug", "PointC: " + pointC.toString());

                drawRoute(origin, pointA);
                drawRoute(pointA, pointB);
                drawRoute(pointB, pointC);
                drawRoute(pointC, origin);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private LatLng generateNextPoint(LatLng previousPoint) {
        Random random = new Random();
        double distance = 200 / 4; // ユーザから入力された距離を考慮して調整
        double angle = random.nextDouble() * 2 * Math.PI; // ランダムな角度
        double latitudeOffset = distance * Math.cos(angle) / 111.32; // 緯度の変化
        double longitudeOffset = distance * Math.sin(angle) / (111.32 * Math.cos(previousPoint.latitude)); // 経度の変化
        return new LatLng(previousPoint.latitude + latitudeOffset, previousPoint.longitude + longitudeOffset);
    }

    private void drawRoute(LatLng origin, LatLng destination) {
//        try {
//            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
//                    .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
//                    .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
//                    .mode(TravelMode.WALKING)
//                    .await();
//
//            if (result.routes != null && result.routes.length > 0) {
//                List<LatLng> decodedPath = decodePolyline(result.routes[0].overviewPolyline.getEncodedPath());
//
//                // ポリラインの色を設定
//                PolylineOptions polylineOptions = new PolylineOptions()
//                        .addAll(decodedPath)
//                        .color(Color.BLUE);
//
//                googleMap.addPolyline(polylineOptions);
//            }
//
//        } catch (ApiException | InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
        DirectionsApiRequest directionsApiRequest = DirectionsApi.newRequest(geoApiContext)
                .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                .mode(TravelMode.WALKING);

        Log.d("DirectionsAPI", "Request: " + directionsApiRequest.toString());


        try {
            DirectionsResult result = directionsApiRequest.await();

            // エラーメッセージの取得
            if (result.geocodedWaypoints != null && result.geocodedWaypoints.length > 0) {
                String errorMessage = result.geocodedWaypoints[0].partialMatch ? "Partial match" : "";
                Log.e("DirectionsAPI", "Error response: " + errorMessage);
            } else {
                // ルートがある場合の処理
//                Log.d("DirectionsAPI", "Status: " + result.status);
            }

        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private LatLng getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // ここで現在地の取得を行う
            // 例: FusedLocationProviderClientを使用して最後の既知の位置を取得する
            // 参考: https://developer.android.com/training/location/retrieve-current
            return new LatLng(35.6895, 139.6917); // 仮の値
        } else {
            return new LatLng(0, 0);
        }
    }

    private List<LatLng> decodePolyline(String encodedPolyline) {
        List<LatLng> points = new ArrayList<>();
        int index = 0, len = encodedPolyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng(lat / 1E5, lng / 1E5);
            points.add(p);
        }
        return points;
    }
    private void getCurrentLocationAndMoveMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 位置情報の取得処理を実装
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            // 現在地を中心にマップを移動
                            moveMapToCurrentLocation();
                        }
                    });
        }
    }
    private void moveMapToCurrentLocation() {
        if (googleMap != null && currentLatLng != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
        }
    }
    @Override







    //    画面遷移---------------------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtSmp=view.findViewById(R.id.textsample);


//        Button btn1 = view.findViewById(R.id.route1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_routepopup2);
//            }
//        });
//
//
//
//        Button btn = view.findViewById(R.id.start1);
//        if (btn != null) {
//            btn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_routestart);
//                }
//            });
//        }
//
//
//
//        Button bt1 = view.findViewById(R.id.set);
//        bt1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_routepopup);
//            }
//        });



        //ここまで

//        Button ro = view.findViewById(R.id.set);//投稿削除確認ポップアップ画面
//        ro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = requireContext();
//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.fragment_route2);
//
//                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
//                params.width = 811; // 幅を変更
//                params.height = 900; // 高さを変更
//                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//
//                Button a = dialog.findViewById(R.id.distance1);
//                Button b = dialog.findViewById(R.id.time);
//
//                a.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
////                dialog.show();
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Navigation.findNavController(view).navigate(R.id.action_navigation_route2_to_navigation_route3);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
//
//
//        Button ru = view.findViewById(R.id.route1);//投稿削除確認ポップアップ画面
//        ru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = requireContext();
//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.fragment_route4);
//
//                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
//                params.width = 811; // 幅を変更
//                params.height = 900; // 高さを変更
//                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//
//                Button c = dialog.findViewById(R.id.place);
//                Button d = dialog.findViewById(R.id.favorite);
//
//                c.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
////                dialog.show();
//                d.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Navigation.findNavController(view).navigate(R.id.action_navigation_route4_to_navigation_route6);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });



    }




//    -----------------------------------------------------------------------------------------------------------

}



