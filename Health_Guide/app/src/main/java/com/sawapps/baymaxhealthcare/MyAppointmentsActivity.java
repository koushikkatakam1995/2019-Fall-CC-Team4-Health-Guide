package com.sawapps.baymaxhealthcare;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.sawapps.baymaxhealthcare.Adapters.AppointmentsRecyclerViewAdapter;
import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;
import com.sawapps.baymaxhealthcare.Network.Responses.GetAppointmentsResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        setTitle("My Appointments");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAppointments();
    }

    public void getAppointments() {


        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", FirebaseAuth.getInstance().getUid());

        ApiUtils.getService()
                .getAppointments(map)
                .enqueue(new Callback<GetAppointmentsResponse>() {
                    @Override
                    public void onResponse(Call<GetAppointmentsResponse> call, Response<GetAppointmentsResponse> response) {
                        try {


                            recyclerView.setAdapter(new AppointmentsRecyclerViewAdapter(response.body().data));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAppointmentsResponse> call, Throwable t) {

                        t.printStackTrace();
                    }
                });


    }
}
