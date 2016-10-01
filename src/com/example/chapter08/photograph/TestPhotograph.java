package com.example.chapter08.photograph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.chapter08.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class TestPhotograph extends Activity implements OnClickListener {
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private Button takePhoto, chooseFromAlbum;
	private ImageView picture;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b3_photograph);
		takePhoto = (Button) findViewById(R.id.b3_take_photo);
		chooseFromAlbum = (Button) findViewById(R.id.b3_choose_from_album);
		picture = (ImageView) findViewById(R.id.b3_picture);
		takePhoto.setOnClickListener(this);
		chooseFromAlbum.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b3_take_photo:
		{
			// 创建File对象，用于存储拍照后的图片
			File outputImage = new File(
					Environment.getExternalStorageDirectory(), "tempImage.jpg");
			try {
				if (outputImage.exists()) {
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputImage);
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			//启动相机程序
			startActivityForResult(intent, TAKE_PHOTO);
			break;
		}
		case R.id.b3_choose_from_album:
		{
			//创建File对象，用于存储选择的照片
			File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
			try {
				if(outputImage.exists()){
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputImage);
			Intent intent = new Intent("android.intent.action.GET_CONTENT");
			intent.setType("image/*");
			intent.putExtra("crop", true);
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, CROP_PHOTO);
			break;
		}
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PHOTO:
			if(resultCode == RESULT_OK){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				//启动裁剪程序
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if(resultCode == RESULT_OK){
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					//将裁剪后的照片显示出来
					picture.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}
		
		
	}

}
