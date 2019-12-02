package com.sawapps.baymaxhealthcare.Network.GetDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koushik Katakam
 */
public class Meal {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("readyInMinutes")
    @Expose
    public Integer readyInMinutes;
    @SerializedName("servings")
    @Expose
    public Integer servings;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("imageUrls")
    @Expose
    public List<String> imageUrls = new ArrayList<String>();

}
