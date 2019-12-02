package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class Specialty {

    @SerializedName("actor")
    @Expose
    public String actor;
    @SerializedName("actors")
    @Expose
    public String actors;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("uid")
    @Expose
    public String uid;

}
