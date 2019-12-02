package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class License {

    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("number")
    @Expose
    public String number;

}
