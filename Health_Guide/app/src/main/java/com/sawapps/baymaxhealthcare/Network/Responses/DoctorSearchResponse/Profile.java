package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaiTejaswi Koppuravuri
 */
public class Profile {

    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("languages")
    @Expose
    public List<Language_> languages = new ArrayList<Language_>();
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("middle_name")
    @Expose
    public String middleName;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("title")
    @Expose
    public String title;

}
