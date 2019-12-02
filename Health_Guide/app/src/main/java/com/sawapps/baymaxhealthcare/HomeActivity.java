package com.sawapps.baymaxhealthcare;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sawapps.baymaxhealthcare.Adapters.DoctorsRecyclerViewAdapter;
import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.DoctorSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    RecyclerView doctorsRv;
    NavigationView navigationView;

    View selectLocation, search;

    AppCompatSpinner spinner;

    private Snackbar mSnackbarGps;
    private Snackbar mSnackbarPermissions;

    private static final int PERMISSIONS_REQUEST = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static String[] PERMISSIONS_REQUIRED = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Health Guide");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        doctorsRv = (RecyclerView) findViewById(R.id.doctorsRv);
        doctorsRv.setLayoutManager(new LinearLayoutManager(this));

        selectLocation = findViewById(R.id.select_location);
        search = findViewById(R.id.search);

        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDoctors();
            }
        });
        categories.add("All");
        categories.add("pediatrician");
        categories.add("internist");
        categories.add("cardiologist");
        categories.add("general-surgeon");
        categories.add("general-dentist");
        categories.add("gastroenterologist");
        categories.add("anesthesiologist");
        categories.add("obstetrics-gynecologist");

        setupSpinner();
        getDoctors();

        checkLocationPermission();

    }

    private void checkLocationPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST);
        } else {
            checkGpsEnabled();
        }
    }

    /**
     * Third and final validation check - ensures GPS is enabled, and if not, prompts to
     * enable it, otherwise all checks pass so start the location tracking service.
     */
    private void checkGpsEnabled() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm != null) {
            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                reportGpsError();
            } else {
                resolveGpsError();
                Log.v("ltest", "intentionally starting location service after 4 secs");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startLocationService();
                    }
                }, 5000);


            }
        }
    }

    /**
     * Callback for location permission request - if successful, run the GPS check.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            // We request storage perms as well as location perms, but don't care
            // about the storage perms - it's just for debugging.
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        reportPermissionsError();
                    } else {
                        resolvePermissionsError();
                        checkGpsEnabled();
                    }
                }
            }
        }
    }


    private void reportPermissionsError() {

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.rootView),
                        getString(R.string.location_permission_required),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.enable, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings
                                .ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(
                android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void resolvePermissionsError() {
        if (mSnackbarPermissions != null) {
            mSnackbarPermissions.dismiss();
            mSnackbarPermissions = null;
        }
    }

    public void showGpsErrorSnackBar() {


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.rootView),
                        getString(R.string.gps_required),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.enable, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));


                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id
                .snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void reportGpsError() {

        changeGPSSettings();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ltest", "onActivityResult()" + Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        Log.v("ltest", "OK");
                        // All required changes were successfully made
                        Toast.makeText(this, "Location enabled", Toast.LENGTH_LONG).show();
                        Log.v("ltest", "intentionally starting location service after 4 secs");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                startLocationService();
                            }
                        }, 5000);

                        break;
                    }
                    case Activity.RESULT_CANCELED: {

                        Log.v("ltest", "Canceled");
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
    }


    public void changeGPSSettings() {

        Log.v("ltest", "changeGPSSettings");
        LocationRequest request = new LocationRequest();
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);
        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(HomeActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(HomeActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                Log.v("ltest", "changeGPSSettings success");
                Toast.makeText(HomeActivity.this, "Success 2", Toast.LENGTH_SHORT).show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startLocationService();
                    }
                }, 5000);


            }
        });

        task.addOnFailureListener(HomeActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();

                Log.v("ltest", "changeGPSSettings failure " + statusCode);
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:

                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().

                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                            showGpsErrorSnackBar();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        showGpsErrorSnackBar();
                        break;
                }
            }
        });

    }

    private void startLocationService() {

        Log.v("ltest", "startLocationService");
        //request location now
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0,
                    new android.location.LocationListener() {
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }

                        @Override
                        public void onLocationChanged(final Location location) {

                            Log.v("ltest", location.getLatitude() + "");
                        }

                    });
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off

                        if (location != null) {
                            Log.v("ltest", "location found not null");
                            onLocationChanged(location);
                        } else {

                            Log.v("ltest", "location found null");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ltest", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    Location currentLocation;

    private void onLocationChanged(Location location) {

        Log.v("ltest", "onLocationChanged");
        if (location != null) {

            currentLocation = location;
            getDoctors();
        }
    }


    private void resolveGpsError() {
        if (mSnackbarGps != null) {
            mSnackbarGps.dismiss();
            mSnackbarGps = null;
        }
    }


    ArrayAdapter<String> dataAdapter;

    List<String> categories = new ArrayList<>();

    private void setupSpinner() {
        spinner = findViewById(R.id.spinner);

        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(HomeActivity.this);


        dataAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }


    public void getDoctors() {

//     "https://api.betterdoctor.com/2016-03-01/doctors?location=37.773%2C-122.413%2C100&user_location=37.773%2C-122.413&skip=0&limit=30&user_key=76a2878a9e8d28dcd556ba0c53461174";
        String query = "https://api.betterdoctor.com/2016-03-01/doctors?location=37.773,-122.413,100&skip=2&limit=10&user_key=" + "76a2878a9e8d28dcd556ba0c53461174";

        if (currentLocation != null) {
            Log.v("lltest", "location found");
            query = "https://api.betterdoctor.com/2016-03-01/doctors?location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + ",100&skip=0&limit=30&user_key=" + "76a2878a9e8d28dcd556ba0c53461174";
        } else {
            Log.v("lltest", "location found null");
        }

        if (currentCategory != null) {
            if (!currentCategory.equals("All")) {
                query += "&specialty_uid=" + currentCategory;
            }
        }
        Log.v("query", query);
        ApiUtils.getService()
                .searchDoctors(query)
                .enqueue(new Callback<DoctorSearchResponse>() {
                    @Override
                    public void onResponse(Call<DoctorSearchResponse> call, Response<DoctorSearchResponse> response) {
                        try {

                            if (response.body().data != null) {
                                doctorsRv.setAdapter(new DoctorsRecyclerViewAdapter(response.body().data, HomeActivity.this));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorSearchResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
            if (FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (navigationView != null)
                navigationView.getMenu().getItem(0).setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_3:

                startActivity(new Intent(this, BMIActivity.class));
                break;
            case R.id.nav_2:

                startActivity(new Intent(this, MyDietActivity.class));
                break;

            case R.id.nav_4:

                startActivity(new Intent(this, MyAppointmentsActivity.class));

                break;

            case R.id.nav_5:

                startActivity(new Intent(this, SettingsActivity.class));

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    String currentCategory = "All";

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        currentCategory = categories.get(position);
        getDoctors();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
