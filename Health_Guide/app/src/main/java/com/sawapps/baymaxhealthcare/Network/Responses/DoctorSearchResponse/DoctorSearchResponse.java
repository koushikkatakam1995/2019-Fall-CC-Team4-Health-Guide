package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DoctorSearchResponse {

    @SerializedName("data")
    @Expose
    public List<Doctor> data = new ArrayList<Doctor>();
    @SerializedName("meta")
    @Expose
    public Meta meta;

}

