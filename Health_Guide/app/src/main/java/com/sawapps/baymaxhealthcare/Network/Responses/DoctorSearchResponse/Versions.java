package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

/**
 * Created by SaiTejaswi Koppuravuri
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Versions {

    @SerializedName("hero")
    @Expose
    public String hero;
    @SerializedName("large")
    @Expose
    public String large;
    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("small")
    @Expose
    public String small;

}
