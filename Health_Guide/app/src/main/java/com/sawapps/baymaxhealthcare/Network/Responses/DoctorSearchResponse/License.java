package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class License {

    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("number")
    @Expose
    public String number;

}
