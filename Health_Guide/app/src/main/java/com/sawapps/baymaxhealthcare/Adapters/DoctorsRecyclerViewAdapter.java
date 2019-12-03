package com.sawapps.baymaxhealthcare.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.sawapps.baymaxhealthcare.HomeActivity;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.Doctor;
import com.sawapps.baymaxhealthcare.R;
import com.sawapps.baymaxhealthcare.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Koushik Katakam
 */

public class DoctorsRecyclerViewAdapter extends RecyclerView.Adapter<DoctorsRecyclerViewAdapter.DoctorsViewHolder> {

    List<Doctor> data;

    HomeActivity homeActivity;

    public DoctorsRecyclerViewAdapter(List<Doctor> data, HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        this.data = data;
    }

    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.doctor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorsViewHolder holder, int position) {
        try {
            final Doctor current = data.get(position);
            if (current != null) {


                holder.name.setText(current.profile.firstName + " " + current.profile.middleName + " " + current.profile.lastName);
                holder.bio.setText(current.profile.bio);

                holder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // Initialize
                        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                                "Book an Appointment",
                                "Book",
                                "Cancel"
                        );

// Assign values
                        dateTimeDialogFragment.startAtCalendarView();
                        dateTimeDialogFragment.set24HoursMode(false);
                        dateTimeDialogFragment.setMinimumDateTime(new Date());
                        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2020, Calendar.DECEMBER, 31).getTime());
                        dateTimeDialogFragment.setDefaultDateTime(new Date());

// Define new day and month format
                        try {
                            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
                        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                            Log.e("dtest", e.getMessage());
                        }

// Set listener
                        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Date date) {
                                // Date is get on positive button click
                                // Do something

                                Log.v("ttest", date.getTime() + "");
                                Utils.bookAppointment(holder.itemView.getContext(), current, date);

                            }

                            @Override
                            public void onNegativeButtonClick(Date date) {
                                // Date is get on negative button click
                            }
                        });

// Show
                        if (homeActivity != null)
                            dateTimeDialogFragment.show(homeActivity.getSupportFragmentManager(), "dialog_time");

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

    class DoctorsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView bio;
        View book;

        public DoctorsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            bio = itemView.findViewById(R.id.bio);
            book = itemView.findViewById(R.id.book);
        }
    }
}
