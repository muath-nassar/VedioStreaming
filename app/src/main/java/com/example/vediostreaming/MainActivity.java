package com.example.vediostreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vediostreaming.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;// view binding code
    private final String url1 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    private final String url2 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
    private final String url3 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";
    private final String url4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    private static String currentUrl = "";
    //exo player properties
    private static boolean playWhenReady = true;
    private static int currentScreen = 0;
    private static long playbackPosition = 0;
    private PlayerView playerView;
    private static SimpleExoPlayer player;
    //buttons
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //end of view binding
        playerView = binding.video;
        btn1 = binding.btn1;
        btn2 = binding.btn2;
        btn3 = binding.btn3;
        btn4 = binding.btn4;
        //end of buttons
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null){
                    release();
                }
                initializeVideo(url1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null){
                    release();
                }
                initializeVideo(url2);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null){
                    release();
                }
                initializeVideo(url3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null){
                    release();
                }
                initializeVideo(url4);
            }
        });

    }
    private void initializeVideo(String url){
        DefaultDataSourceFactory dataFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"));
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataFactory).createMediaSource(MediaItem.fromUri(url));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(dataFactory);
        player = new SimpleExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build();
        player.addMediaSource(mediaSource);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentScreen);
        player.prepare();
        playerView.setPlayer(player);
        playerView.requestFocus();
        currentUrl = url;
    }
    private void release(){
        playWhenReady = player.getPlayWhenReady();
        currentScreen = player.getCurrentWindowIndex();
        playbackPosition =  player.getCurrentPosition();
        player.release();
        player = null;
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(player != null){
            release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player != null){
            release();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeVideo(currentUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeVideo(currentUrl);
    }
}