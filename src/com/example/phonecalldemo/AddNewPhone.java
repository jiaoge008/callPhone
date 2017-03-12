package com.example.phonecalldemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.DisplayMetrics;

public class AddNewPhone extends Activity implements OnClickListener{
	
	private DisplayMetrics dm;
	private LinearLayout.LayoutParams imagebtn_params;
	private ImageButton addHeadImageButton;
	private Button addPersonButton;
	private EditText phoneNumEditText;
	
	private Bitmap backPicMap=null;
	
	private final static String projectName = "dadianhua";
	private final static String phoneCallProjectPath = Environment.getExternalStorageDirectory()+File.separator+projectName+File.separator;
	private final static String imagePath = phoneCallProjectPath+"images"+File.separator;
	private final static String imageFilePath = imagePath+"myPic.png";
	private final static String phoneBookFilePath = phoneCallProjectPath+"dianhuaben.txt";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.add_new_phone);
		
		
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		imagebtn_params = new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imagebtn_params.height = dm.heightPixels/2;
        imagebtn_params.width = dm.widthPixels;
        
        addHeadImageButton = (ImageButton)findViewById(R.id.add_head_image);
        addHeadImageButton.setLayoutParams(imagebtn_params);
        addHeadImageButton.setOnClickListener(this);
        
        addPersonButton = (Button)findViewById(R.id.add_person);
        addPersonButton.setOnClickListener(this);
        phoneNumEditText = (EditText)findViewById(R.id.phone_num);
        
	}
	
	public void onClick(View v){
        switch(v.getId()){
               case R.id.add_head_image:
            	   ShowPickDialog();
            	   break;
               case R.id.add_person:
            	   if(backPicMap == null)
            	   {
            		   Toast.makeText(AddNewPhone.this, "��������ͷ��", Toast.LENGTH_LONG).show();
	            		 new AlertDialog.Builder(AddNewPhone.this)
	   					.setIcon(android.R.drawable.ic_dialog_alert)
	   					.setTitle("��ʾ")
	   					.setMessage("��������ͷ��")
	   					.setPositiveButton("ȷ��", null)
	   					.show();
	   					return;
            	   }
            	   Intent intent = new Intent();
            	   intent.putExtra("bitPhoto", backPicMap);
            	 //ȡ������ĵ绰���봮
   			    String inputStr = phoneNumEditText.getText().toString();
            	   if(inputStr.trim().length()==0)
            	   {
            		   Toast.makeText(AddNewPhone.this, "�绰���벻��Ϊ��", Toast.LENGTH_LONG).show();
            		   new AlertDialog.Builder(AddNewPhone.this)
	   					.setIcon(android.R.drawable.ic_dialog_alert)
	   					.setTitle("��ʾ")
	   					.setMessage("��������绰����")
	   					.setPositiveButton("ȷ��", null)
	   					.show();
	   					return;
            	   }
            	   //���绰�����Ƿ��Ѵ���
            	   HashMap<Integer,String> map;
            	   map = FileOperate.ReadTxtFile(phoneBookFilePath);
            	   Iterator iter = map.keySet().iterator();
            	   while (iter.hasNext())
            	   {
            		   	Object key = iter.next();
       					Object val = map.get(key);
       					Integer integerKey = (Integer)key;
       					String stringVal = (String)val;
       					if(stringVal.equals(inputStr))
       					{
       						Log.e("rawVal:",stringVal);
       						Log.e("rawInputVal:",inputStr);
       						new AlertDialog.Builder(AddNewPhone.this)
    	   					.setIcon(android.R.drawable.ic_dialog_alert)
    	   					.setTitle("��ʾ")
    	   					.setMessage("�绰����"+inputStr+"�Ѵ���")
    	   					.setPositiveButton("ȷ��", null)
    	   					.show();
    	   					return;
       					}
            	   }
            	   
            	   intent.putExtra("phoneNum", inputStr);
            	   setResult(1001, intent);
            	   finish();
            	   break;
               default:;
                      
        }
	}
	/**
	 * ѡ����ʾ�Ի���
	 */
	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
				.setTitle("����ͷ��...")
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						/**
						 * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬
						 * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ���
						 */
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						
						/**
						 * ������仰����������ʽд��һ����Ч���������
						 * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 * intent.setType(""image/*");������������
						 * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����"
						 * ����ط�С���и����ʣ�ϣ�����ֽ���£������������URI������ΪʲôҪ��������ʽ��дѽ����ʲô����
						 */
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent, 1);

					}
				})
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						/**
						 * ������仹�������ӣ����ÿ������չ��ܣ�����Ϊʲô�п������գ���ҿ��Բο����¹ٷ�
						 * �ĵ���you_sdk_path/docs/guide/topics/media/camera.html
						 * �Ҹտ���ʱ����Ϊ̫�������濴����ʵ�Ǵ�ģ�����������õ�̫���ˣ����Դ�Ҳ�Ҫ��Ϊ
						 * �ٷ��ĵ�̫���˾Ͳ����ˣ���ʵ�Ǵ�ģ�����ط�С��Ҳ���ˣ��������
						 */
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						//�������ָ������������պ����Ƭ�洢��·��
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										"xiaoma.jpg")));
						startActivityForResult(intent, 2);
					}
				}).show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			startPhotoZoom(data.getData());
			break;
		// ����ǵ����������ʱ
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/xiaoma.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// ȡ�òü����ͼƬ
		case 3:
			/**
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ���
			 * �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * ��ǰ����ʱ���ᱨNullException��С��ֻ
			 * ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ�
			 * �ط����жϴ����������
			 * 
			 */
			if(data != null){
				setPicToView(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * �ü�ͼƬ����ʵ��
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����,
		 * ��ֱ�ӵ����ؿ�ģ�С����C C++  ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô
		 * ��������...���
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//�������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	/**
	 * ����ü�֮���ͼƬ����
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			backPicMap = photo;
			//��Bitmap����ΪpngͼƬ
			/*
			File tmpImageFile = new File(imageFilePath);
			try{
				FileOutputStream out = new FileOutputStream(tmpImageFile); 
				photo.compress(Bitmap.CompressFormat.PNG, 60, out); 
				try{
					out.flush(); 
					out.close();
				}catch (IOException e){
					
				}
				
			}catch (FileNotFoundException e){
				
			}
			*/

			Drawable drawable = new BitmapDrawable(photo);
			
			/**
			 * ����ע�͵ķ����ǽ��ü�֮���ͼƬ��Base64Coder���ַ���ʽ��
			 * ������������QQͷ���ϴ����õķ������������
			 */
			
			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// ��ͼƬ�����ַ�����ʽ�洢����
			
			tp = new String(Base64Coder.encodeLines(b));
			����ط���ҿ���д�¸��������ϴ�ͼƬ��ʵ�֣�ֱ�Ӱ�tpֱ���ϴ��Ϳ����ˣ�
			����������ķ����Ƿ������Ǳߵ����ˣ����
			
			������ص��ķ����������ݻ�����Base64Coder����ʽ�Ļ������������·�ʽת��
			Ϊ���ǿ����õ�ͼƬ���;�OK��...���
			Bitmap dBitmap = BitmapFactory.decodeFile(tp);
			Drawable drawable = new BitmapDrawable(dBitmap);
			*/
			ImageButton addHeadImage = (ImageButton)findViewById(R.id.add_head_image);
			addHeadImage.setScaleType(ImageView.ScaleType.FIT_XY);
			addHeadImage.setLayoutParams(imagebtn_params);
			addHeadImage.setImageDrawable(drawable);
			//addHeadImage.setBackground(drawable);
		}
	}

}
