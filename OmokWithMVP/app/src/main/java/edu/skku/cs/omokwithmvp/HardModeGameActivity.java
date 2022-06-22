package edu.skku.cs.omokwithmvp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nordan.dialog.NordanAlertDialog;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HardModeGameActivity extends AppCompatActivity {

    private GridView gridView;
    public int[][]             omok_pos;
    public ArrayList<Position> sharding;
    public ArrayList<Pair>     victory_pos;
    public GridAdapter         gridAdapter;
    public GestureView gestureView;
    public String              passStr;
    public OkHttpClient client;
    public TextView userScoreTextView;

    public int userScore;
    public int scoreCopy;

    public String first_hardscore;

    public String name;
    public String easyscore;
    public String hardscore;
    public String passwd;

    public boolean checkVictory(int current_player) {
        int flag = 0;
        for(int i=0; i<20; i++) {
            for(int j=0; j<20; j++) {
                try {
                    if((omok_pos[i][j] == current_player) && (omok_pos[i+1][j] == current_player) && (omok_pos[i+2][j] == current_player) && (omok_pos[i+3][j] == current_player) && (omok_pos[i+4][j] == current_player)) {
                        flag = 1;
                        System.out.println("COND1");
                        victory_pos.add(new Pair(i, j));
                        victory_pos.add(new Pair(i+1, j));
                        victory_pos.add(new Pair(i+2, j));
                        victory_pos.add(new Pair(i+3, j));
                        victory_pos.add(new Pair(i+4, j));
                        break;
                    }
                }
                catch (Exception e) {

                }
                try {
                    if((omok_pos[i][j] == current_player) && (omok_pos[i][j+1] == current_player) && (omok_pos[i][j+2] == current_player) && (omok_pos[i][j+3] == current_player) && (omok_pos[i][j+4] == current_player)) {
                        flag = 1;
                        System.out.println("COND2");
                        victory_pos.add(new Pair(i, j));
                        victory_pos.add(new Pair(i, j+1));
                        victory_pos.add(new Pair(i, j+2));
                        victory_pos.add(new Pair(i, j+3));
                        victory_pos.add(new Pair(i, j+4));
                        break;
                    }
                }
                catch (Exception e) {

                }
                try {
                    if((j >= 4) && (omok_pos[i][j] == current_player) && (omok_pos[i+1][j-1] == current_player) && (omok_pos[i+2][j-2] == current_player) && (omok_pos[i+3][j-3] == current_player) && (omok_pos[i+4][j-4] == current_player)) {
                        flag = 1;
                        System.out.println("COND3");
                        victory_pos.add(new Pair(i, j));
                        victory_pos.add(new Pair(i+1, j-1));
                        victory_pos.add(new Pair(i+2, j-2));
                        victory_pos.add(new Pair(i+3, j-3));
                        victory_pos.add(new Pair(i+4, j-4));
                        break;
                    }
                }
                catch (Exception e) {

                }
                try {
                    if((omok_pos[i][j] == current_player) && (omok_pos[i+1][j+1] == current_player) && (omok_pos[i+2][j+2] == current_player) && (omok_pos[i+3][j+3] == current_player) && (omok_pos[i+4][j+4] == current_player)) {
                        flag = 1;
                        System.out.println("COND4");
                        victory_pos.add(new Pair(i, j));
                        victory_pos.add(new Pair(i+1, j+1));
                        victory_pos.add(new Pair(i+2, j+2));
                        victory_pos.add(new Pair(i+3, j+3));
                        victory_pos.add(new Pair(i+4, j+4));
                        break;
                    }
                }
                catch (Exception e) {

                }
            }
            if(flag >= 1) {
                break;
            }
        }
        if(flag > 0) {
            return true;
        }
        return false;
    }

    public void updateScoreinDatabase() {
        HardScoreUpdateRequestDataModel hardScoreUpdateRequestDataModel = new HardScoreUpdateRequestDataModel();
        hardScoreUpdateRequestDataModel.setScore(String.valueOf(userScore));
        hardScoreUpdateRequestDataModel.setName(name);
        hardScoreUpdateRequestDataModel.setPasswd(passwd);

        Gson gson = new Gson();
        String json = gson.toJson(hardScoreUpdateRequestDataModel, HardScoreUpdateRequestDataModel.class);

        HttpUrl.Builder urlBuilder =
                HttpUrl.parse("https://f3dzusuokg.execute-api.ap-northeast-2.amazonaws.com/dev/update/hardscore").newBuilder();
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
                final HardScoreUpdateResponseDataModel hardScoreUpdateResponseDataModel = gson.fromJson(myResponse, HardScoreUpdateResponseDataModel.class);

                if(hardScoreUpdateResponseDataModel.getSuccess()) {
                    System.out.println("Update Done");
                }
                else {
                    return;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HardModeGameActivity.this, MenuActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("easyscore", easyscore);
        if(Integer.parseInt(first_hardscore) < scoreCopy) {
            intent.putExtra("hardscore", String.valueOf(scoreCopy));
        }
        else {
            intent.putExtra("hardscore", first_hardscore);
        }
        intent.putExtra("passwd", passwd);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_mode_game);

        Intent intent = getIntent();
        name      = intent.getStringExtra("name");
        easyscore = intent.getStringExtra("easyscore");
        hardscore = intent.getStringExtra("hardscore");
        passwd    = intent.getStringExtra("passwd");

        first_hardscore = hardscore;

        gridView          = findViewById(R.id.grid);
        gestureView       = findViewById(R.id.gest);
        userScoreTextView = findViewById(R.id.textView13);
        gestureView.getController()
                .getSettings()
                .setFitMethod(Settings.Fit.INSIDE)
                .setMaxZoom(3f)
                .setDoubleTapEnabled(false);

        omok_pos    = new int[20][20];
        passStr     = new String();
        sharding    = new ArrayList<>();
        victory_pos = new ArrayList<>();
        client      = new OkHttpClient();
        userScore   = 0;

        for(int i=0; i<400; i++) {
            sharding.add(new Position(i / 20, i % 20, -1, true));
            omok_pos[i/20][i%20] = 0;
        }

        gridAdapter = new GridAdapter(sharding, getApplicationContext());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(sharding.get(i).getIsEmpty() == true) {
                    omok_pos[sharding.get(i).getY()][sharding.get(i).getX()] = 1;
                    sharding.set(i, new Position(sharding.get(i).getY(), sharding.get(i).getX(), 1, false));
                    userScore += 1;
                    userScoreTextView.setText("Your Current Score : " + String.valueOf(userScore));

                    gridAdapter.notifyDataSetChanged();

                    if(checkVictory(1)) {
                        System.out.println("player 1 won");

                        for(int k=0; k<5; k++) {
                            int vY = victory_pos.get(k).getY();
                            int vX = victory_pos.get(k).getX();
                            sharding.set(vY * 20 + vX, new Position(vY, vX, 3, false));
                        }
                        gridAdapter.notifyDataSetChanged();
                        victory_pos.clear();
                        updateScoreinDatabase();
                        new NordanAlertDialog.Builder(HardModeGameActivity.this)
                                .isCancellable(false)
                                .setTitle("You VICTORY!")
                                .setMessage("You Win! Score is " + String.valueOf(userScore) + "\n재도전을 원하시나요?")
                                .setPositiveBtnText("안할래요")
                                .setNegativeBtnText("다시하기")
                                .onPositiveClicked(() -> {
                                    Intent intent = new Intent(HardModeGameActivity.this, MenuActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("easyscore", easyscore);
                                    if(Integer.parseInt(first_hardscore) < userScore) {
                                        intent.putExtra("hardscore", String.valueOf(userScore));
                                    }
                                    else {
                                        intent.putExtra("hardscore", first_hardscore);
                                    }
                                    intent.putExtra("passwd", passwd);
                                    startActivity(intent);
                                })
                                .onNegativeClicked(() -> {
                                    sharding.clear();
                                    scoreCopy = userScore;
                                    userScore = 0;
                                    userScoreTextView.setText("Your Current Score : 0");
                                    for(int p=0; p<400; p++) {
                                        sharding.add(new Position(p / 20, p % 20, -1, true));
                                        omok_pos[p/20][p%20] = 0;
                                    }
                                    gridAdapter.notifyDataSetChanged();
                                })
                                .build().show();
                    }

                    gridView.setClickable(false);
                    for(int k=0; k<400; k++) {
                        passStr += String.valueOf(omok_pos[k/20][k%20]);
                        passStr += ',';
                    }

                    HttpUrl.Builder urlBuilder =
                            HttpUrl.parse("https://18shshin-w9j2x0joh1c1c0j1.socketxp.com/get/prediction").newBuilder();
                    urlBuilder.addQueryParameter("req", passStr);
                    urlBuilder.addQueryParameter("type", "hard");
                    passStr = new String();

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
                            final Gson gson = new GsonBuilder().create();
                            final AIPos aiPos = gson.fromJson(myResponse, AIPos.class);
                            HardModeGameActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Integer ai_x = Integer.parseInt(aiPos.getO_y());
                                    Integer ai_y = Integer.parseInt(aiPos.getO_x());
                                    omok_pos[ai_y][ai_x] = -1;
                                    sharding.set(ai_y * 20 + ai_x, new Position(ai_y, ai_x, 2, false));
                                    gridAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "AI가 " + String.valueOf(ai_x) + ", " + String.valueOf(ai_y) + "에 놓았습니다.", Toast.LENGTH_SHORT).show();
                                    gridView.setClickable(true);

                                    if(checkVictory(-1)) {
                                        System.out.println("player 2 won");
                                        for(int k=0; k<5; k++) {
                                            Integer vY = victory_pos.get(k).getY();
                                            Integer vX = victory_pos.get(k).getX();
                                            sharding.set(vY * 20 + vX, new Position(vY, vX, 4, false));
                                        }
                                        gridAdapter.notifyDataSetChanged();
                                        victory_pos.clear();
                                        updateScoreinDatabase();

                                        new NordanAlertDialog.Builder(HardModeGameActivity.this)
                                                .isCancellable(false)
                                                .setTitle("AI VICTORY!")
                                                .setMessage("You lose! Score is " + String.valueOf(userScore) + "\n재도전을 원하시나요?")
                                                .setPositiveBtnText("안할래요")
                                                .setNegativeBtnText("다시하기")
                                                .onPositiveClicked(() -> {
                                                    Intent intent = new Intent(HardModeGameActivity.this, MenuActivity.class);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("easyscore", easyscore);
                                                    if(Integer.parseInt(first_hardscore) < userScore) {
                                                        intent.putExtra("hardscore", String.valueOf(userScore));
                                                    }
                                                    else {
                                                        intent.putExtra("hardscore", first_hardscore);
                                                    }
                                                    intent.putExtra("passwd", passwd);
                                                    startActivity(intent);
                                                })
                                                .onNegativeClicked(() -> {
                                                    sharding.clear();
                                                    scoreCopy = userScore;
                                                    userScore = 0;
                                                    userScoreTextView.setText("Your Current Score : 0");
                                                    for(int i=0; i<400; i++) {
                                                        sharding.add(new Position(i / 20, i % 20, -1, true));
                                                        omok_pos[i/20][i%20] = 0;
                                                    }
                                                    gridAdapter.notifyDataSetChanged();
                                                })
                                                .build().show();
                                    }
                                }
                            });
                        }
                    });
                }
                else {

                }
            }
        });
    }
}