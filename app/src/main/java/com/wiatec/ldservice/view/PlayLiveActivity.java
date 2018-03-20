package com.wiatec.ldservice.view;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.utils.EmojiToast;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.px.common.utils.RegularUtil;
import com.px.common.utils.SPUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.databinding.ActivityPlayLiveBinding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.model.UserContentResolver;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * play
 */

public class PlayLiveActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private static final int MSG_NET_SPEED = 1;
    private static final int MSG_WS_COMMENT = 2;

    private ActivityPlayLiveBinding binding;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private boolean send = true;
    private boolean isJSLoaded = false;

    private boolean isNeedPaid = false;

    private String channelId = "";
    private String playUserId = "";
    private String title = "";
    private String message ="";
    private String playUrl ="";

    private WebSocketClient webSocketClient;
    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_live);
        surfaceHolder = binding.surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        isNeedPaid = getIntent().getBooleanExtra("isNeedPaid", false);
        channelId =  getIntent().getStringExtra("id");
        playUserId =  getIntent().getStringExtra("userId");
        title =  getIntent().getStringExtra("title");
        message =  getIntent().getStringExtra("message");
        playUrl = getIntent().getStringExtra("playUrl");

        if(!TextUtils.isEmpty(message)) {
            binding.tvMessage.setText(message);
            binding.tvMessage.setVisibility(View.VISIBLE);
        }
        binding.btSend.setOnClickListener(this);
        binding.switchDanMu.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isNeedPaid){
            SPUtil.put("already_preview" + playUserId + title, true);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EmojiToast.showLong("Preview over.  To continue watch, please click the channel and confirm to proceed with payment.", EmojiToast.EMOJI_SAD);
                            finish();
                        }
                    });
                }
            }, 60000);
        }

        if(binding.switchDanMu.isChecked()){
            binding.etMessage.requestFocus();
            binding.etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        sendComment();
                        return true;
                    }
                    return false;
                }
            });
            initWS();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(playUrl);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseMediaPlayer();
    }

    private void play(final String url) {
        Logger.d(url);
        sendNetSpeed();
        binding.pbPlay.setVisibility(View.VISIBLE);
        try {
            if(mediaPlayer == null){
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    binding.pbPlay.setVisibility(View.GONE);
                    binding.tvNetSpeed.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                        binding.pbPlay.setVisibility(View.VISIBLE);
                        binding.tvNetSpeed.setVisibility(View.VISIBLE);
                    }
                    if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                        binding.pbPlay.setVisibility(View.GONE);
                        binding.tvNetSpeed.setVisibility(View.GONE);
                    }
                    return false;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    binding.tvNetSpeed.setVisibility(View.VISIBLE);
                    play(playUrl);
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play(playUrl);
                }
            });
        } catch (IOException e) {
            Logger.d(e.getMessage());
        }
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        closeWS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
        closeWS();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        send = false;
        closeWS();
    }

    private void sendNetSpeed(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (send){
                    int s1 = NetUtil.getNetSpeedBytes();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int s2 = NetUtil.getNetSpeedBytes();
                    float f  = (s2-s1)/2/1024F;
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    String s = decimalFormat.format(f);
                    Message m = handler.obtainMessage();
                    m.what = MSG_NET_SPEED;
                    m.obj = s;
                    handler.sendMessage(m);
                }
            }
        }).start();
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_NET_SPEED:
                    String s = msg.obj.toString();
                    binding.tvNetSpeed.setText(s + "kbs");
                    break;
                case MSG_WS_COMMENT:
                    String comment = msg.obj.toString();
                    showComment(comment);
                    break;
            }
        }
    };

    private void initWS(){
        try {
            String watchUserId = UserContentResolver.get("userId");
            if(TextUtils.isEmpty(watchUserId)){ watchUserId = "0";}
            webSocketClient = new WebSocketClient(new URI(Constant.url.blive_ws_live + playUserId + "/" + watchUserId)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Logger.d("ws open");
                }

                @Override
                public void onMessage(String message) {
                    Logger.d("ws: " + message);
                    Message msg = handler.obtainMessage();
                    msg.what = MSG_WS_COMMENT;
                    msg.obj = message;
                    handler.sendMessage(msg);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Logger.d("ws close");
                }

                @Override
                public void onError(Exception ex) {
                    Logger.d(ex.getMessage());
                }
            };
            webSocketClient.connect();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    private void closeWS(){
        if(webSocketClient != null){
            webSocketClient.close();
        }
    }

    private void showComment(String comment){
        if(stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        if(comment.startsWith("blive group count:")){
            return;
        }
        stringBuilder.append("\r\n");
        stringBuilder.append(comment);
        binding.tvComment.setText(stringBuilder.toString());
        binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void sendComment(){
        String comment = binding.etMessage.getText().toString();
        if(TextUtils.isEmpty(comment)){
            return;
        }
        if(webSocketClient == null){
            return;
        }
        webSocketClient.send("1/" + playUserId + "/" + comment);
        binding.etMessage.setText("");
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btSend){
            sendComment();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.switchDanMu){
            if(isChecked){
                binding.etMessage.setVisibility(View.VISIBLE);
                binding.btSend.setVisibility(View.VISIBLE);
                binding.scrollView.setVisibility(View.VISIBLE);
                initWS();
            }else{
                binding.etMessage.setVisibility(View.GONE);
                binding.btSend.setVisibility(View.GONE);
                binding.scrollView.setVisibility(View.GONE);
                closeWS();
            }
        }
    }
}
