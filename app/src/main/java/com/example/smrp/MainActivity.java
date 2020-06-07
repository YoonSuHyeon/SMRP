package com.example.smrp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.appbar);
        toolbar.setTitle(R.string.app_bar_title);

        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //iv_headerImage.setImage();

        name = getIntent().getStringExtra("name");

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        TextView text = (TextView) header.findViewById(R.id.tv_header);
        text.setText(name + "님 \n\r 환영합니다.");
        ImageView imageView =(ImageView) header.findViewById(R.id.iv_headerImage);
        imageView.setImageDrawable(getDrawable(R.drawable.clear_sky1));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_alarm, R.id.nav_hospital,
                R.id.nav_inquiry, R.id.nav_logout,R.id.nav_medicine,R.id.nav_pharmacy,R.id.nav_report)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed(){ //네비게이션 드로어를 클릭하여 활성화 한 후 물리적으로 뒤로가기 버튼을 했을때 드로어 창이 닫힘
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu) ;

        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_logout :
                Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                sharedPreferences = getSharedPreferences("setting",0);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this,StartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
