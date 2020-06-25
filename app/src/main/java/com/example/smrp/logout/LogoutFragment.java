package com.example.smrp.logout;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.LoginActivity;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.User;
import com.example.smrp.UserAlarm;
import com.example.smrp.alarm.Alarm_Reciver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutFragment extends DialogFragment implements View.OnClickListener {

    private LogoutViewModel logOutViewModel;
    private SharedPreferences loginInfromation;
    private SharedPreferences.Editor editor;
    private FragmentManager fragmentManager;
    private Boolean bool_logout = false;
    public static AlarmManager alarmManager=null;
    public static PendingIntent pendingIntent=null;
    Intent intent;
    private Dialog enddialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RetrofitService retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());

        alertdialog.setCancelable(false);//외부영역 터치시 dismiss되는것을 방지
        alertdialog.setMessage("현재 계정을 종료하시겠습니까?");
        loginInfromation = getActivity().getSharedPreferences("setting",0);
        enddialog = new Dialog();

        alertdialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String user_id = loginInfromation.getString("id","");
                String user_pass = loginInfromation.getString("password","");
                String name = loginInfromation.getString("name","");
                String getAutoLogin = loginInfromation.getString("auto_login","");

                alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(getContext(),Alarm_Reciver.class);

                User user = new User(user_id,"",user_pass,"","",""); //서버에서 USER 클래스를 받기에 불필요한 매개변수가 들어가도 이해할것
                Call<UserAlarm> call = retrofitService.login(user);
                enddialog.execute();
                call.enqueue(new Callback<UserAlarm>() {
                    @Override
                    public void onResponse(Call<UserAlarm> call, Response<UserAlarm> response) {
                        if(response.body() !=  null){
                            try{
                                if(response.body().getAlramMedicines().size()!=0 && response.body() !=null){
                                    for(int i=0; i<response.body().getAlramMedicines().size(); i++){
                                        switch (response.body().getAlramMedicines().get(i).getOneTimeCapacity()){
                                            case 1:{
                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;
                                                break;
                                            }
                                            case 2:{
                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;

                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId()+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;
                                                break;
                                            }
                                            case 3:{
                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;

                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId()+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;

                                                pendingIntent = PendingIntent.getBroadcast(getContext(),response.body().getAlramMedicines().get(i).getAlramGroupId()+200,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                                alarmManager.cancel(pendingIntent);
                                                pendingIntent.cancel();
                                                pendingIntent=null;
                                                break;
                                            }
                                            default:{

                                            }
                                        }
                                    }

                                }
                            }catch (NullPointerException e){

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<UserAlarm> call, Throwable t) {

                    }
                });
                editor = loginInfromation.edit();
                editor.clear();
                editor.commit();
                //bool_logout = true;
                AlarmManager alarmManager =(AlarmManager)getContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), Alarm_Reciver.class);
                //  PendingIntent pendingIntent =PendingIntent.getBroadcast(root.getContext(),)

            }
        });

        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.commit();
                //fragmentManager.beginTransaction().remove(get).commit();
                fragmentManager.popBackStack();

            }
        });

        AlertDialog alert = alertdialog.create();
        //alert.setIcon();
        alert.show();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        logOutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        final View root = inflater.inflate(R.layout.logout_fragment, container, false);
        //final TextView textView = root.findViewById(R.id.text_look_up);
        root.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View v) {

    }


    private class Dialog extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*ViewGroup group = (ViewGroup) root.getParent();
            if(group!=null)
                group.removeView(root);*/
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로그아웃 중입니다.");


            // show dialog
            progressDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {
           /* while(!bool_logout)
                ;
            bool_logout = false;*/

            try {
                Thread.sleep(2500); // 2초 지속

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

            super.onPostExecute(result);
        }
    }

}