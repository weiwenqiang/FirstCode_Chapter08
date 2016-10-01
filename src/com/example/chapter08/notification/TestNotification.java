package com.example.chapter08.notification;

import java.io.File;

import com.example.chapter08.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TestNotification extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b1_notification);
		findViewById(R.id.b1_send_notice).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b1_send_notice:
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.ic_launcher, "This is ticker text(这是通知文本)", System.currentTimeMillis());
			//通知时播放音乐
			Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
			notification.sound = soundUri;
			//通知时震动,还需要声明权限
			long[] vibrates = {0, 1000, 1000, 1000};
			notification.vibrate = vibrates;
			//控制LED灯
			notification.ledARGB = Color.GREEN;
			notification.ledOnMS =1000;
			notification.ledOffMS=1000;
			notification.flags = Notification.FLAG_SHOW_LIGHTS;
			//直接使用系统默认效果
//			notification.defaults = Notification.DEFAULT_ALL;
			//通知页面跳转
			Intent intent = new Intent(TestNotification.this, NotificationActivity.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			
			notification.setLatestEventInfo(this, "This is content title(内容标题)", "This is content text(内容文本)", pi);
			manager.notify(1, notification);
			break;

		default:
			break;
		}
	}

}
