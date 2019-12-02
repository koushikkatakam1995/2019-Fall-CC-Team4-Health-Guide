package com.sawapps.baymaxhealthcare.Network.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SaiTejaswi Koppuravuri
 */

public class ValueObject {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("imageType")
    @Expose
    public String imageType;
    @SerializedName("title")
    @Expose
    public String title;
}
