package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Phone {

    @SerializedName("number")
    @Expose
    public String number;
    @SerializedName("type")
    @Expose
    public String type;

}
