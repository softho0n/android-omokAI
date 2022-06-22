package edu.skku.cs.omokwithmvp;

public class MainPresenter implements MainContract.MainPresenter, MainContract.MainModel.OnApiListener {

    private MainContract.MainView view;
    private MainContract.MainModel model;

    public MainPresenter(MainContract.MainView view) {
        this.view = view;
        this.model = new MainModel();
    }

    @Override
    public void callSignIn(String name, String passwd) {
        model.api_with_sign_in(name, passwd, this);
    }

    @Override
    public void onSuccess() {
        if(view != null) view.success_method(model.getUserModel_of_mainModel());
    }

    @Override
    public void onFail() {
        if(view != null) view.fail_method();
    }
}
