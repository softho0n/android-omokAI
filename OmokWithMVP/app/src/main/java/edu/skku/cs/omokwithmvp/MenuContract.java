package edu.skku.cs.omokwithmvp;

public class MenuContract {
    interface MenuView {
        void startExplainActivity();
        void startEasyModeGameActivity();
        void startHardModeGameActivity();
        void startRankingActivity();
        void success_method();
    }

    interface MenuModel {
        void getIntentExtraFromView(OnApiListener listener);
        String getName();
        String getPasswd();
        String getEasyscore();
        String getHardscore();

        interface OnApiListener {
            void onSuccess();
        }
    }

    interface MenuPresenter {
        void getIntentExtra();
    }

}
