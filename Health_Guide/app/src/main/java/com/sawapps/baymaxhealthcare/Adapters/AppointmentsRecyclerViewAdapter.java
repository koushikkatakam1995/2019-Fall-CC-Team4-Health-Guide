package com.sawapps.baymaxhealthcare.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sawapps.baymaxhealthcare.Network.GetDiet.Appointment;
import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.CancelAppointmentsResponse;
import com.sawapps.baymaxhealthcare.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Koushik Katakam
 */

public class AppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<AppointmentsRecyclerViewAdapter.AppointmentsViewHolder> {

    private List<Appointment> data;

    public AppointmentsRecyclerViewAdapter(List<Appointment> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.appointment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentsViewHolder holder, int position) {
        try {
            final Appointment current = data.get(position);
            if (current != null) {


                holder.doctorName.setText("Doctor's Name : " + current.doctorName);
                holder.userName.setText("Patient's Name : " + current.userName);


                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                Date resultdate2 = new Date(current.appointmentDate);
                holder.date.setText(sdf2.format(resultdate2));

                if (current.status != null)
                    holder.status.setText(current.status);

                holder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        new MaterialDialog.Builder(holder.itemView.getContext())
                                .title("Cancel Appointment")
                                .content("Are you sure, you wanna cancel your appointment")
                                .positiveText("Yes")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        if (current.status != null && current.status.equals("confirmed")) {
                                            HashMap<String, Object> map = new HashMap<>();
                                            map.put("id", current.id);
                                            ApiUtils.getService().cancelAppointment(map)
                                                    .enqueue(new Callback<CancelAppointmentsResponse>() {
                                                        @Override
                                                        public void onResponse(Call<CancelAppointmentsResponse> call, Response<CancelAppointmentsResponse> response) {
                                                            try {

                                                                if (response.body().message != null) {


                                                                    Toast.makeText(holder.itemView.getContext(), response.body().message, Toast.LENGTH_SHORT).show();

                                                                }

                                                                if (response.body().status != null)
                                                                    current.status = response.body().status;

                                                                notifyDataSetChanged();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<CancelAppointmentsResponse> call, Throwable t) {
                                                            t.printStackTrace();

                                                        }
                                                    });
                                        }
                                    }
                                }).show();


                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class AppointmentsViewHolder extends RecyclerView.ViewHolder {

        TextView doctorName;
        TextView userName;
        TextView date;

        TextView status;

        public AppointmentsViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            userName = itemView.findViewById(R.id.user_name);
            date = itemView.findViewById(R.id.appointment_time);
            status = itemView.findViewById(R.id.status);
        }
    }
}
