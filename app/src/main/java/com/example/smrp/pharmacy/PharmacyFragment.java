package com.example.smrp.pharmacy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.R;
import com.example.smrp.pharmacy.PharmacyViewModel;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PharmacyFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {

    private PharmacyViewModel pharmacyViewModel;
    private View root;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Location location;
    private double latitude, longitude;
    private String[] phy_inf;
    private ArrayList<String> total_phy = new ArrayList<String>();
    private MapPOIItem marker1, marker2;
    private Handler handler;
    public ImageView btn_location, btn_research;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        startLocationService(); //현재 위치 좌표
        pharmacyViewModel =
                ViewModelProviders.of(this).get(PharmacyViewModel.class);
        root = inflater.inflate(R.layout.pharmacy_fragment, container, false);

        btn_research = root.findViewById(R.id.btn_research);
        btn_location = root.findViewById(R.id.btn_location);


        createMapView();

        pharmacyViewModel.setInformation(longitude,latitude);
        pharmacyViewModel.run();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pharmacyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d("TAG", "search_phy_onChanged: "+s+"\n");
                        if(s.equals("'"))
                            Toast.makeText(getActivity().getApplicationContext(),"현재 위치 반경 "+pharmacyViewModel.radius+"m 약국은 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                        else {
                            addMarker(s); // 찾은 약국들을 지도에 마커 표시 하기 위한 클래스 호출
                            s="";

                        }
                    }
                });
            }
        },2500); //스레드 처리로 인해 delay 2초간 부여

        return root;
    }

    @SuppressLint("ShowToast")
    private void startLocationService(){ //위치 좌표 가져오는 클래스
        LocationManager locationManager1 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성AllBusStopDustInfo
        LocationManager locationManager2 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성AllBusStopDustInfo
        GPSListener gpsListener = new GPSListener();
        long minTime = 1000;//단위 millisecond
        float minDistance = 10;//단위 m
        try {
            locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener); //// 위치 기반을 GPS모듈을 이용함
            ;//gps
            locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener);//// 위치 기반을 네트워크 모듈을 이용함
            //5초 마다 or 10m 이동할떄마다 업데이트   network는 gps에 비해 정확도가 떨어짐

            location = locationManager1.getLastKnownLocation(LocationManager.GPS_PROVIDER);//최근 gps기록  실내에서는 안잡힐수가 있다
            //location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);    ///네트워크로 얻은 마지막 위치좌표를 이용

            if (location != null) {
                latitude = location.getLatitude(); //ex) 37.30616958190577
                longitude = location.getLongitude(); //ex) 127.92099856059595
            }else{
                location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
    private void createMapView(){ //MapView 객체 선언과 이벤트 설정하는 클래스
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup)root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        Log.d("TAG", "getVisiblitity:"+btn_location.getVisibility());
        Log.d("TAG", "getVisiblitity:"+btn_research.getVisibility());
        btn_location.setVisibility(View.VISIBLE);
        btn_research.setVisibility(View.VISIBLE);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        setMapView(latitude,longitude);
    }
    private void setMapView(double latitude, double longitude){ //MapView의 인터페이스 설정 클래스
        //하이브리드 맵 설정
        //mapView.setMapType(MapView.MapType.Hybrid); //Standard ,Statllite, Hybrid
        Log.d("TAG", "getMapType(): "+mapView.getMapType());
        //고해상도
        //mapView.setHDMapTileEnabled(true);
        Log.d("TAG", "getMapTileMode(): "+mapView.getMapTileMode());
        //중심적 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true);// 중심점 변경
        //줌 레벨 변경
        mapView.setZoomLevel(2,true);

        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);
        // 트랙
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving); //트래킥 모등 on + 나침반 모드 on
        //Log.d("TAG", "TrackingMode:"+mapView.getCurrentLocationTrackingMode());
        //marker 중심으로 그릴 원 반경 지정
        mapView.setCurrentLocationRadius(pharmacyViewModel.radius);
        // 원 색상 적용
        mapView.setCurrentLocationRadiusStrokeColor(Color.argb(128,255,0,0));
        // 중심점에 Marker 로 표시해줍니다
       // CenterMarker(latitude, longitude);
        Toast.makeText(getActivity().getApplicationContext(),"사용자 위치 반경 "+pharmacyViewModel.radius+"m 약국을 검색합니다.",Toast.LENGTH_LONG);
    }
    private void CenterMarker(double latitude, double longitude){ //사용자 현재 위치를 Marker표시
        marker1 = new MapPOIItem();
        marker1.setItemName("현재위치");
        marker1.setTag(0);////MapView 객체에 등록된 POI Item들 중 특정 POI Item을 찾기 위한 식별자로 사용할 수 있음.
        marker1.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker1.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker1.setCustomImageResourceId(R.drawable.location_icon2);
        marker1.setCustomImageAutoscale(false);// hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌
        //marker1.setAlpha(0.2f);// marker 투명도
        mapView.addPOIItem(marker1);
        Toast.makeText(getActivity().getApplicationContext(),"사용자 위치 반경 "+pharmacyViewModel.radius+"m 약국을 검색합니다.",Toast.LENGTH_LONG);
    }
    private void addMarker(String inf){
        mapView.removeAllPOIItems();
        total_phy.clear();

        StringTokenizer token1 = new StringTokenizer(inf,"\n");
        int total_count = token1.countTokens();
        Log.d("TAG", "total_count: "+total_count+"\n");

        Toast.makeText(getActivity().getApplicationContext(), "총 "+total_count+"건의 약국을 검색하였습니다." , Toast.LENGTH_LONG).show();



        while(token1.hasMoreTokens()){
            total_phy.add(token1.nextToken()); //total_phy: ArrayList<String> 에 약국별로 넣기
        }
        int i = 0;
        while(i < total_phy.size()){ //total_phy 약국별 목록

            Log.d("TAG", "total_phy.size():"+total_phy.size()+"\n");
            StringTokenizer token2 = new StringTokenizer(total_phy.get(i),"/");
            phy_inf = new String[token2.countTokens()];

            phy_inf[0] = token2.nextToken(); //약국 주소
            phy_inf[1] = token2.nextToken(); //약국
            phy_inf[2] = token2.nextToken(); //약국 전화번호
            phy_inf[3] = token2.nextToken(); //약국 xPos
            phy_inf[4] = token2.nextToken(); //약국 yPos
            phy_inf[5] = token2.nextToken(); //약국 이름

            //marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
            double latitude = Double.parseDouble(phy_inf[3]);
            double longitude = Double.parseDouble(phy_inf[4]);
            Log.d("TAG", " latitude: "+ latitude+",longitude:"+longitude+"\n");
            marker2= new MapPOIItem();
            marker2.setItemName(phy_inf[5]);
            marker2.setTag(1);//MapView 객체에 등록된 POI Item들 중 특정 POI Item을 찾기 위한 식별자로 사용할 수 있음.
            marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(phy_inf[4]), Double.parseDouble(phy_inf[3])));
            marker2.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            marker2.setCustomImageResourceId(R.drawable.pharmacy_icon2);
            marker2.setCustomImageAutoscale(false);// hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌
            //marker2.setAlpha(0.2f);// marker 투명도
            mapView.addPOIItem(marker2);

            i++;
        }

    }
    private class GPSListener implements LocationListener {//위치리너스 클래스

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }//위치 공급자의 상태가 바뀔 때 호출

        @Override
        public void onProviderEnabled(String provider) {

        } //위치 공급자가 사용 가능해질(enabled) 때 호출

        @Override
        public void onProviderDisabled(String provider) {

        }//위치 공급자가 사용 불가능해질(disabled) 때 호출
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {//사용자가 MapView 에 등록된 POI Item 아이콘(마커)를 터치한 경우 호출된다.

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) { //사용하지 않은 메소드

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {//클릭한 Balloon의 정보를 가져온다.

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        //단말 사용자가 길게 누른 후(long press) 끌어서(dragging) 위치 이동이 가능한 POI Item의 위치를 이동시킨 경우 호출된다
        /*GeoCoordinate geoCoordinate = mapView.getMapCenterPoint();
        double latitude = geoCoordinate.latitude; // 위도
        double longitude = geoCoordinate.longitude; // 경도*/
        Log.d("TAG", "onDraggablePOIItemMoved: ===============>");

        Toast.makeText(getActivity().getApplicationContext(), "단말기의 방향 각도값:"+mapView.getMapCenterPoint(), Toast.LENGTH_LONG).show();
    }
    /*
     *  현재 위치 업데이트(setCurrentLocationEventListener)
     */
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) { // Tracking 모드가 켜진경우 단말의 현위치 좌표값을 통보받을 수 있다.
        MapPoint.GeoCoordinate mPointGeo = mapPoint.getMapPointGeoCoord();
        longitude = mPointGeo.longitude;
        latitude = mPointGeo.latitude;
        //Toast.makeText(getActivity().getApplicationContext(),"현재 위치 좌표 업데이트 됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) { //단말의 방향(Heading) 각도값을 통보받을 수 있다.
        //Toast.makeText(getActivity().getApplicationContext(), "단말기의 방향 각도값:"+v , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) { //현위치 갱신 작업에 실패한 경우 호출된다.

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {//현위치 트랙킹 기능이 사용자에 의해 취소된 경우 호출된다.
        //처음 현위치를 찾는 동안에 현위치를 찾는 중이라는 Alert Dialog 인터페이스가 사용자에게 노출된다.
        //첫 현위치를 찾기전에 사용자가 취소 버튼을 누른 경우 호출 된다.

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) { //지도 중심 좌표가 이동한 경우 호출 됨.
        //Toast.makeText(getActivity().getApplicationContext(),"지도 중심 좌표 변경",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        Toast.makeText(getActivity().getApplicationContext(),"zoom_level:"+i,Toast.LENGTH_LONG).show();
        switch (i){
            case 1:
                pharmacyViewModel.radius=500;
                break;
            case 2:
                pharmacyViewModel.radius=750;
                break;
            case 3:
                pharmacyViewModel.radius=1000;
                break;
            case 4:
                pharmacyViewModel.radius=1250;
                break;
            case 5:
                pharmacyViewModel.radius=1500;
                break;
            case 6:
                pharmacyViewModel.radius=1750;
                break;
            case 7:
                pharmacyViewModel.radius=2000;
                break;
            case 8:
                pharmacyViewModel.radius=2250;
                break;
            case 9:
                pharmacyViewModel.radius=2500;
                break;
            case 10:
                pharmacyViewModel.radius=2750;
                break;
        }
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) { //사용자가 지도 위 한지점을 터치 한 경우 호출

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) { //사용자가 지도 위 한지점을 연속으로 두번 터치 한 경우 호출

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) { //사용자가 지도 한 지점을 길게 누른경우

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) { //사용자가 지도 드래그를 시작한 경우

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) { //사용자가 지도 드래그를 끝낸 경우

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) { // 지동의 이동이 완료된 경우
        //Toast.makeText(getContext().getApplicationContext(),"end of move",Toast.LENGTH_LONG).show();
        latitude = mapPoint.getMapPointGeoCoord().latitude;
        longitude = mapPoint.getMapPointGeoCoord().longitude;
        mapView.removeAllPOIItems();

        pharmacyViewModel.setInformation(longitude,latitude);
        pharmacyViewModel.run();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pharmacyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d("TAG", "search_phy_onChanged: "+s+"\n");
                        if(s.equals("'"))
                            Toast.makeText(getActivity().getApplicationContext(),"현재 위치 반경 "+pharmacyViewModel.radius+"m 약국은 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                        else {
                            addMarker(s); // 찾은 약국들을 지도에 마커 표시 하기 위한 클래스 호출
                            s="";
                        }
                    }
                });
            }
        },2500); //스레드 처리로 인해 delay 2초간 부여
    }
}
