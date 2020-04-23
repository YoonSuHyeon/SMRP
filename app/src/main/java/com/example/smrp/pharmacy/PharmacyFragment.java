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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {

   // private PharmacyViewModel pharmacyViewModel;
    private View root;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Location location;
    private double latitude, longitude;
    private String[] phy_inf;
    private ArrayList<String> total_phy = new ArrayList<String>();
    private MapPOIItem marker1, marker2;
    private Handler handler;
    private RecyclerView recyclerView;
    private Button btn_call, find_road;
    private ArrayList<Pharmacy> list;
    private PharmacyAdapter adapter;
    private ImageView btn_location, btn_research;
    private LinearLayoutManager mlinearLayoutManager;
    private Pharmacy pharmacy;
    private int radiuse = 300, count = 0 ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        startLocationService(); //사용자의 현재위치를 좌표를 가져오기 위한 클래스 호출

        /*if(pharmacyViewModel==null)
            pharmacyViewModel =
                ViewModelProviders.of(this).get(PharmacyViewModel.class);*/
        container.removeAllViews();
        root = inflater.inflate(R.layout.pharmacy_fragment, container, false);

        /*btn_research = root.findViewById(R.id.btn_research); //지도의 중앙값 좌표를 통해 반경 radius 약국목록을 가져오기 위한 버튼
        btn_location = root.findViewById(R.id.btn_location); //지도의 위치를 사용자 위치로 변경하기 위함*/
        recyclerView = root.findViewById(R.id.recycle_view);
        mlinearLayoutManager = new LinearLayoutManager(root.getContext());

        recyclerView.setLayoutManager(mlinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        createMapView(); //mapView 객체를 생성하고 mapView의 이벤트 처리

        list = new ArrayList<>();
        adapter = new PharmacyAdapter(list);
        recyclerView.setAdapter(adapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mlinearLayoutManager.getOrientation());//구분선을 넣기 위함

        recyclerView.addItemDecoration(dividerItemDecoration);


        adapter.setOnItemClickListener(new PharmacyAdapter.OnPharmacyItemClickListener() { // 약국 리스트를 눌렀을 때 처리하는 어댑터!!!!!!!!!
            @Override
            public void onItemClick(PharmacyAdapter.ViewHolder holder, View view, int position) {

            }

            @Override
            public void onCallClick(int position) {

            }

            @Override
            public void onPath(int position) {

            }
        });

        RetrofitService json = new RetrofitFactory().create();
        json.getList(latitude,longitude,radiuse).enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if(response.isSuccessful()){

                    count = response.body().getCount();
                    for(int i =0; i< response.body().count;i++){
                        String add  = response.body().getList().get(i).getAddr(); //주소
                        String crate_data = response.body().getList().get(i).getCreated_at(); //데이터 생성일자
                        float latitude = response.body().getList().get(i).getLat(); //경도
                        float longitude = response.body().getList().get(i).getLng(); //위도
                        String name = response.body().getList().get(i).getName(); //이름
                        String remain_state = response.body().getList().get(i).getRemain_stat(); // 마스크 보유량
                        String input_time = response.body().getList().get(i).getStock_at(); // 마스크 입고시간
                        String type = response.body().getList().get(i).getType();
                        addMarker(add,crate_data,latitude,longitude,name,remain_state,input_time,type);
                    }
                    Log.d("TAG", "response.body().getCount().size: "+response.body().getCount());
                }
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                Log.d("데이터 가져오기 실패:",t.toString());
            }
        });
        return root;
    }

    private void startLocationService(){ //사용자의 위치 좌표를 가져오기 위한 클래스
        LocationManager locationManager1 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성
        LocationManager locationManager2 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 1000;//단위 위치정보를 초기화 하기 위한 기준 설정 (millisecond , m)
        float minDistance = 10;//단위 m
        try {
            locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener); //// 위치 기반을 GPS모듈을 이용함
            locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener);//// 위치 기반을 네트워크 모듈을 이용함
            //5초 마다 or 10m 이동할떄마다 업데이트   network는 gps에 비해 정확도가 떨어짐
            location = locationManager1.getLastKnownLocation(LocationManager.GPS_PROVIDER);//최근 gps기록  실내에서는 안잡힐수가 있다
            if (location != null) {
                latitude = location.getLatitude(); // GPS 모듈 경도 값 ex) 37.30616958190577
                longitude = location.getLongitude(); //GPS 모듈 위도 값 ex) 127.92099856059595
            }else{
                location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);///네트워크로 얻은 마지막 위치좌표를 이용
                latitude = location.getLatitude(); //네트워크 경도 값
                longitude = location.getLongitude(); // 네트워크 위도 값
            }
        } catch (SecurityException e) { //보안적인 예외처리 발생시 실행
            e.printStackTrace();
        }

    }
    private void createMapView(){ //MapView 객체 선언과 이벤트 설정하는 클래스
        if(mapView==null)
            mapView = new MapView(getActivity()); // MapView 객체 선언

        mapViewContainer = (ViewGroup)root.findViewById(R.id.map_view); // mapViewContainer 선언
        mapViewContainer.addView(mapView);

        /*btn_location.setVisibility(View.VISIBLE);
        btn_research.setVisibility(View.VISIBLE);*/
        mapView.setMapViewEventListener(this); //MapView의 Event 처리를 위함
        mapView.setPOIItemEventListener(this); // MapView의 marker 표시를 위함
        mapView.setCurrentLocationEventListener(this); // MapView의 현재위치 리스너

        //중심적 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true);// 중심점 변경
        setMapView(latitude,longitude);

    }
    private void setMapView(double latitude, double longitude){ //MapView의 인터페이스 설정 클래스
        //하이브리드 맵 설정
        //mapView.setMapType(MapView.MapType.Hybrid); //Standard ,Statllite, Hybrid

        //고해상도
        //mapView.setHDMapTileEnabled(true);


        //줌 레벨 변경
        mapView.setZoomLevel(2,true);

        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);
        // 트랙
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading); //트래킥 모등 on + 나침반 모드 on
        //Log.d("TAG", "TrackingMode:"+mapView.getCurrentLocationTrackingMode());

        // 원 색상 적용
        mapView.setCurrentLocationRadiusStrokeColor(Color.argb(128,255,0,0));
        // 중심점에 Marker 로 표시해줍니다
       // CenterMarker(latitude, longitude);
        //Toast.makeText(getActivity().getApplicationContext(),"사용자 위치 반경 "+pharmacyViewModel.radius+"m 약국을 검색합니다.",Toast.LENGTH_LONG);
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
        mapView.addPOIItem(marker1); //사용자의 최초 위치를 지도 위에 marker표시 하기 위함
       // Toast.makeText(getActivity().getApplicationContext(),"사용자 위치 반경 "+pharmacyViewModel.radius+"m 약국을 검색합니다.",Toast.LENGTH_LONG);
    }
    private void addMarker(String addr, String created_at, float latitude, float longitude, String name, String remain_stat, String stock_at,String type){
        //mapView.removeAllPOIItems(); //mapview 의 marker 표시를 모두 지움(새로운 marker를 최신화 하기 위해)
        total_phy.clear(); //ArrayList total_phy 의 모든 값을 clear()

        marker2= new MapPOIItem(); // 약국들을 mapview 에 표시하기 전에 marker를 생성함.
        marker2.setItemName(name); //marker의 타이틀(제목)값을 부여
        marker2.setTag(1);//MapView 객체에 등록된 POI Item들 중 특정 POI Item을 찾기 위한 식별자로 사용할 수 있음.
        marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude)); //mapview의 초점을 marker를 중심으로 함
        marker2.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker2.setCustomImageResourceId(R.drawable.pharmacy_icon2); //커스텀 icon 을 설정하기 위함
        marker2.setCustomImageAutoscale(false);// hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌
        //marker2.setAlpha(0.2f);// marker 투명도
        mapView.addPOIItem(marker2);//mapview위에 marker 띄우기

        pharmacy = new Pharmacy(addr, created_at, latitude, longitude, name,remain_stat,stock_at,type);
        list.add(pharmacy);
        adapter.notifyDataSetChanged();

    }
    private class GPSListener implements LocationListener {//위치리너스 클래스

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

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
        /*MapPoint.GeoCoordinate mPointGeo = mapPoint.getMapPointGeoCoord();
        longitude = mPointGeo.longitude;
        latitude = mPointGeo.latitude;*/
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
///////////////////////////지도의 반경을 그릴때 이 값을 표현 하기 위해 서 최신화 해주기위한 클래스 호출 고려
    
    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) { //지도의 레벨이
        Toast.makeText(getActivity().getApplicationContext(),"zoom_level:"+i,Toast.LENGTH_LONG).show();
        switch (i){
            case 1:
                break;
            case 2:
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:
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
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) { // 지도의 이동이 완료된 경우
        //Toast.makeText(getContext().getApplicationContext(),"end of move",Toast.LENGTH_LONG).show();
        latitude = mapPoint.getMapPointGeoCoord().latitude;
        longitude = mapPoint.getMapPointGeoCoord().longitude;
        //mapView.removeAllPOIItems();
        //marker 중심으로 그릴 원 반경 지정
        mapView.setCurrentLocationRadius(radiuse);

    }
}
