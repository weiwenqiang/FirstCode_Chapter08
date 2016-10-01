package com.example.chapter08.sms;

import com.example.chapter08.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestSMS extends Activity {
	private TextView sender, content;

	private IntentFilter receiveFilter;
	private MessageReceiver messageReceiver;
	
	private EditText to, msgInput;
	private Button send;
	
	//监控短信发送的状态
	private IntentFilter sendFilter;
	private SendStatusReceiver sendStatusReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b2_sms);
		sender = (TextView) findViewById(R.id.sms_sender);
		content = (TextView) findViewById(R.id.sms_content);

		//动态注册广播
		receiveFilter = new IntentFilter();
		receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		
		//提高优先级
		receiveFilter.setPriority(100);
		
		messageReceiver = new MessageReceiver();
		registerReceiver(messageReceiver, receiveFilter);
		
		//发送短信逻辑代码
		to = (EditText) findViewById(R.id.sms_to);
		msgInput = (EditText) findViewById(R.id.sms_input);
		send = (Button) findViewById(R.id.sms_send);
		
		//发送短信的状态监控
		sendFilter = new IntentFilter();
		sendFilter.addAction("SENT_SMS_ACTION");
		sendStatusReceiver = new SendStatusReceiver();
		registerReceiver(sendStatusReceiver, sendFilter);
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SmsManager smsManager = SmsManager.getDefault();
				//发送短信的状态监控逻辑
				Intent sentIntent = new Intent("SENT_SMS_ACTION");
				PendingIntent pi = PendingIntent.getBroadcast(TestSMS.this, 0, sentIntent, 0);
				
				smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
				
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(messageReceiver);
		
		unregisterReceiver(sendStatusReceiver);
	}
	/*
	 * 短信接收的内部类
	 */
	class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			// 提取短信消息
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			// 获取短信发送方号码
			String address = messages[0].getOriginatingAddress();
			String fullMessage = "";
			for (SmsMessage message : messages) {
				// 获取短信内容
				fullMessage += message.getMessageBody();
			}
			sender.setText(address);
			content.setText(fullMessage);
			
			//终止广播的继续传递
			abortBroadcast();
		}
	}
	/*
	 * 
	 */
	class SendStatusReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(getResultCode() == RESULT_OK){
				Toast.makeText(context, "Send succeeded(发送成功)", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "Send succeeded(发送成功)", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
