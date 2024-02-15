package com.example.petsns.ui.route;

import static com.example.petsns.LocationUtils.calculateDestinationLatLng;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.RouteViewViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RouteViewFragment extends Fragment  implements OnMapReadyCallback {

    private RouteViewModel viewModel;

    private GoogleMap googleMap;
    private GeoApiContext geoApiContext;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private List<LatLng> routePoints; // ルートの座標リストを保持するリスト
    private Polyline routePolyline;
    private FirebaseFirestore db;
    Boolean checkSearch;
    LatLng currentLatLng;
    TextView txtSmp;
    LatLng origin;
    LatLng intermediatePoint1;
    LatLng intermediatePoint2;
    LatLng intermediatePoint3;
    LatLng intermediatePoint4;
    int totalDistance;
    int distance;
    Button generateRouteButton;

    public static RouteViewFragment newInstance() {
        return new RouteViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_view, container, false);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();

        checkSearch = false;

        if (myApplication != null) {
            viewModel = myApplication.getSharedRouteViewModel();
        } else {
            // エラーハンドリング
        }

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


        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_route_view_to_navigation_route);
            }
        });

        generateRouteButton = view.findViewById(R.id.routeButton);
        generateRouteButton.setOnClickListener(v -> {
            // 権限の確認とリクエスト
//            txtSmp.setText(String.valueOf(viewModel.getDistance()));
            totalDistance = viewModel.getDistance();
            distance = totalDistance / 6;
            checkLocationPermission();
            googleMap.clear();
            if (viewModel.favoriteCheck) {
                new FetchDirectionsTask().execute(viewModel.getOrigin(),viewModel.getPoint1(),viewModel.getPoint2(),viewModel.getPoint3(),viewModel.getOrigin());
            } else {
                generateRoute();
            }
        });

        return view;
    }
    private void generateRoute() {
        if (currentLatLng != null) {
            Random random = new Random();
            // 0から360の範囲でランダムな数値を取得
            int randomAngle = random.nextInt(361);
            LatLng intermediatePoint1 = calculateDestinationLatLng(currentLatLng, distance,  random.nextInt(361)/*指定した方向*/);// 500m先の座標を計算（適切な方法で実装する必要があります）

            LatLng intermediatePoint2 = calculateDestinationLatLng(intermediatePoint1, distance,  random.nextInt(361)/* 新しい方向 */);
            LatLng intermediatePoint3 = calculateDestinationLatLng(intermediatePoint2, distance,  random.nextInt(361)/* 新しい方向 */);
            LatLng intermediatePoint4 = currentLatLng;

            // Directions APIを非同期で呼び出し、ルートを取得
            new FetchDirectionsTask().execute(currentLatLng, intermediatePoint1,intermediatePoint2, intermediatePoint3, intermediatePoint4);
        }
    }

    private class FetchDirectionsTask extends AsyncTask<LatLng, Void, DirectionsResult> {
        @Override
        protected DirectionsResult doInBackground(LatLng... params) {
            origin = params[0];
            intermediatePoint1 = params[1];
            intermediatePoint2 = params[2];
            intermediatePoint3 = params[3];
            intermediatePoint4 = params[4];

            try {
                // 現在地から中継地点1までのルート
                DirectionsResult route1 = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.WALKING)
                        .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                        .destination(new com.google.maps.model.LatLng(intermediatePoint1.latitude, intermediatePoint1.longitude))
                        .await();

                // 中継地点1から中継地点2までのルート
                DirectionsResult route2 = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.WALKING)
                        .origin(new com.google.maps.model.LatLng(intermediatePoint1.latitude, intermediatePoint1.longitude))
                        .destination(new com.google.maps.model.LatLng(intermediatePoint2.latitude, intermediatePoint2.longitude))
                        .await();

                DirectionsResult route3 = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.WALKING)
                        .origin(new com.google.maps.model.LatLng(intermediatePoint2.latitude, intermediatePoint2.longitude))
                        .destination(new com.google.maps.model.LatLng(intermediatePoint3.latitude, intermediatePoint3.longitude))
                        .await();

                DirectionsResult route4 = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.WALKING)
                        .origin(new com.google.maps.model.LatLng(intermediatePoint3.latitude, intermediatePoint3.longitude))
                        .destination(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                        .await();


                DirectionsResult completeRoute = concatenateRoutes(route1, route2, route3, route4);




                return completeRoute;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private DirectionsResult concatenateRoutes(DirectionsResult... routes) {
            if (routes != null && routes.length > 0) {
                // 各ルートの総数を計算
                int totalRoutesLength = 0;
                for (DirectionsResult route : routes) {
                    if (route != null && route.routes != null) {
                        totalRoutesLength += route.routes.length;
                    }
                }

                // すべてのルートを結合して新しい DirectionsResult を作成
                DirectionsResult concatenatedRoute = new DirectionsResult();
                concatenatedRoute.routes = new DirectionsRoute[totalRoutesLength];

                int currentIdx = 0;
                for (DirectionsResult route : routes) {
                    if (route != null && route.routes != null) {
                        System.arraycopy(route.routes, 0, concatenatedRoute.routes, currentIdx, route.routes.length);
                        currentIdx += route.routes.length;
                    }
                }

                checkSearch=true;
                return concatenatedRoute;
            } else {
                return null;
            }
        }


        @Override
        protected void onPostExecute(DirectionsResult directionsResult) {
            if (directionsResult != null && directionsResult.routes.length > 0) {
                DirectionsRoute finalRoute = directionsResult.routes[0]; // 最初のルートを取得

                if (finalRoute.legs != null && finalRoute.legs.length > 0) {
                    // 最終的なルートの総距離を取得（メートル単位）
                    int totalDistanceInMeters = 0;

                    for (DirectionsRoute route : directionsResult.routes) {
                        if (route != null && route.legs != null && route.legs.length > 0) {
                            // 各ルートの最初のLegオブジェクトから距離を取得
                            totalDistanceInMeters += route.legs[0].distance.inMeters;
                        }
                    }

                    // totalDistanceInMeters に最終的なルートの総距離が格納されます


                    if(totalDistance-500<=totalDistanceInMeters &&totalDistanceInMeters<=totalDistance+500){
                        generateRouteButton.setText("ルートを再検索");
                        txtSmp.setText(Integer.toString(totalDistanceInMeters)+"m");
                        routePoints = new ArrayList<>();
                        // ルートを描画
                        for (DirectionsRoute route : directionsResult.routes) {
                            routePoints.addAll(decodePolyline(route.overviewPolyline.getEncodedPath()));
                        }
                        if (routePolyline != null) {
                            routePolyline.remove();
                        }

                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(routePoints)
                                .color(Color.BLUE)
                                .width(3);
                        routePolyline = googleMap.addPolyline(polylineOptions);

                        // ルート全体が表示されるようにカメラを調整
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng point : routePoints) {
                            builder.include(point);
                        }
                        LatLngBounds bounds = builder.build();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                        if (!routePoints.isEmpty()) {
                            LatLng startLatLng = routePoints.get(0); // ルートの最初の座標を開始地点とする
                            addStartMarker(startLatLng);
                        }
                    }else{
                        generateRoute();
                    }
                }





            }
        }
    }

    private void addStartMarker(LatLng startLatLng) {
        MarkerOptions startMarkerOptions = new MarkerOptions()
                .position(startLatLng)  // 開始地点の座標
                .title("Start Point");  // マーカーのタイトル

        Marker startMarker = googleMap.addMarker(startMarkerOptions);
        startMarker.showInfoWindow();  // マーカーの情報ウィンドウを表示
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
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 権限が許可されていない場合、ユーザーにリクエストする
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
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
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 位置情報の取得処理を実装
            // ...
            fusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), locationCallback, null);
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
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
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

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtSmp = view.findViewById(R.id.textsample);

        Button favoriteButton = view.findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.popup_route_submit);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 500; // 幅を変更
                params.height = 400; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnCancel = dialog.findViewById(R.id.btnRouteCan);
                Button btnSub = dialog.findViewById(R.id.btnRouteSub);


                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText txtRoute=dialog.findViewById(R.id.txtRouteName);
                        if(txtRoute!=null){
                            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference collectionRefId = db.collection("userId");
                                if (checkSearch) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    String userUid = user.getUid();
                                    collectionRefId.whereEqualTo("uid", userUid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(Task<QuerySnapshot> task1) {
                                                    if (task1.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                            // ドキュメントが見つかった場合、IDを取得
                                                            String userId = document1.getId();

                                                            Map<String, Object> data = new HashMap<>();
                                                            data.put("id", userId);

                                                            data.put("originLongitude", origin.longitude);
                                                            data.put("originLatitude", origin.latitude);

                                                            data.put("point1Longitude", intermediatePoint1.longitude);
                                                            data.put("point1Latitude", intermediatePoint1.latitude);

                                                            data.put("point2Longitude", intermediatePoint2.longitude);
                                                            data.put("point2Latitude", intermediatePoint2.latitude);

                                                            data.put("point3Longitude", intermediatePoint3.longitude);
                                                            data.put("point3Latitude", intermediatePoint3.latitude);

                                                            data.put("name", txtRoute.getText().toString());
                                                            data.put("timestamp", FieldValue.serverTimestamp());

                                                            CollectionReference routeCollection = db.collection("routeFavorite");

                                                            routeCollection.document(UUID.randomUUID().toString()).set(data);
                                                            Toast.makeText(requireContext(), "ルートが登録されました", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            });
                            try {
                                future1.get(); // 非同期処理が終わるまでブロック

                            } catch (InterruptedException | ExecutionException e) {
                                // 例外処理
                            }
                        }

                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
//
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RouteViewViewModel.class);
        // TODO: Use the ViewModel
    }

}