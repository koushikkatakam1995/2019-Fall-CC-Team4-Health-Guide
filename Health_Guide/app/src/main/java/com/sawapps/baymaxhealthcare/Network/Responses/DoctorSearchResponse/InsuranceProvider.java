package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class InsuranceProvider {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("uid")
    @Expose
    public String uid;

}
