package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CancelAppointmentsResponse implements Serializable {

    @SerializedName("result")
    @Expose
    public int result;

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;
}
