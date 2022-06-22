package edu.skku.cs.omokwithmvp;

import java.util.ArrayList;

public class RankingContract {
    interface RankingView {
        void setListandEmptyViews(ArrayList<RankingUserModel> h_al, ArrayList<RankingUserModel> e_al);
    }
    interface RankingModel {
        void get_data(OnChangedListener listener);

        interface OnChangedListener {
            void onSuccess(ArrayList<RankingUserModel> h_al, ArrayList<RankingUserModel> e_al);
        }
    }

    interface RankingPresenter {
        void get_ranking_data();
    }
}
