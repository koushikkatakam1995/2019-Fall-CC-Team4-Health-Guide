package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Education {

    @SerializedName("degree")
    @Expose
    public String degree;
    @SerializedName("school")
    @Expose
    public String school;
    @SerializedName("graduation_year")
    @Expose
    public String graduationYear;

}
