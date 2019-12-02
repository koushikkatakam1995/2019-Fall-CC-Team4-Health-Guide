package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class Medium {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tags")
    @Expose
    public List<String> tags = new ArrayList<String>();
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("versions")
    @Expose
    public Versions versions;

}
