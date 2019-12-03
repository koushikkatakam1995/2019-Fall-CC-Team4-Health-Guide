package com.sawapps.baymaxhealthcare.Network.Responses;

/**
 * Created by Koushik Katakam
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("day")
    @Expose
    public Integer day;
    @SerializedName("mealPlanId")
    @Expose
    public Integer mealPlanId;
    @SerializedName("slot")
    @Expose
    public Integer slot;
    @SerializedName("position")
    @Expose
    public Integer position;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("value")
    @Expose
    public String value;

}