package com.example.smrp.hospital;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smrp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.kakao.kakaonavi.options.RpOption;
import com.kakao.kakaonavi.options.VehicleType;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {
    private View root;
    private double latitude=0.0 ,longitude=0.0; //사용자 위치
    private double movelatititue=0.0, movelongitude=0.0;//지도 움직임 위치
    private Location location;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private int radiuse=500;
    private MapPOIItem marker;
    private Hospital hospital;
    private ArrayList<String> total_hos = new ArrayList<String>();
    private ArrayList<Hospital> list;
    private HospitalAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mlinearLayoutManager;
    private Handler handler;
    private int count=0, i=1;
    private FloatingActionButton location_fb,research_fb;
    private Dialog dialog;
    private boolean boolean_start=false;
    private MapCircle mapCircle;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d("TAG", "Hos_container.count: "+container.getChildCount());
        dialog = new Dialog();
        dialog.execute();
        root = inflater.inflate(R.layout.hospital_fragment, container, false);


        startLocationService();

        recyclerView = root.findViewById(R.id.recycle_view); //recyclerView 객체 선언
        location_fb = root.findViewById(R.id.floatingActionButton1);
        research_fb = root.findViewById(R.id.floatingActionButton2);

        mlinearLayoutManager = new LinearLayoutManager(root.getContext()); // layout 매니저 객체 선언

        recyclerView.setLayoutManager(mlinearLayoutManager);
        recyclerView.setHasFixedSize(true); //리싸이클 뷰 안 아이템들의 크기를 가변적으로 바꿀건지(false) , 일정한 크기를 사용할 것인지(true)

        list = new ArrayList<>();
        adapter = new HospitalAdapter(list);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mlinearLayoutManager.getOrientation());//구분선을 넣기 위함
        recyclerView.addItemDecoration(dividerItemDecoration);


        location_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 트랙
                mapCircle = new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude),radiuse, Color.argb(128,255,0,0),Color.argb(128,95,0,255));
                mapCircle.setTag(2);
                mapView.removeAllCircles();
                mapView.addCircle(mapCircle);
                dialog = new Dialog();
                dialog.execute();
                mapView.removeAllPOIItems(); //mapview 의 marker 표시를 모두 지움(새로운 marker를 최신화 하기 위해)
                list.clear();
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                mapView.setCurrentLocationRadius(radiuse);
                mapCircle = new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude),radiuse, Color.argb(128,255,0,0),Color.argb(128,95,0,255));
                mapCircle.setTag(2);
                mapView.addCircle(mapCircle);
                re_parsingData(latitude,longitude,radiuse);

            }
        });
        research_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boolean_start){
                    dialog = new Dialog();
                    dialog.execute();
                    mapCircle = new MapCircle(MapPoint.mapPointWithGeoCoord(movelatititue, movelongitude),radiuse, Color.argb(128,255,0,0),Color.argb(128,95,0,255));
                    mapCircle.setTag(2);
                    mapView.removeAllCircles();
                    mapView.addCircle(mapCircle);
                    mapView.removeAllPOIItems(); //mapview 의 marker 표시를 모두 지움(새로운 marker를 최신화 하기 위해)
                    list.clear();
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(movelatititue, movelongitude), true);
                    mapView.setCurrentLocationRadius(radiuse);
                    re_parsingData(movelatititue, movelongitude, radiuse);
                    boolean_start=false;
                }

            }
        });
        adapter.setOnitemClickListener(new HospitalAdapter.OnHospitalItemClickListener() {
            @Override
            public void onItemClick(HospitalAdapter.ViewHolder holder, View viewm, int position) {
                String lat = list.get(position).getxPos();
                String lon = list.get(position).getyPos();
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(lon), Double.parseDouble(lat)), true);
            }

            @Override
            public void onCallClick(int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list.get(position).getTelno()));
                //ACTION_DIAL: 전화 다이얼로그 Action_call:전화 연결
                startActivity(intent);
                Toast.makeText(getActivity(),"통화 연결다이얼로그로 전환합니다.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUrl(int position) {
                if(!list.get(position).getHosurl().contains("www")){
                    Log.d("TAG", "onUrl: url not exist");
                    Toast.makeText(getActivity(),"해당 병원은 사이트가 존재 하지 않습니다.",Toast.LENGTH_LONG).show();
                }else{
                    Uri uri = Uri.parse(list.get(position).getHosurl());
                    Log.d("TAG", "onUrl: "+uri);
                    Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }

            }

            @Override
            public void onPath(int position) {
                if(KakaoNaviService.isKakaoNaviInstalled(getContext())){
                    Log.d("TAG", "yes: ");
                    Log.d("TAG", "name:"+list.get(position).getYadmNm() );
                    Log.d("TAG", "name:"+Double.parseDouble(list.get(position).getyPos() ));
                    Log.d("TAG", "name:"+Double.parseDouble(list.get(position).getxPos()) );
                    com.kakao.kakaonavi.Location location = com.kakao.kakaonavi.Location.newBuilder(list.get(position).getYadmNm(),Double.parseDouble(list.get(position).getxPos()),Double.parseDouble(list.get(position).getyPos())).build();
                    NaviOptions options = NaviOptions.newBuilder().setCoordType(CoordType.WGS84).setVehicleType(VehicleType.FIRST).setRpOption(RpOption.SHORTEST).build(); //setCoordType: 좌표계  setVehicleType: 차종  setRpOption: 경로 옵션
                    KakaoNaviParams parms = KakaoNaviParams.newBuilder(location).setNaviOptions(options).build();
                    KakaoNaviService.navigate(getActivity(),parms);
                }else{ //카카오 네비게이션 설치가 안되어 있을 경우
                    Log.d("TAG", "nononno ");

                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.locnall.KimGiSa"));
                    startActivity(intent);

                }

            }
        });
        Log.d("TAG", "latitude1: "+latitude);
        Log.d("TAG", "longitude1: "+longitude);

        parsingData(latitude,longitude,radiuse);

        return root;
    }

    private void parsingData(final double latitude, final double longitude, final int radiuse){
        RetrofitService json = new RetrofitFactory().create();
        Log.d("TAG", "latitude: "+latitude);
        Log.d("TAG", "longitude: "+longitude);

        json.getList(latitude,longitude,radiuse).enqueue(new Callback<Return_tag>() {
            @Override
            public void onResponse(Call<Return_tag> call, final Response<Return_tag> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.message());
                    Log.d("TAG", "size: "+response.body().response_tag.body.items.getItemsList().size() );

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            createMapView(); //mapView 객체를 생성하고 mapView의 이벤트 처리
                            count = response.body().response_tag.body.items.getItemsList().size();
                            Log.d("TAG", "count1: "+count);
                            for(int i =  0 ; i < count;i++){
                                String yadmNm = response.body().response_tag.body.items.getItemsList().get(i).getYadmNm();  //병원 이름
                                String clCdNm = response.body().response_tag.body.items.getItemsList().get(i).getClCdNm(); //병원 등급
                                String addr = response.body().response_tag.body.items.getItemsList().get(i).getAddr(); // 병원주소
                                String hosurl = response.body().response_tag.body.items.getItemsList().get(i).getHospUrl(); //병원 URL
                                String telno = response.body().response_tag.body.items.getItemsList().get(i).getTelno(); // 병원 전화번호
                                String xPos = response.body().response_tag.body.items.getItemsList().get(i).getXPos(); //병원 x좌표
                                String yPos = response.body().response_tag.body.items.getItemsList().get(i).getYPos(); //병원 x좌표
                                double distance = response.body().response_tag.body.items.getItemsList().get(i).getDistance(); //병원 x좌표
                                addMarker(yadmNm,clCdNm,addr,hosurl,telno,xPos,yPos,distance);
                            }
                            mapCircle = new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude),radiuse, Color.argb(128,255,0,0),Color.argb(128,95,0,255));
                            mapCircle.setTag(2);
                            mapView.addCircle(mapCircle);

                        }
                    },150);


                }else{
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"해당지역에는 병원이 없습니다.",Toast.LENGTH_LONG).show();
                            createMapView(); //mapView 객체를 생성하고 mapView의 이벤트 처리
                        }
                    },150);
                }

            }

            @Override
            public void onFailure(Call<Return_tag> call, Throwable t) {
                Toast.makeText(getActivity(),"데이터 불러오기 오류",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void re_parsingData(double latitude, double longitude, final int radiuse){
        RetrofitService json = new RetrofitFactory().create();
        Log.d("TAG", "latitude: "+latitude);
        Log.d("TAG", "longitude: "+longitude);
        json.getList(latitude,longitude,radiuse).enqueue(new Callback<Return_tag>() {
            @Override
            public void onResponse(Call<Return_tag> call, final Response<Return_tag> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.message());
                    Log.d("TAG", "size: "+response.body().response_tag.body.items.getItemsList().size() );

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            count = response.body().response_tag.body.items.getItemsList().size();
                            Log.d("TAG", "count1: "+count);
                            for(int i =  0 ; i < count;i++){
                                String yadmNm = response.body().response_tag.body.items.getItemsList().get(i).getYadmNm();  //병원 이름
                                String clCdNm = response.body().response_tag.body.items.getItemsList().get(i).getClCdNm(); //병원 등급
                                String addr = response.body().response_tag.body.items.getItemsList().get(i).getAddr(); // 병원주소
                                String hosurl = response.body().response_tag.body.items.getItemsList().get(i).getHospUrl(); //병원 URL
                                String telno = response.body().response_tag.body.items.getItemsList().get(i).getTelno(); // 병원 전화번호
                                String xPos = response.body().response_tag.body.items.getItemsList().get(i).getXPos(); //병원 x좌표
                                String yPos = response.body().response_tag.body.items.getItemsList().get(i).getYPos(); //병원 x좌표
                                double distance = response.body().response_tag.body.items.getItemsList().get(i).getDistance(); //병원 x좌표
                                addMarker(yadmNm,clCdNm,addr,hosurl,telno,xPos,yPos,distance);
                            }


                        }
                    },150);


                }else{
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"해당지역에는 병원이 없습니다.",Toast.LENGTH_LONG).show();
                        }
                    },150);
                }

            }

            @Override
            public void onFailure(Call<Return_tag> call, Throwable t) {
                Toast.makeText(getActivity(),"해당지역에는 병원이 없습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();
    }
    private void createMapView(){

        if(mapView == null) {
            mapView = new MapView(getContext());
        }
        if(mapViewContainer!=null){
            Log.d("TAG", "mapViewContainer not null: ");
            mapViewContainer.removeAllViews();
        }


        i++;
        mapViewContainer = (ViewGroup)root.findViewById(R.id.hos_map_view); // mapViewContainer 선언
        mapViewContainer.addView(mapView);

        /*btn_location.setVisibility(View.VISIBLE);
        btn_research.setVisibility(View.VISIBLE);*/
        mapView.setMapViewEventListener(this); //MapView의 Event 처리를 위함
        mapView.setPOIItemEventListener(this); // MapView의 marker 표시를 위함
        mapView.setCurrentLocationEventListener(this); // MapView의 현재위치 리스너

        setMapView(latitude, longitude);
    }
    private void setMapView(double latitude, double longitude){ //MapView의 인터페이스 설정 클래스
        //하이브리드 맵 설정
        //mapView.setMapType(MapView.MapType.Hybrid); //Standard ,Statllite, Hybrid

        // 내 현재위치 원 그리기
        mapView.setCurrentLocationRadius(radiuse);

        //고해상도
        //mapView.setHDMapTileEnabled(true);

        //중심적 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true);// 중심점 변경

        //줌 레벨 변경
        mapView.setZoomLevel(3,true);

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
        //Toast.makeText(getActivity().getApplicationContext(),"사용자 위치 반경 "+pharmacyViewModel.radius+"m 약국을 검색합니다.",Toast.LENGTH_LONG).show();
    }


    private void addMarker(String yadmNm, String clCdNm, String addr, String hosurl, String telno, String xPos, String yPos, double distance){
        //mapView.removeAllPOIItems(); //mapview 의 marker 표시를 모두 지움(새로운 marker를 최신화 하기 위해)
        total_hos.clear(); //ArrayList total_hos 의 모든 값을 clear()


        marker= new MapPOIItem(); // 약국들을 mapview 에 표시하기 전에 marker를 생성함.
        marker.setItemName(yadmNm); //marker의 타이틀(제목)값을 부여
        marker.setTag(1);//MapView 객체에 등록된 POI Item들 중 특정 POI Item을 찾기 위한 식별자로 사용할 수 있음.
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(yPos),Double.parseDouble(xPos))); //mapview의 초점을 marker를 중심으로 함
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setCustomImageResourceId(R.drawable.hospital_icon); //커스텀 icon 을 설정하기 위함
        marker.setCustomImageAutoscale(false);// hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌
        //marker2.setAlpha(0.2f);// marker 투명도
        mapView.addPOIItem(marker);//mapview위에 marker 띄우기

        if(addr.contains("("))
            addr = addr.substring(0, addr.indexOf("("));

        if(hosurl==null)
            hosurl="병원 사이트 없음.";
        hospital= new Hospital(yadmNm,clCdNm,addr,hosurl,telno,xPos,yPos,distance);
        list.add(hospital);
        adapter.notifyDataSetChanged();

    }

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


    }
    /*f
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
        movelatititue = mapPoint.getMapPointGeoCoord().latitude;
        movelongitude = mapPoint.getMapPointGeoCoord().longitude;
        boolean_start=true;
    }
///////////////////////////지도의 반경을 그릴때 이 값을 표현 하기 위해 서 최신화 해주기위한 클래스 호출 고려

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) { //지도의 레벨이 변경되었을때 호출
        //Toast.makeText(getActivity().getApplicationContext(),"zoom_level:"+i,Toast.LENGTH_LONG).show();
        switch (i){
            case 1:
                radiuse=100;
                break;
            case 2:
                radiuse=250;
                break;
            case 3:
                radiuse=500;
                break;
            case 4:
                radiuse=600;
                break;
            case 5:
                radiuse=700;
                break;
            case 6:
                radiuse=800;
                break;
            case 7:
                radiuse=900;
                break;
            case 8:
                radiuse=1000;
                break;
            case 9:
                radiuse=1100;
                break;
            case 10:
                radiuse=1200;
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

        // 트랙
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff); //트래킹 모드 on + 나침반 모드 on:TrackingModeOnWithoutHeading
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) { //사용자가 지도 드래그를 끝낸 경우
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) { // 지도의 이동이 완료된 경우
        //Toast.makeText(getContext().getApplicationContext(),"end of move",Toast.LENGTH_LONG).show();

    }


    ///////////////////////////  GPS 관련 클래스 ////////////////////////////////
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
                location=null;
            }else{
                location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);///네트워크로 얻은 마지막 위치좌표를 이용
                latitude = location.getLatitude(); //네트워크 경도 값
                longitude = location.getLongitude(); // 네트워크 위도 값
                location=null;
            }
        } catch (SecurityException e) { //보안적인 예외처리 발생시 실행
            e.printStackTrace();
        }
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

    private class Dialog extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            /*ViewGroup group = (ViewGroup) root.getParent();
            if(group!=null)
                group.removeView(root);*/
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중입니다..");

            // show dialog
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2500); // 2초 지속

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();

            //finish();
            Log.d("TAG", "onPostExecute: "+count);;
            Toast.makeText(getActivity(), "총"+count+"건을 검색하였습니다.", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
    }
}
