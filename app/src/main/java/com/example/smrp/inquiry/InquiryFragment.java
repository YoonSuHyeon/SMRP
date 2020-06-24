package com.example.smrp.inquiry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.MainActivity;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryFragment extends Fragment {

    private InquiryViewModel root;
    private RetrofitService retrofitService;
    private Button btn_send;
    private TextView content;
    private SharedPreferences login_inform;
    private String userid, message;
    private AlertDialog.Builder diaglog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = ViewModelProviders.of(this).get(InquiryViewModel.class);
        View root = inflater.inflate(R.layout.inquiry_fragment, container, false);

        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
        diaglog  = new AlertDialog.Builder(getContext());
        diaglog.setCancelable(false);
        content = root.findViewById(R.id.content); //사용자가 입력한 내용
        btn_send = root.findViewById(R.id.sendbutton); //전송하기 버튼
        login_inform = getActivity().getSharedPreferences("setting",0);

        userid = login_inform.getString("id","");
        Log.d("TAG", "useriduserid: "+userid);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = String.valueOf(content.getText());
                if(message.equals("")){ //메시지 입력없이 전송하기 버튼을 누를경우
                    Toast.makeText(getActivity().getApplicationContext(),"내용을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG", "message: "+message);
                    Send_Message send_message = new Send_Message(message,userid);
                    Call<response> call = retrofitService.sendInquiry(send_message);

                    call.enqueue(new Callback<response>() {
                        @Override
                        public void onResponse(Call<response> call, Response<response> response) {
                            diaglog.setTitle("전송 성공");
                            diaglog.setMessage("문의한 내용이 정상적으로 전송하였습니다.");
                            diaglog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                            diaglog.show();

                        }

                        @Override
                        public void onFailure(Call<response> call, Throwable t) {
                            diaglog.setTitle("전송 실패");
                            diaglog.setMessage("문의한 내용이 전송 실패하였습니다.");

                            diaglog.show();
                            diaglog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                        }
                    });
                }
            }
        });

        return root;
    }

}
