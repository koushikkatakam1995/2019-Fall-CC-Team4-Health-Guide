package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koushik Katakam
 */
public class Doctor {

    @SerializedName("insurances")
    @Expose
    public List<Insurance> insurances = new ArrayList<Insurance>();
    @SerializedName("licenses")
    @Expose
    public List<License> licenses = new ArrayList<License>();
    @SerializedName("npi")
    @Expose
    public String npi;
    @SerializedName("practices")
    @Expose
    public List<Practice> practices = new ArrayList<Practice>();
    @SerializedName("profile")
    @Expose
    public Profile profile;
    @SerializedName("specialties")
    @Expose
    public List<Specialty> specialties = new ArrayList<Specialty>();
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("educations")
    @Expose
    public List<Education> educations = new ArrayList<Education>();

}
