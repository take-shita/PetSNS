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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.petsns.ui.snstop.TestPost;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static com.example.petsns.LocationUtils.calculateDestinationLatLng;
import com.google.maps.model.DirectionsLeg;

public class routeFragment extends DashboardFragment {


    private RouteViewModel viewModel;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private FavoriteRouteAdapter routeAdapter;
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


        EditText txtDis = view.findViewById(R.id.textDistance);
        Button btn = view.findViewById(R.id.start1);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

//                    txtDis.setText("3000");
                    try {

                        viewModel.setDistance(Integer.parseInt(txtDis.getText().toString()));
                        viewModel.FalseFavoriteCheck();
                        Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route_view);
                    } catch (Exception e) {

                    }

                }
            });
        }

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


        Button btnFavorite = view.findViewById(R.id.btnFavorite);//投稿削除確認ポップアップ画面
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_routepopup2);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                recyclerView=dialog.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                routeAdapter = new FavoriteRouteAdapter(requireContext(),requireActivity(),view,dialog);
                recyclerView.setAdapter(routeAdapter);

                firestore = FirebaseFirestore.getInstance();
                firestore.collection("routeFavorite")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {


                                List<FavoriteRoute> routes = new ArrayList<>();

                                db = FirebaseFirestore.getInstance();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String userUid = user.getUid();


                                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                                    CollectionReference collectionRefId = db.collection("userId");
                                    collectionRefId.whereEqualTo("uid", userUid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(Task<QuerySnapshot> task1) {

                                                    if (task1.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                            // ドキュメントが見つかった場合、IDを取得
                                                            String userId = document1.getId();

                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                FavoriteRoute route = document.toObject(FavoriteRoute.class);

                                                                Map<String, Object> data = document.getData();
                                                                if(data.get("id").equals(userId)){
                                                                    String documentId = document.getId();

                                                                    route.setOrigin((Double) data.get("originLatitude"),(Double) data.get("originLongitude"));
                                                                    route.setPoint1((Double) data.get("point1Latitude"),(Double) data.get("point1Longitude"));
                                                                    route.setPoint2((Double) data.get("point2Latitude"),(Double) data.get("point2Longitude"));
                                                                    route.setPoint3((Double) data.get("point3Latitude"),(Double) data.get("point3Longitude"));

                                                                    route.setName((String)data.get("name"));

                                                                    routes.add(route);
                                                                }
                                                            }
                                                            routeAdapter.setRoutes(routes);
                                                        }
                                                    }
                                                }
                                            });
                                });
                            }
                        });
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
//                        Navigation.findNavController(view).navigate(R.id.action_navigation_routepopup2_to_navigation_routefavorite);
//                        dialog.dismiss();
//                    }
//                });
                dialog.show();
            }
        });
    }
}



