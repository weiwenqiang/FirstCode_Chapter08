package com.example.chapter08;

import com.example.chapter08.music.TestMusic;
import com.example.chapter08.notification.TestNotification;
import com.example.chapter08.photograph.TestPhotograph;
import com.example.chapter08.sms.TestSMS;
import com.example.chapter08.video.TestVideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.c08_b1).setOnClickListener(this);
		findViewById(R.id.c08_b2).setOnClickListener(this);
		findViewById(R.id.c08_b3).setOnClickListener(this);
		findViewById(R.id.c08_b4).setOnClickListener(this);
		findViewById(R.id.c08_b5).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.c08_b1:
			startActivity(new Intent(MainActivity.this, TestNotification.class));
			break;
		case R.id.c08_b2:
			startActivity(new Intent(MainActivity.this, TestSMS.class));
			break;
		case R.id.c08_b3:
			startActivity(new Intent(MainActivity.this, TestPhotograph.class));
			break;
		case R.id.c08_b4:
			startActivity(new Intent(MainActivity.this, TestMusic.class));
			break;
		case R.id.c08_b5:
			startActivity(new Intent(MainActivity.this, TestVideo.class));
			break;
		default:
			break;
		}
	}
}
