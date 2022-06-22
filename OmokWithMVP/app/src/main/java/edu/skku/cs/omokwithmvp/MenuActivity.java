package edu.skku.cs.omokwithmvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements MenuContract.MenuView {

    private ImageButton hardModeBtn;
    private ImageButton easyModeBtn;
    private ImageButton rankingBtn;
    private ImageButton explainBtn;

    private TextView greetingText;
    private TextView easyscoreText;
    private TextView hardscoreText;

    private MenuPresenter presenter;

    private String name;
    private String easyscore;
    private String hardscore;
    private String passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        easyscore = intent.getStringExtra("easyscore");
        hardscore = intent.getStringExtra("hardscore");
        passwd    = intent.getStringExtra("passwd");

        greetingText  = findViewById(R.id.textView3);
        easyscoreText = findViewById(R.id.textView4);
        hardscoreText = findViewById(R.id.textView5);

        presenter = new MenuPresenter(this, new MenuModel(name, easyscore, hardscore, passwd));
        presenter.getIntentExtra();

        rankingBtn  = findViewById(R.id.imageButton5);
        rankingBtn.setOnClickListener(view -> {
            startRankingActivity();
        });

        hardModeBtn = findViewById(R.id.imageButton2);
        hardModeBtn.setOnClickListener(view -> {
            startHardModeGameActivity();
        });

        easyModeBtn = findViewById(R.id.imageButton3);
        easyModeBtn.setOnClickListener(view -> {
            startEasyModeGameActivity();
        });

        explainBtn = findViewById(R.id.imageButton4);
        explainBtn.setOnClickListener(view -> {
            startExplainActivity();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void startExplainActivity() {
        Intent gameIntent = new Intent(MenuActivity.this, ExplainActivity.class);
        startActivity(gameIntent);
    }

    @Override
    public void startEasyModeGameActivity() {
        Intent gameIntent = new Intent(MenuActivity.this, EasyModeGameActivity.class);
        gameIntent.putExtra("name", name);
        gameIntent.putExtra("easyscore", easyscore);
        gameIntent.putExtra("hardscore", hardscore);
        gameIntent.putExtra("passwd", passwd);
        startActivity(gameIntent);
    }

    @Override
    public void startHardModeGameActivity() {
        Intent gameIntent = new Intent(MenuActivity.this, HardModeGameActivity.class);
        gameIntent.putExtra("name", name);
        gameIntent.putExtra("easyscore", easyscore);
        gameIntent.putExtra("hardscore", hardscore);
        gameIntent.putExtra("passwd", passwd);
        startActivity(gameIntent);
    }

    @Override
    public void startRankingActivity() {
        Intent rankingIntent = new Intent(MenuActivity.this, RankingModeSelectionActivity.class);
        startActivity(rankingIntent);
    }

    @Override
    public void success_method() {
        greetingText.setText("Hello! " + name);
        easyscoreText.setText("Your Best Score in Easy Mode is " + easyscore);
        hardscoreText.setText("Your Best Score in Hard Mode is " + hardscore);
    }
}