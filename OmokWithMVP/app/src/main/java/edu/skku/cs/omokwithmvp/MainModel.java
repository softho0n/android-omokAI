package edu.skku.cs.omokwithmvp;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.nordan.dialog.NordanAlertDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainModel implements MainContract.MainModel {
    private String name;
    private String passwd;
    private OkHttpClient client;
    private UserModel userModel_of_mainModel;

    public void setUserModel_of_mainModel(UserModel userModel_of_mainModel) {
        this.userModel_of_mainModel = userModel_of_mainModel;
    }

    @Override
    public UserModel getUserModel_of_mainModel() {
        return userModel_of_mainModel;
    }

    @Override
    public void api_with_sign_in(String name, String passwd, OnApiListener listener) {
        client = new OkHttpClient();
        SignInRequestDataModel signInRequestDataModel = new SignInRequestDataModel();
        signInRequestDataModel.setName(name);
        signInRequestDataModel.setPasswd(passwd);

        Gson gson = new Gson();
        String json = gson.toJson(signInRequestDataModel, SignInRequestDataModel.class);

        HttpUrl.Builder urlBuilder =
                HttpUrl.parse("https://f3dzusuokg.execute-api.ap-northeast-2.amazonaws.com/dev/login").newBuilder();
        String url = urlBuilder.build().toString();
        Request req = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                final UserModel userModel = gson.fromJson(myResponse, UserModel.class);
                setUserModel_of_mainModel(userModel);

                if (userModel.getName() != null) {
                    listener.onSuccess();
                } else {
                    listener.onFail();
                }
            }
        });
    }
}
