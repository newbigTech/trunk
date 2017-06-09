package com.uniware.driver.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import javax.inject.Inject;

/**
 * Created by jian on 15/12/16.
 */
public class XFSpeech {
  // 语音合成对象
  private SpeechSynthesizer mTts;
  private Toast mToast;
  private Complete complete;
  private boolean flag=false;
  public interface Complete {
    void complete();
  }

  public void setCompleteListener(Complete complete){
    this.complete = complete;
  }

  @Inject public XFSpeech(Context context) {
    // 初始化合成对象
    mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
    setParam();
    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
  }

  public void ttsSpeaking(String str) {
    try {
      int code = mTts.startSpeaking(str, mTtsListener);
      if (code != ErrorCode.SUCCESS) {
        showTip("语音合成失败,错误码: " + code);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void ttsSpeaking(String str,boolean flag) {
    this.flag=flag;
    try {
      int code = mTts.startSpeaking(str, mTtsListener);
      if (code != ErrorCode.SUCCESS) {
        showTip("语音合成失败,错误码: " + code);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 参数设置
   */
  private void setParam() {
    // 清空参数
    mTts.setParameter(SpeechConstant.PARAMS, null);
    // 根据合成引擎设置相应参数
    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
    // 设置在线合成发音人
    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
    //设置合成语速
    mTts.setParameter(SpeechConstant.SPEED, "75");
    //设置合成音调
    mTts.setParameter(SpeechConstant.PITCH, "50");
    //设置合成音量
    mTts.setParameter(SpeechConstant.VOLUME, "50");

    //设置播放器音频流类型
    mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
    // 设置播放合成音频打断音乐播放，默认为true
    mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

    // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
    // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
    mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
        Environment.getExternalStorageDirectory() + "/msc/tts.wav");
  }

  /**
   * 初始化监听。
   */
  private InitListener mTtsInitListener = new InitListener() {
    @Override public void onInit(int code) {
      //LogUtils.e(this, "InitListener init() code = " + code);
      if (code != ErrorCode.SUCCESS) {
        showTip("初始化失败,错误码：" + code);
      } else {
        //LogUtils.e(this, "InitListener SUCCESS");
        // 初始化成功，之后可以调用startSpeaking方法
        // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
        // 正确的做法是将onCreate中的startSpeaking调用移至这里
      }
    }
  };

  /**
   * 合成回调监听。
   */
  private SynthesizerListener mTtsListener = new SynthesizerListener() {

    @Override public void onSpeakBegin() {
      //showTip("开始播放");
    }

    @Override public void onSpeakPaused() {
      //showTip("暂停播放");
    }

    @Override public void onSpeakResumed() {
      //showTip("继续播放");
    }

    @Override public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
      // 合成进度
    }

    @Override public void onSpeakProgress(int percent, int beginPos, int endPos) {
      // 播放进度
    }

    @Override public void onCompleted(SpeechError error) {
      if (error != null) {
        showTip(error.getPlainDescription(true));
      }
      if (flag){
        complete.complete();
        flag=false;
      }

      //showTip("播放完成");
    }

    @Override public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
      // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
      // 若使用本地能力，会话id为null
      //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
      //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
      //		Log.d(TAG, "session id =" + sid);
      //	}
    }
  };

  private void showTip(final String str) {
    mToast.setText(str);
    mToast.show();
  }

  public void cancel() {
    mTts.stopSpeaking();
  }

  public void resume() {
    mTts.resumeSpeaking();
  }

  public void pause() {
    mTts.pauseSpeaking();
  }

  public void stop() {
    if (mTts.isSpeaking()) {
      mTts.stopSpeaking();
    }
  }

  public void destroy() {
    mTts.stopSpeaking();
    // 退出时释放连接
    mTts.destroy();
  }
}
