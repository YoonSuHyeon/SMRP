package com.example.smrp.medicine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;

import com.example.smrp.MainActivity;
import com.example.smrp.R;
import com.example.smrp.home.HomeAlarmFragment;
import com.example.smrp.home.HomeFragment;

public class ViewPagerAdapter extends PagerAdapter {


    private int[] images;
    private LayoutInflater inflater;
    private Context context;
    private int flag =0;
    public ViewPagerAdapter(Context context, int []images, int flag) {
        this.images = images;
        this.context = context;
        this.flag = flag;
    }
    public ViewPagerAdapter(Context context, int []images)
    {
        this.images = images;
        this.context = context;



       // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.ContentLayout, fragment.newInstance()).commit();






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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout)o);
    }
    @Override
    public @NonNull Object instantiateItem(@NonNull final ViewGroup container, final int position)
    {
        inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View v = inflater.inflate(R.layout.pager, container, false);
        ImageView imageView = v.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        v.setOnClickListener(new View.OnClickListener() { //이미지를 클릭 했을때.
            @Override
            public void onClick(View v1) {
                Toast.makeText(context,"이미지"+position,Toast.LENGTH_SHORT).show();

                View root = inflater.inflate(R.layout.home_fragment, container, false);


                   // Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_medicine);

                if(flag == 1){

                    NavHostFragment navHostFragment =
                            (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager()
                                    .findFragmentById(R.id.nav_host_fragment);
                    NavController navController = navHostFragment.getNavController();




                    if(position == 0){
                        navController.navigate(R.id.action_nav_home_to_nav_medicine);
                    }
                    else if(position==1){
                        navController.navigate(R.id.action_nav_home_to_nav_inquiry);

                    }
                    else if(position == 2){

                        navController.navigate(R.id.action_nav_home_to_nav_look_up);
                    }


                }







            }
        });
        container.addView(v);
        return v;
    }
}