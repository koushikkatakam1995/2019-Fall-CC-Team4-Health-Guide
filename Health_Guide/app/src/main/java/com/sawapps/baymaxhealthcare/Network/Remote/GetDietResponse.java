package com.sawapps.baymaxhealthcare.Network.Remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sawapps.baymaxhealthcare.Network.Responses.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koushik Katakam
 */

public class GetDietResponse {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("publishAsPublic")
    @Expose
    public Boolean publishAsPublic;
    @SerializedName("items")
    @Expose
    public List<Item> items = new ArrayList<Item>();

}