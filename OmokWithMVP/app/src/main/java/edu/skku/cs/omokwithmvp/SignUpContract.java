package edu.skku.cs.omokwithmvp;

public class SignUpContract {
    interface SignUpView {
        void success_method();
        void fail_method();
    }

    interface SignUpModel {
        void api_with_sign_up(String nickname, String name, String passwd, OnSignUpApiListener listener);
        interface OnSignUpApiListener {
            void onSuccess();
            void onFail();
        }
    }

    interface SignUpPresenter {
        void callSignUp(String nickname, String name, String passwd);
    }
}
