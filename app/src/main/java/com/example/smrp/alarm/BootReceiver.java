package com.example.smrp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.example.smrp.LoginActivity;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.User;
import com.example.smrp.UserAlarm;


import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BootReceiver extends BroadcastReceiver {
    public AlarmManager alarmManager;
    final RetrofitService retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
    Calendar calendar ;
    int count = 0;
    @Override
    public void onReceive(final Context context, Intent intent) {
        calendar=Calendar.getInstance();
        if(Objects.equals(intent.getAction(),"android.intent.action.BOOT_COMPLETED")){

            final Intent my_intent = new Intent(context, Alarm_Reciver.class);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,alarmIntent,0);

            AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences loginInfromation = context.getSharedPreferences("setting",0);
            SharedPreferences.Editor editor = loginInfromation.edit();
            String user_id = loginInfromation.getString("id","");
            String user_pass = loginInfromation.getString("password","");
            String name = loginInfromation.getString("name","");
            String getAutoLogin = loginInfromation.getString("auto_login","");

            if(getAutoLogin.equals("true")){
                User user = new User(user_id,"",user_pass,"","",""); //서버에서 USER 클래스를 받기에 불필요한 매개변수가 들어가도 이해할것
                Call<UserAlarm> call = retrofitService.login(user);
                call.enqueue(new Callback<UserAlarm>() {
                    @Override
                    public void onResponse(Call<UserAlarm> call, Response<UserAlarm> response) {
                        if(response.body().getAlramMedicines().size()!=0){
                            for(int i=0; i<response.body().getAlramMedicines().size(); i++){
                                switch (response.body().getAlramMedicines().get(i).getOneTimeCapacity()){
                                    case 1:{
                                        if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                            calendar.set(Calendar.HOUR_OF_DAY,11);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);
                                        }else{
                                            calendar.set(Calendar.HOUR_OF_DAY,13);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);
                                        }

                                        break;
                                    }
                                    case 2:{
                                        if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                            calendar.set(Calendar.HOUR_OF_DAY,7);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                            calendar.set(Calendar.HOUR_OF_DAY,17);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender2 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);
                                        }else{
                                            calendar.set(Calendar.HOUR_OF_DAY,9);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                            calendar.set(Calendar.HOUR_OF_DAY,18);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender2 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);
                                        }
                                        break;
                                    }
                                    case 3:{
                                        if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                            calendar.set(Calendar.HOUR_OF_DAY,7);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                            calendar.set(Calendar.HOUR_OF_DAY,11);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender2 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);

                                            calendar.set(Calendar.HOUR_OF_DAY,17);      //식전
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender3 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+200,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender3);
                                        }else{
                                            calendar.set(Calendar.HOUR_OF_DAY,9);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                            calendar.set(Calendar.HOUR_OF_DAY,13);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender2 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);

                                            calendar.set(Calendar.HOUR_OF_DAY,18);      //식후
                                            calendar.set(Calendar.MINUTE,00);
                                            if(calendar.before(Calendar.getInstance())){
                                                calendar.add(Calendar.DATE, 1);
                                            }
                                            PendingIntent sender3 = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId()+200,my_intent,0);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender3);
                                        }
                                        break;
                                    }

                                    default:{


                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAlarm> call, Throwable t) {

                    }
                });
            }
        }
    }
}
