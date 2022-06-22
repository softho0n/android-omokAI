package edu.skku.cs.omokwithmvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.nordan.dialog.NordanAlertDialog;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.SignUpView {
    private EditText nicknameInput;
    private EditText nameInput;
    private EditText passwdInput;
    private Button signUpbtn;

    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nicknameInput = findViewById(R.id.editTextTextPassword3);
        nameInput     = findViewById(R.id.editTextTextPersonName2);
        passwdInput   = findViewById(R.id.editTextTextPassword2);

        presenter = new SignUpPresenter(this);
        signUpbtn     = findViewById(R.id.button3);
        signUpbtn.setOnClickListener(view -> {
            presenter.callSignUp(nicknameInput.getText().toString(), nameInput.getText().toString(), passwdInput.getText().toString());
        });
    }

    @Override
    public void success_method() {
        SignUpActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new NordanAlertDialog.Builder(SignUpActivity.this)
                        .isCancellable(false)
                        .setTitle("회원가입 성공")
                        .setMessage("로그인을 진행해주세요.")
                        .setPositiveBtnText("Okay")
                        .onPositiveClicked(() -> {
                            nicknameInput.setText("");
                            nameInput.setText("");
                            passwdInput.setText("");
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        })
                        .build().show();
            }
        });
    }

    @Override
    public void fail_method() {
        SignUpActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new NordanAlertDialog.Builder(SignUpActivity.this)
                        .isCancellable(false)
                        .setTitle("회원가입 실패")
                        .setMessage("아이디 또는 닉네임이 중복됩니다.")
                        .setPositiveBtnText("Okay")
                        .onPositiveClicked(() -> {
                            nicknameInput.setText("");
                            nameInput.setText("");
                            passwdInput.setText("");
                        })
                        .build().show();
            }
        });
    }
}