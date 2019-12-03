package com.sawapps.baymaxhealthcare.Network.Remote;

import com.sawapps.baymaxhealthcare.Network.Responses.BookAppointmentResponse;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.CancelAppointmentsResponse;
import com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse.DoctorSearchResponse;
import com.sawapps.baymaxhealthcare.Network.Responses.GetAppointmentsResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Koushik Katakam
 */

public interface Service {


    @GET
    Call<DoctorSearchResponse> searchDoctors(@Url String url);

    @GET
    Call<GetDietResponse> getMeal(@Url String url, @Header("X-Mashape-Key") String s, @Header("X-Mashape-Host") String s2);

    @POST("/api/appointment/bookappointment")
    @FormUrlEncoded
    Call<BookAppointmentResponse> bookAppointment(@FieldMap HashMap<String, Object> map);

    @POST("/api/appointment/getappointments")
    @FormUrlEncoded
    Call<GetAppointmentsResponse> getAppointments(@FieldMap HashMap<String, Object> map);

    @POST("/api/appointment/cancelappointment")
    @FormUrlEncoded
    Call<CancelAppointmentsResponse> cancelAppointment(@FieldMap HashMap<String, Object> map);
}

