package com.morcabtaxi.morcab;

import android.content.Context;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morcabtaxi.morcab.models.RawConfig;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 * Created by rupam.ghosh on 17/03/16.
 */
public class AppUtils {
  public static RawConfig getCabData(Context context){
    String s = null;
    try {
      InputStream is = context.getResources().openRawResource(R.raw.cabdata);
      s = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
    }catch (Exception ex){
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    return gson.fromJson(s, RawConfig.class);
  }
}
