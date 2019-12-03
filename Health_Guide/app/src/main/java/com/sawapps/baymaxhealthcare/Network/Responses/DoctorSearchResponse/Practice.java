package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koushik Katakam
 */
public class Practice {

    @SerializedName("accepts_new_patients")
    @Expose
    public Boolean acceptsNewPatients;
    @SerializedName("distance")
    @Expose
    public Double distance;
    @SerializedName("insurance_uids")
    @Expose
    public List<String> insuranceUids = new ArrayList<String>();
    @SerializedName("languages")
    @Expose
    public List<Language> languages = new ArrayList<Language>();
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("location_slug")
    @Expose
    public String locationSlug;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("media")
    @Expose
    public List<Medium> media = new ArrayList<Medium>();
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phones")
    @Expose
    public List<Phone> phones = new ArrayList<Phone>();
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("visit_address")
    @Expose
    public VisitAddress visitAddress;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("within_search_area")
    @Expose
    public Boolean withinSearchArea;

}
