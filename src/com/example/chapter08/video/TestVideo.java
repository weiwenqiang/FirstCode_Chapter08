package com.example.chapter08.video;

import java.io.File;

import com.example.chapter08.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.VideoView;

public class TestVideo extends Activity implements OnClickListener {

	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b5_video);
		videoView = (VideoView) findViewById(R.id.b5_video_view);
		findViewById(R.id.b5_play).setOnClickListener(this);
		findViewById(R.id.b5_pause).setOnClickListener(this);
		findViewById(R.id.b5_replay).setOnClickListener(this);
		initVideoPath();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b5_play:
			if(!videoView.isPlaying()){
				videoView.start();
			}
			break;
		case R.id.b5_pause:
			if(videoView.isPlaying()){
				videoView.pause();
			}
			break;
		case R.id.b5_replay:
			if(videoView.isPlaying()){
				videoView.resume();
			}
			break;
		default:
			break;
		}
	}
	private void initVideoPath(){
		File file = new File(Environment.getExternalStorageDirectory(), "sms.3gp");
		videoView.setVideoPath(file.getPath());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(videoView != null){
			videoView.suspend();
		}
	}
}
