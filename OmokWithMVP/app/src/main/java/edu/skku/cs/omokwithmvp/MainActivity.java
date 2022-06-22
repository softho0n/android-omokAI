package edu.skku.cs.omokwithmvp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.nordan.dialog.NordanAlertDialog;


public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private EditText idTextField;
    private EditText passwdField;
    private Button   signUpBtn;
    private Button   signInBtn;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idTextField = findViewById(R.id.editTextTextPersonName);
        passwdField = findViewById(R.id.editTextTextPassword);

        signUpBtn = findViewById(R.id.button2);
        signInBtn = findViewById(R.id.button);

        presenter = new MainPresenter(this);
        signUpBtn.setOnClickListener(view -> {
            startSignUpActivity();
        });

        signInBtn.setOnClickListener(view -> {
            if (idTextField.getText().toString().length() == 0 && passwdField.getText().toString().length() == 0) {
                fail_method();
            }
            else {
                presenter.callSignIn(idTextField.getText().toString(), passwdField.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void startSignUpActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void success_method(UserModel userModel) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("name", userModel.getName());
                intent.putExtra("passwd", userModel.getPasswd());
                intent.putExtra("easyscore", userModel.getEasyscore());
                intent.putExtra("hardscore", userModel.getHardscore());
                startActivity(intent);
            }
        });
    }

    @Override
    public void fail_method() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new NordanAlertDialog.Builder(MainActivity.this)
                        .isCancellable(false)
                        .setTitle("로그인 실패")
                        .setMessage("아이디/패스워드를 다시 확인하세요.")
                        .setPositiveBtnText("Okay")
                        .onPositiveClicked(() -> {
                            idTextField.setText("");
                            passwdField.setText("");
                        })
                        .build().show();
            }
        });
    }
}