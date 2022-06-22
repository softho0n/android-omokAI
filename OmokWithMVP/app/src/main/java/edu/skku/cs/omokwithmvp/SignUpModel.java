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

public class SignUpModel implements SignUpContract.SignUpModel {
    private String name;
    private String passwd;
    private String nickname;
    private OkHttpClient client;
    private SignInResponseDataModel signInResponseDataModel_of_signUpModel;

    public SignInResponseDataModel getSignInResponseDataModel_of_signUpModel() {
        return signInResponseDataModel_of_signUpModel;
    }

    public void setSignInResponseDataModel_of_signUpModel(SignInResponseDataModel signInResponseDataModel_of_signUpModel) {
        this.signInResponseDataModel_of_signUpModel = signInResponseDataModel_of_signUpModel;
    }

    @Override
    public void api_with_sign_up(String nickname, String name, String passwd, OnSignUpApiListener listener) {
        client = new OkHttpClient();

        SignUpRequestDataModel signUpRequestDataModel = new SignUpRequestDataModel();
        signUpRequestDataModel.setName(name);
        signUpRequestDataModel.setNickname(nickname);
        signUpRequestDataModel.setPasswd(passwd);

        Gson gson = new Gson();
        String json = gson.toJson(signUpRequestDataModel, SignUpRequestDataModel.class);

        System.out.println(json);

        HttpUrl.Builder urlBuilder =
                HttpUrl.parse("https://f3dzusuokg.execute-api.ap-northeast-2.amazonaws.com/dev/post/signUp").newBuilder();
        String url = urlBuilder.build().toString();

        System.out.println(url);
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
                System.out.println(myResponse);
                final SignInResponseDataModel signInResponseDataModel = gson.fromJson(myResponse, SignInResponseDataModel.class);
                setSignInResponseDataModel_of_signUpModel(signInResponseDataModel);

                if(signInResponseDataModel.getSuccess()) {
                    listener.onSuccess();
                }
                else {
                    listener.onFail();
                }
            }
        });
    }
}
