package edu.skku.cs.omokwithmvp;

public class MenuPresenter implements MenuContract.MenuPresenter, MenuContract.MenuModel.OnApiListener{
    private MenuContract.MenuView view;
    private MenuContract.MenuModel model;

    public MenuPresenter(MenuContract.MenuView view, MenuContract.MenuModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSuccess() {
        if(view != null) {
            view.success_method();
        }
    }

    @Override
    public void getIntentExtra() {
        model.getIntentExtraFromView(this);
    }
}
