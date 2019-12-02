package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

/**
 * Created by SaiTejaswi Koppuravuri
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VisitAddress {

    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("state_long")
    @Expose
    public String stateLong;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("zip")
    @Expose
    public String zip;
    @SerializedName("street2")
    @Expose
    public String street2;

}