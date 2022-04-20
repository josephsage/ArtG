package com.example.artg.mtn;

import com.example.artg.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class gettoken extends Checkout {

    public static String thetoken() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/token/")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", "93eab82d106d40c28c225bbef6e40426")
                .addHeader("Authorization", "Basic ZGM5N2QwMzMtNTlkNC00ZDc0LTllNTQtZGQ5YjY0N2E3MGNhOjA0Y2EyY2U5OWNkZjQxZDM5OTk1YjJhNzk3OWQ4YjY2")
                .build();
        Response response = client.newCall(request).execute();

        //////////////
        String resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        String token = object.getString("access_token");
        String token_type = object.getString("token_type");
        String expire_in = object.getString("expires_in");

        String status = String.valueOf(response.code());
        System.out.println("---Token is----" +token +"-------");
        System.out.println("---Token type:----" +token_type +"-------");
        System.out.println("---Expires in:----" +expire_in +"-------");
        System.out.println("---Status----" +status +"-------");

        System.out.println(response);

        return token;
    }
}