package com.example.artg.mtn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class checkrtp extends requesttopay{
    private static gettoken gt;
    private static requesttopay rtp;

    public static JSONObject checkpaymnt() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay/"+ rtp.rtp())
                .method("GET", null)
                .addHeader("Ocp-Apim-Subscription-Key", "93eab82d106d40c28c225bbef6e40426")
                .addHeader("X-Target-Environment", "sandbox")
                .addHeader("Authorization", "Bearer "+ gt.thetoken())
                .build();
        Response response = client.newCall(request).execute();

        //////////////
        String resp3 = response.body().string();
        JSONObject object3 = new JSONObject(resp3);
        //String token = object.getString("access_token");
        //String token_type = object.getString("token_type");
        //String expire_in = object.getString("expires_in");

        String status2 = String.valueOf(response.code());
        //System.out.println("---Token is----" +token +"-------");
        //System.out.println("---Token type:----" +token_type +"-------");
        //System.out.println("---Expires in:----" +expire_in +"-------");
        System.out.println("---Status 2----" +status2 +"-------");
        System.out.println("---object----" +object3 +"-------");

        //System.out.println(response);
        return object3;

    }
}

