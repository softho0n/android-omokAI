package edu.skku.cs.omokwithmvp;

public class MainContract {

    interface MainView {
        void startSignUpActivity();

        void success_method(UserModel userModel_of_mainModel);
        void fail_method();
    }

    interface MainModel {
        void api_with_sign_in(String name, String passwd, OnApiListener listener);
        UserModel getUserModel_of_mainModel();
        interface OnApiListener {
            void onSuccess();
            void onFail();
        }
    }

    interface MainPresenter {
        void callSignIn(String name, String passwd);
    }
}
