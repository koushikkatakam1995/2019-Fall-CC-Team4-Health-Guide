package com.sawapps.baymaxhealthcare.Network.GetDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Nutrients {

    @SerializedName("calories")
    @Expose
    public double calories;
    @SerializedName("protein")
    @Expose
    public double protein;
    @SerializedName("fat")
    @Expose
    public double fat;
    @SerializedName("carbohydrates")
    @Expose
    public double carbohydrates;

}
