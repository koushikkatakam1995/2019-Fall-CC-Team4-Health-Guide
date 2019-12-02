package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class InsurancePlan {

    @SerializedName("category")
    @Expose
    public List<String> category = new ArrayList<String>();
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("uid")
    @Expose
    public String uid;

}
