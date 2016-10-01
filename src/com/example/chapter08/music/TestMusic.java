package com.example.chapter08.music;

import java.io.File;
import java.io.IOException;

import com.example.chapter08.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TestMusic extends Activity implements OnClickListener {

	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b4_music);
		findViewById(R.id.b4_play).setOnClickListener(this);
		findViewById(R.id.b4_pause).setOnClickListener(this);
		findViewById(R.id.b4_stop).setOnClickListener(this);
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		try {
			File file = new File(Environment.getExternalStorageDirectory(),
					"Kalimba.mp3");
			// ָ����Ƶ�ļ���·��
			mediaPlayer.setDataSource(file.getPath());
			// ��MediaPlayer���뵽׼��״̬
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b4_play:
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();// ��ʼ����
			}
			break;
		case R.id.b4_pause:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();// ��ͣ����
			}
			break;
		case R.id.b4_stop:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.reset();// ֹͣ����
				initMediaPlayer();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

}
