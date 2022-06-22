package edu.skku.cs.omokwithmvp;

import java.util.ArrayList;

public class RankingPresenter implements RankingContract.RankingPresenter, RankingContract.RankingModel.OnChangedListener {

    private RankingContract.RankingModel model;
    private RankingContract.RankingView view;

    public RankingPresenter(RankingContract.RankingView view, RankingContract.RankingModel model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void get_ranking_data() {
        model.get_data(this);
    }

    @Override
    public void onSuccess(ArrayList<RankingUserModel> h_al, ArrayList<RankingUserModel> e_al) {
        if(view != null) view.setListandEmptyViews(h_al, e_al);
    }
}
