package com.uniware.driver.util;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by ayue on 2016/9/30.
 */

public class Mp3Player {
  private MediaPlayer mp;
  private String ss;

  public Mp3Player(Context context, int id) {
    mp = MediaPlayer.create(context, id);
  }
}
