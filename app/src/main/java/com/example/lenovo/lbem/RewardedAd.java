package com.example.lenovo.lbem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class RewardedAd extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mAd;
    Button adbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_ad);

        adbutton = (Button) findViewById(R.id.adbutton);

        mAd = MobileAds.getRewardedVideoAdInstance(RewardedAd.this);
        mAd.setRewardedVideoAdListener(RewardedAd.this);

        loadRewardedVideoAd();

        adbutton.setOnClickListener(new View.OnClickListener() {

        @Override
            public void onClick(View v) {
                if (mAd.isLoaded()) {
                    mAd.show();
                    Intent intent = new Intent(RewardedAd.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(RewardedAd.this, "Video is not yet loaded..Please try again",
                            Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadRewardedVideoAd() {

        mAd.loadAd("ca-app-pub-9197249229585858/8450631166", new AdRequest.Builder().
                addTestDevice("16B6134ABC43007286E647C2100CA7CE").build());

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(RewardedAd.this,"Loaded..Now Can Proceed.!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(RewardedAd.this,"Opened",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(RewardedAd.this,"Closed",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(RewardedAd.this,"Rewarded",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

}
