package com.example.smrp.logout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.R;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logOutViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean bool_logout = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logOutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        View root = inflater.inflate(R.layout.logout_fragment, container, false);
        //final TextView textView = root.findViewById(R.id.text_look_up);
        Dialog dialog = new Dialog();
        dialog.execute();

        sharedPreferences = getActivity().getSharedPreferences("setting",0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        //bool_logout = true;
        return root;
    }

    private class Dialog extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            /*ViewGroup group = (ViewGroup) root.getParent();
            if(group!=null)
                group.removeView(root);*/
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로그아웃 중입니다.");


            // show dialog
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
           /* while(!bool_logout)
                ;
            bool_logout = false;*/

            try {
                Thread.sleep(1000); // 2초 지속

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getActivity().finish();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();

            //finish();

            super.onPostExecute(result);
        }
    }

}
