package com.example.smrp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.icu.util.ICUUncheckedIOException;
import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import me.relex.circleindicator.CircleIndicator;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;

    Button loginBtn;
    Button singupBtn;

    final long DELAY_MS = 1000;
    final long PERIOD_MS = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewPager = findViewById(R.id.view);
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        loginBtn = findViewById(R.id.loginB);
        singupBtn = findViewById(R.id.signB);
        loginBtn.setOnClickListener(this);
        singupBtn.setOnClickListener(this);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


    }

    @Override
    public void onClick(View v) {

    }
}




class ViewPagerAdapter extends PagerAdapter {


    private int[] images = {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context)
    {
        this.context = context;

    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

    @Override
    public boolean isViewFromObject( @NonNull View view,  @NonNull Object o) {
        return (view == (LinearLayout)o);
    }
    @Override
    public @NonNull Object instantiateItem(@NonNull ViewGroup contatiner, int position)
    {
        inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View v = inflater.inflate(R.layout.pager, contatiner, false);
        ImageView imageView = v.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        contatiner.addView(v);
        return v;
    }
}