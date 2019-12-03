package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Insurance {

    @SerializedName("insurance_plan")
    @Expose
    public InsurancePlan insurancePlan;
    @SerializedName("insurance_provider")
    @Expose
    public InsuranceProvider insuranceProvider;

}
