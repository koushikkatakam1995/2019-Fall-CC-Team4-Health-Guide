package com.sawapps.baymaxhealthcare.Network.GetDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Appointment {

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("appointment_date")
    @Expose
    public long appointmentDate;

    @SerializedName("user_id")
    @Expose
    public String userId;

    @SerializedName("user_name")
    @Expose
    public String userName;

    @SerializedName("doctor_id")
    @Expose
    public String doctorId;

    @SerializedName("doctor_name")
    @Expose
    public String doctorName;

    @SerializedName("status")
    @Expose
    public String status;

}
