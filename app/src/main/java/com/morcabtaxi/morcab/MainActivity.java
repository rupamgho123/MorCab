package com.morcabtaxi.morcab;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initUI();
    initAdMob();
  }

  private void initUI(){
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.base_frame,new CabListFragment(),AppConstants.CAB_LIST_FRAGMENT_TAG);
    fragmentTransaction.commitAllowingStateLoss();
  }

  private void initAdMob(){
    AdView mAdView = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder()
        .setIsDesignedForFamilies(true)
        .build();
    mAdView.loadAd(adRequest);
  }
}
