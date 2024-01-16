package com.example.petsns.ui.route;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
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
public class routeFragment extends DashboardFragment implements OnMapReadyCallback {


    private RouteViewModel mViewModel;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private GeoApiContext geoApiContext;

    LatLng currentLatLng;
    TextView txtSmp;



    private void generateRandomRoute() {
        try {
            // 出発地点
            LatLng origin = currentLatLng;
            // A地点
            LatLng pointA = generateNextPoint(origin);
            // B地点
            LatLng pointB = generateNextPoint(pointA);
            // C地点
            LatLng pointC = generateNextPoint(pointB);

            // 出発地からA地点までのルート
            drawRoute(origin, pointA,Color.RED);
            // A地点からB地点までのルート
            drawRoute(pointA, pointB,Color.BLUE);
            // B地点からC地点までのルート
            drawRoute(pointB, pointC, Color.GREEN);
            // C地点から出発地までのルート
            drawRoute(pointC, origin,Color.MAGENTA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LatLng generateNextPoint(LatLng previousPoint) {
        Random random = new Random();
        double distance = 2000 / 4; // ユーザから入力された距離を考慮して調整
        double angle = random.nextDouble() * 2 * Math.PI; // ランダムな角度
        double latitudeOffset = distance * Math.cos(angle) / 111.32; // 緯度の変化
        double longitudeOffset = distance * Math.sin(angle) / (111.32 * Math.cos(previousPoint.latitude)); // 経度の変化
        return new LatLng(previousPoint.latitude + latitudeOffset, previousPoint.longitude + longitudeOffset);
    }

    private void drawRoute(LatLng origin, LatLng destination, int color) {
        try {
            com.google.maps.model.LatLng originLatLng =
                    new com.google.maps.model.LatLng(origin.latitude, origin.longitude);
            com.google.maps.model.LatLng destinationLatLng =
                    new com.google.maps.model.LatLng(destination.latitude, destination.longitude);

            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .origin(originLatLng)
                    .destination(destinationLatLng)
                    .mode(TravelMode.WALKING)
                    .await();

            if (result.routes != null && result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];
                DirectionsLeg leg = route.legs[0];

                // PolylineOptionsを構築
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(decodePolyline(route.overviewPolyline.getEncodedPath())).color(Color.RED);;

                // マップにポリラインを描画
                googleMap.addPolyline(polylineOptions);

            }

        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
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

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // パーミッションが許可されているか確認
        if (hasLocationPermission()) {
            enableMyLocation();
        } else {
            // パーミッションが許可されていない場合はリクエスト
            requestLocationPermission();
        }
    }
    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    private void enableMyLocation() {
        try {
            // 位置情報の更新をリクエスト
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5000); // 更新の間隔（ミリ秒）
            locationRequest.setFastestInterval(1000); // 最短の更新間隔（ミリ秒）
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            // 最後に知られている位置情報を取得
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    updateCameraPosition(location);
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
            // SecurityExceptionが発生した場合の対処を行う
        }
    }

    private void updateCameraPosition(Location location) {
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
    }

    private void centerMapOnMyLocation() {
        try {
            if (hasLocationPermission()) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        updateCameraPosition(location);
                    }
                });
            } else {
                // パーミッションが許可されていない場合はリクエスト
                requestLocationPermission();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            // SecurityExceptionが発生した場合の対処を行う
        }
        // ボタンを押したときに現在地を中心にして地図を更新

    }

    // パーミッションのリクエスト結果をハンドル
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // パーミッションが許可された場合の処理
                enableMyLocation();
            } else {
                // パーミッションが拒否された場合の処理
                // 必要に応じてユーザーにメッセージを表示して説明するなどの対応を行う
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // アプリが非表示になるときに位置情報の更新を停止
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }




    public static routeFragment newInstance() {
        return new routeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_samplemap, container, false);

        Button centerButton = view.findViewById(R.id.centerButton);
        centerButton.setOnClickListener(v -> centerMapOnMyLocation());
        geoApiContext = new GeoApiContext.Builder().apiKey("AIzaSyB7PVa9P1isPm5kkSEDlXuaVXepW7v17Fw").build();
        Button generateRouteButton = view.findViewById(R.id.routeButton);
        generateRouteButton.setOnClickListener(v -> generateRandomRoute());
        return view;
//        return inflater.inflate(R.layout.fragment_route, container, false);
    }


    //    画面遷移---------------------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtSmp=view.findViewById(R.id.textsample);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        // 位置情報が更新されたときに通知を受け取りますが、ここでは何もしません
                    }
                }
            }
        };

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



