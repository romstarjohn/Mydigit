package com.project.android.finanzm.utility;

import java.text.DateFormat;
import java.util.Date;
import java.util.Set;

import okhttp3.Headers;
import retrofit2.Response;

public class NetworkUtil {

    public static String onServerResponseError(Response response) {

        if (response.code() == 403) {
            //Declare a Header Object
            Headers headers = response.headers();
            Set<String> headerNames = headers.names();
            long rateLimitReset = 0;

            for (String headerName: headerNames
                 ) {
                String headerValue = headers.get(headerName);
                if(headerValue == null){

                    continue;
                }

                if (headerName.equals("X-RateLimit-Reset")){
                    rateLimitReset = Long.valueOf(headers.get(headerName));

                    break;
                }
            }

            if(rateLimitReset != 0){
                Date resetDate = new Date(rateLimitReset * 1000);
                String resetTimeText = DateFormat.getTimeInstance().format(resetDate);
                return "Try again after some time " + resetTimeText;

            }
        }

        return response.message();
    }
}
