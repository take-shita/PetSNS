//package com.example.petsns;
//import androidx.lifecycle.ViewModelProvider;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.PolylineOptions;
//import java.util.List;
//
//public class MapFragment extends Fragment implements OnMapReadyCallback {
//    private static final double START_LATITUDE = 37.7749;  // ここに適切な開始緯度を設定
//    private static final double START_LONGITUDE = -122.4194;  // ここに適切な開始経度を設定
//    private static final double END_LATITUDE = 31.567175288814944;  /* ここに適切な終了緯度を設定 */;
//    private static final double END_LONGITUDE = 130.54191033248483; /* ここに適切な終了経度を設定 */;
//
//    private GoogleMap googleMap;
//
//    public static MapFragment newInstance() {
//        return new MapFragment();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }
//        return view;
//    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
//
//        // ルート検索を実行するメソッド
//        searchRoute(new LatLng(START_LATITUDE, START_LONGITUDE), new LatLng(END_LATITUDE, END_LONGITUDE));
//    }
//
//    private void searchRoute(LatLng start, LatLng end) {
//        // ルート検索のロジックを実装
//        // ここではPolylineを使用して経路を描画する例を示します
//
//        // ダミーの経路データ
//        List<LatLng> routePoints = getDummyRoutePoints(start, end);
//
//        // Polylineを追加
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .addAll(routePoints)
//                .width(5)
//                .color(getResources().getColor(R.color.your_color_primary));
//        googleMap.addPolyline(polylineOptions);
//
//        // カメラを経路に合わせて移動
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(routePoints), 100));
//    }
//
//    // ダミーの経路データを生成するメソッド
//    private List<LatLng> getDummyRoutePoints(LatLng start, LatLng end) {
//        // 実際にはDirections APIなどを使用して本番の経路データを取得するべきです
//        // ここでは簡単なダミーデータを生成しています
//        List<LatLng> routePoints = //...
//
//        return routePoints;
//    }
//
//    // 経路に合わせたLatLngBoundsを取得するメソッド
//    private LatLngBounds getLatLngBounds(List<LatLng> routePoints) {
//        // 経路データからLatLngBoundsを計算するロジックを実装
//        // ここでは簡単なダミーデータを使用しています
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (LatLng point : routePoints) {
//            builder.include(point);
//        }
//
//        return builder.build();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//}