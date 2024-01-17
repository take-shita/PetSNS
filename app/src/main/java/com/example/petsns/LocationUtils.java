package com.example.petsns;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtils {

    // ある座標から指定した方向に一定の距離だけ移動した先の座標を計算するメソッド
    public static LatLng calculateDestinationLatLng(LatLng startLatLng, double distanceInMeters, double bearing) {
        double earthRadius = 6371000.0; // 地球の半径（メートル）

        // 緯度・経度をラジアンに変換
        double startLatRad = Math.toRadians(startLatLng.latitude);
        double startLngRad = Math.toRadians(startLatLng.longitude);
        double bearingRad = Math.toRadians(bearing);

        // 新しい座標の緯度を計算
        double newLatRad = Math.asin(Math.sin(startLatRad) * Math.cos(distanceInMeters / earthRadius) +
                Math.cos(startLatRad) * Math.sin(distanceInMeters / earthRadius) * Math.cos(bearingRad));

        // 新しい座標の経度を計算
        double newLngRad = startLngRad + Math.atan2(Math.sin(bearingRad) * Math.sin(distanceInMeters / earthRadius) * Math.cos(startLatRad),
                Math.cos(distanceInMeters / earthRadius) - Math.sin(startLatRad) * Math.sin(newLatRad));

        // ラジアンを度に戻して新しい座標を返す
        return new LatLng(Math.toDegrees(newLatRad), Math.toDegrees(newLngRad));
    }
}
