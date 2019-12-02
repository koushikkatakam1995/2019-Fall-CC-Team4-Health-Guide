package com.sawapps.baymaxhealthcare.Network.Remote;

import static com.sawapps.baymaxhealthcare.Utils.BASE_URL;


/**
 * Created by Koushik Katakam
 */

public class ApiUtils {

    private static Service currentService = RetrofitClient.getClient(BASE_URL).create(Service.class);

    public static Service getService() {
        return currentService;
    }

    public static void changeBaseUrl(String newUrl) {

        BASE_URL = newUrl;
        currentService = RetrofitClient.getClient(BASE_URL).create(Service.class);

    }
}