package edu.skku.cs.omokwithmvp;

public class SignUpPresenter implements SignUpContract.SignUpPresenter, SignUpContract.SignUpModel.OnSignUpApiListener {

    private SignUpContract.SignUpView view;
    private SignUpContract.SignUpModel model;

    public SignUpPresenter(SignUpContract.SignUpView view) {
        this.view = view;
        this.model = new SignUpModel();
    }

    @Override
    public void onSuccess() {
        if(view != null) view.success_method();
    }

    @Override
    public void onFail() {
        if(view != null) view.fail_method();
    }

    @Override
    public void callSignUp(String nickname, String name, String passwd) {
        model.api_with_sign_up(nickname, name, passwd, this);
    }
}
