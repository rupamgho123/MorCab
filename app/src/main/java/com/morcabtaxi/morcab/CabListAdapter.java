package com.morcabtaxi.morcab;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by rupam.ghosh on 17/03/16.
 */
public class CabListAdapter extends RecyclerView.Adapter<CabListAdapter.ViewHolder> {

  private List<CabData> cabs;
  private WeakReference<AppCompatActivity> activityWeakReference;

  public CabListAdapter(Activity activity){
    cabs = AppUtils.getCabData(activity).getCabData();
    activityWeakReference = new WeakReference(activity);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cab_list_item,parent,false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    CabData cabData = cabs.get(position);
    holder.companyName.setText(cabData.getName());
    holder.callButton.setVisibility(cabData.getPhoneNumber() == null ? View.GONE : View.VISIBLE);
    holder.goToAppButton.setVisibility(cabData.getAppLink() == null ? View.GONE : View.VISIBLE);
    holder.callButton.setOnClickListener(new CabClickListener(cabData));
    holder.goToAppButton.setOnClickListener(new CabClickListener(cabData));
  }

  @Override public int getItemCount() {
    return cabs.size();
  }

  private class CabClickListener implements View.OnClickListener{
    private CabData cabData;
    public CabClickListener(CabData cabData){
      this.cabData = cabData;
    }

    @Override public void onClick(View v) {
      switch (v.getId()){
        case R.id.go_to_app_button:
          goToApp(cabData.getAppLink());
          break;
        case R.id.call_button:
          launchDialer(cabData.getPhoneNumber());
          break;
      }
    }
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView companyName;
    public Button callButton;
    public Button goToAppButton;

    public ViewHolder(View v) {
      super(v);
      companyName = (TextView) v.findViewById(R.id.cab_company_name);
      callButton = (Button) v.findViewById(R.id.call_button);
      goToAppButton = (Button) v.findViewById(R.id.go_to_app_button);
    }
  }

  private void goToApp(String appLink){
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appLink));
    AppCompatActivity activity = activityWeakReference.get();
    if(activity != null) {
      try {
        activity.startActivity(intent);
      }catch (ActivityNotFoundException ex){
        ex.printStackTrace();
      }
    }
  }

  private void launchDialer(String phoneNumber){
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
    AppCompatActivity activity = activityWeakReference.get();
    if(activity != null) {
      try {
        activity.startActivity(intent);
      }catch (ActivityNotFoundException ex){
        ex.printStackTrace();
      }
    }
  }
}
