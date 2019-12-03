package com.sawapps.baymaxhealthcare.Network.Responses.DoctorSearchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Koushik Katakam
 */
public class Meta {

    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("data_type")
    @Expose
    public String dataType;
    @SerializedName("item_type")
    @Expose
    public String itemType;
    @SerializedName("limit")
    @Expose
    public Integer limit;
    @SerializedName("skip")
    @Expose
    public Integer skip;
    @SerializedName("total")
    @Expose
    public Integer total;

}
