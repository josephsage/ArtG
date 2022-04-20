package com.example.artg.mtn;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class requesttopay extends gettoken {
    private static gettoken gt = new gettoken();
    public static String uniqueID = UUID.randomUUID().toString();

    public static String rtp() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"amount\": \"5.0\",\n  \"currency\": \"EUR\",\n  \"externalId\": \"6353636\",\n  \"payer\": {\n    \"partyIdType\": \"MSISDN\",\n    \"partyId\": \"0248888736\"\n  },\n  \"payerMessage\": \"Pay for product a\",\n  \"payeeNote\": \"payer note\"\n}");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay")
                .method("POST", body)
                .addHeader("X-Reference-Id", uniqueID)
                .addHeader("X-Target-Environment", "sandbox")
                .addHeader("Ocp-Apim-Subscription-Key", "93eab82d106d40c28c225bbef6e40426")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+ gt.thetoken())
                .build();
        Response response = client.newCall(request).execute();

        //////////////
        String resp2 = response.body().string();
        JSONObject object = new JSONObject(resp2);
        //String token = object.getString("access_token");
        //String token_type = object.getString("token_type");
        //String expire_in = object.getString("expires_in");

        String status2 = String.valueOf(response.code());
        //System.out.println("---Token is----" +token +"-------");
        //System.out.println("---Token type:----" +token_type +"-------");
        //System.out.println("---Expires in:----" +expire_in +"-------");
        System.out.println("---Status 2----" +status2 +"-------");
        System.out.println("---UUID----" +uniqueID +"-------");

        System.out.println(response);

        return uniqueID;
    }
}

