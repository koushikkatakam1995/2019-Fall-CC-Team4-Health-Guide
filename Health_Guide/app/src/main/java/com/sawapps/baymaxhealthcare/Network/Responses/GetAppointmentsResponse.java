package com.sawapps.baymaxhealthcare.Network.Responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sawapps.baymaxhealthcare.Network.GetDiet.Appointment;

import java.io.Serializable;
import java.util.List;

public class GetAppointmentsResponse implements Serializable {

    @SerializedName("result")
    @Expose
    public int result;

    @SerializedName("data")
    @Expose
    public List<Appointment> data;
}
