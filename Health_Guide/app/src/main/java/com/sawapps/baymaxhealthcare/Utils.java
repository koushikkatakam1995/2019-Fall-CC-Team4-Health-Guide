package com.sawapps.baymaxhealthcare;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;
import com.sawapps.baymaxhealthcare.Network.Responses.BookAppointmentResponse;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.Doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Utils {


    public static String BASE_URL = "http://ec2-54-85-214-162.compute-1.amazonaws.com:8080";


    static void showSnackbar(View view, String string) {

        Snackbar.make(view, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createEvent(Context ctx, Date date, Doctor doctor) {

        try {
            long dtstart = date.getTime();
            ContentResolver cr = ctx.getContentResolver();
            ContentValues values = new ContentValues();

            values.put(CalendarContract.Events.DTSTART, dtstart);
            values.put(CalendarContract.Events.TITLE, "Dr." + doctor.profile.firstName + "'s Appointment");
            values.put(CalendarContract.Events.DESCRIPTION, "Doctor Appointment");

            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());


// Default calendar
            values.put(CalendarContract.Events.CALENDAR_ID, 1);

            ;
            values.put(CalendarContract.Events.DURATION, "+P1H");

//        values.put(CalendarContract.Events.ALLOWED_REMINDERS, CalendarContract.Reminders.METHOD_ALARM);

            values.put(CalendarContract.Events.HAS_ALARM, 1);

// Insert event to calendar
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                Toast.makeText(ctx, "Need Write Calender Permission to write events to calender, Please enable it in Settings...", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = 0;
            if (uri != null) {
                eventID = Long.parseLong(uri.getLastPathSegment());
                createReminder(ctx, eventID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createReminder(Context ctx, long eventID) {

        try {
            ContentResolver cr = ctx.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, 15);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(final Context context, final Doctor current, final Date date) {

        if (current != null && date != null && context != null) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("appointment_date", date.getTime());
            params.put("user_name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            params.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            params.put("doctor_id", current.uid);
            params.put("doctor_name", current.profile.firstName + " " + current.profile.middleName + " ");

            ApiUtils.getService()
                    .bookAppointment(params)
                    .enqueue(new Callback<BookAppointmentResponse>() {
                        @Override
                        public void onResponse(Call<BookAppointmentResponse> call, Response<BookAppointmentResponse> response) {
                            try {

                                if (response.body().data != null) {


                                    Toast.makeText(context, "Booked an Appointment Successfully", Toast.LENGTH_SHORT).show();

                                    context.startActivity(new Intent(context, MyAppointmentsActivity.class));

                                    createEvent(context, date, current);

                                } else {
                                    Toast.makeText(context, "Error in booking appointment", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<BookAppointmentResponse> call, Throwable t) {
                            t.printStackTrace();

                        }
                    });
        } else {
            Toast.makeText(context, "Something went wrong, Please try again...", Toast.LENGTH_SHORT).show();
        }

    }


    public static void saveLocally(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("ORNews", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        Log.v("locale", "savingLocally " + value);
    }


    public static String getLocal(Context context, String key) {
        String locale = context.getSharedPreferences("ORNews", Context.MODE_PRIVATE).getString(key, null);

        return locale;

    }


    static String getBuildNumber(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


}
