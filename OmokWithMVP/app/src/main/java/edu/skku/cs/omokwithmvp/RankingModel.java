package edu.skku.cs.omokwithmvp;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RankingModel implements RankingContract.RankingModel {
    private List<RankingUserModel> easyModeUsers_l;
    private List<RankingUserModel> hardModeUsers_l;

    private ArrayList<RankingUserModel> easyModeUsers_al;
    private ArrayList<RankingUserModel> hardModeUsers_al;
    private OkHttpClient client;

    public RankingModel() {
        this.easyModeUsers_al = new ArrayList<>();
        this.hardModeUsers_al = new ArrayList<>();
    }

    @Override
    public void get_data(OnChangedListener listener) {
        client = new OkHttpClient();
        HttpUrl.Builder urlBuilder =
                HttpUrl.parse("https://f3dzusuokg.execute-api.ap-northeast-2.amazonaws.com/dev/get/allUsers").newBuilder();
        String url = urlBuilder.build().toString();
        Request req = new Request.Builder()
                .url(url).build();


        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new GsonBuilder().create();
                UserTest userTest = gson.fromJson(myResponse, UserTest.class);

                easyModeUsers_l  = Arrays.asList(userTest.getEasy());
                easyModeUsers_al.addAll(easyModeUsers_l);

                hardModeUsers_l  = Arrays.asList(userTest.getHard());
                hardModeUsers_al.addAll(hardModeUsers_l);

                listener.onSuccess(hardModeUsers_al, easyModeUsers_al);
            }
        });
    }
}
