package edu.skku.cs.omokwithmvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class RankingModeSelectionActivity extends AppCompatActivity implements RankingContract.RankingView {

    private Switch modeSwitch;
    private View inflateView;
    private ListView listView;
    private ConstraintLayout emptyView;

    private HardListAdapter hardListAdapter;
    private EasyListAdapter easyListAdapter;
    private RankingPresenter presenter;

    public ArrayList<RankingUserModel> easyModeUsers_al;
    public ArrayList<RankingUserModel> hardModeUsers_al;

    class modeSwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                modeSwitch.setText("Hard Mode");
                if(hardModeUsers_al.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(hardListAdapter);
                }
            }
            else {
                modeSwitch.setText("Easy Mode");
                if(easyModeUsers_al.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(easyListAdapter);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_mode_selection);

        presenter = new RankingPresenter(this, new RankingModel());
        presenter.get_ranking_data();

        listView      = findViewById(R.id.ranking_list);
        emptyView     = findViewById(R.id.dummy);

        emptyView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);

        modeSwitch = findViewById(R.id.switch1);
        modeSwitch.setVisibility(View.INVISIBLE);
        modeSwitch.setOnCheckedChangeListener(new modeSwitchListener());
    }

    @Override
    public void setListandEmptyViews(ArrayList<RankingUserModel> h_al, ArrayList<RankingUserModel> e_al) {
        RankingModeSelectionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hardListAdapter = new HardListAdapter(h_al, getApplicationContext());
                easyListAdapter = new EasyListAdapter(e_al, getApplicationContext());

                hardModeUsers_al = h_al;
                easyModeUsers_al = e_al;

                modeSwitch.setVisibility(View.VISIBLE);

                if(e_al.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(easyListAdapter);
                }
            }
        });
    }
}