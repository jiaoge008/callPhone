package com.example.phonecalldemo;

import java.io.File;
import android.util.Log;
import com.example.phonecalldemo.FileOperate;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.*;
import android.view.*;

public class PhoneCallDemo extends Activity implements OnClickListener,OnLongClickListener{
	   
	 private Button bt;
	 private EditText et;
	 private DisplayMetrics dm;
	 private LinearLayout.LayoutParams imagebtn_params;
	 private ImageButton jiangjiang;
	 private ImageButton dujun;
	 private ImageButton bingbing;
	 private ImageButton dagu;
	 
	 private LinearLayout linerLayout;
	 private ImageButton topAdd;
	 private Integer deleteImageButtonId;
	 private String deletePhoneNum;
	 
	 private HashMap<Integer,String> map;
	 
	 private int increaseNum = 1;
	 
	 private final static String projectName = "dadianhua";
	 private final static String phoneCallProjectPath = Environment.getExternalStorageDirectory()+File.separator+projectName+File.separator;
	 private final static String imagePath = phoneCallProjectPath+"images"+File.separator;
	 private final static String imageFilePath = imagePath+"myPic.png";
	 private final static String phoneBookFilePath = phoneCallProjectPath+"dianhuaben.txt";
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
			setContentView(R.layout.main1);

			
			//���Ŀ¼���������½���Ŀ���ݴ洢Ŀ¼
			makeProjectPath();
			
			//��ȡ�ļ�
			map = FileOperate.ReadTxtFile(phoneBookFilePath);
			FileOperate.composeMapStr(map);
			
			linerLayout = (LinearLayout)findViewById(R.id.ll);
			
			
			ImageButton last = new ImageButton(this);
			dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
	        
	        //ȡ����Դ
	        bt = (Button)findViewById(R.id.bt1);
	        et = (EditText)findViewById(R.id.et1);
	        topAdd = (ImageButton)findViewById(R.id.top_add);
	        bt.setVisibility(View.GONE);
	        et.setVisibility(View.GONE);
	        jiangjiang = (ImageButton)findViewById(R.id.jiangjiang);
	        jiangjiang.setVisibility(View.GONE);
	        dujun = (ImageButton)findViewById(R.id.dujun);
	        dujun.setVisibility(View.GONE);
	        bingbing = (ImageButton)findViewById(R.id.bingbing);
	        bingbing.setVisibility(View.GONE);
	        dagu = (ImageButton)findViewById(R.id.dagu);
	        dagu.setVisibility(View.GONE);
	        imagebtn_params = new LinearLayout.LayoutParams(
	        		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	        imagebtn_params.height = dm.heightPixels/2;
	        imagebtn_params.width = dm.widthPixels;
	        dujun.setLayoutParams(imagebtn_params);
	        bingbing.setLayoutParams(imagebtn_params);
	        dagu.setLayoutParams(imagebtn_params);
	        //ͨ������button��̬����ImageButton
	        Iterator iter = map.keySet().iterator();
			while (iter.hasNext())
			{
				Object key = iter.next();
				Object val = map.get(key);
				Integer integerKey = (Integer)key;
				if(integerKey > increaseNum)
				{
					increaseNum = integerKey;
				}
				ImageButton addImageButton = new ImageButton(this);
				addImageButton.setId(integerKey.intValue());
				addImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
				addImageButton.setLayoutParams(imagebtn_params);
				addImageButton.setOnClickListener(this);
				//���ó���ʱ�亯��
				addImageButton.setOnLongClickListener(this);
				String imageFilePath = imagePath+(String)val+".png";
				Log.e("startActivity:",(String)val);
				File tmpImageFile = new File(imageFilePath);
				Bitmap photo;
				try{
					FileInputStream in = new FileInputStream(tmpImageFile); 
					try{
						 photo = BitmapFactory.decodeStream(in);
						 Drawable drawable = new BitmapDrawable(photo);
						 addImageButton.setImageDrawable(drawable);
					     linerLayout.addView(addImageButton);
						 in.close();
					}catch (IOException e){
						
					}
					
				}catch (FileNotFoundException e){
					
				}

			}
			
	        last.setId(1000);
	        last.setImageDrawable(getResources().getDrawable(R.drawable.changjiang));
	        last.setScaleType(ImageView.ScaleType.FIT_XY);
	        last.setLayoutParams(imagebtn_params);
	        last.setVisibility(View.GONE);
	        last.setOnClickListener(this);
	        //topAdd.setOnClickListener(this);
	        linerLayout.addView(last);
	        topAdd.setOnClickListener(new ImageButton.OnClickListener(){
	        	public void onClick(View v){
	        		
	        		Intent intent = new Intent();
	        		intent.setClass(PhoneCallDemo.this, AddNewPhone.class);
	        		//����
	        		//startActivity(intent);
	        		startActivityForResult(intent,1000);
	        	}
	        });
	        dagu.setOnClickListener(new ImageButton.OnClickListener(){
	        	public void onClick(View v){
	        		String daguPhomeNum = "13280533817";
	        		Intent phoneIntent = new Intent("android.intent.action.CALL",
		     			       Uri.parse("tel:" + daguPhomeNum));
	        		//����
	        		startActivity(phoneIntent);
	        	}
	        });
	        bingbing.setOnClickListener(new ImageButton.OnClickListener(){
	        	public void onClick(View v){
	        		String bingBingPhoneNum = "13854975246";
	        		Intent phoneIntent = new Intent("android.intent.action.CALL",
		     			       Uri.parse("tel:" + bingBingPhoneNum));
	        		//����
	        		startActivity(phoneIntent);
	        	}
	        });
	        dujun.setOnClickListener(new ImageButton.OnClickListener(){
	        	public void onClick(View v){
	        		String dujunPhoneNum = "13505605306";
	        		Intent phoneIntent = new Intent("android.intent.action.CALL",
		     			       Uri.parse("tel:" + dujunPhoneNum));
	        		//����
	        		startActivity(phoneIntent);
	        	}
	        });
	        jiangjiang.setOnClickListener(new ImageButton.OnClickListener(){
	        	public void onClick(View v){
	        		String jiangJiangPhoneNum = "18698868895";
	        		Intent phoneIntent = new Intent("android.intent.action.CALL",
	     			       Uri.parse("tel:" + jiangJiangPhoneNum));
	        		//����
	        		startActivity(phoneIntent); 
	        	}
	        });
	        //�����¼���Ӧ
	        
	         bt.setOnClickListener(new Button.OnClickListener(){
			   @Override
			   public void onClick(View v) {
			    
			    //ȡ������ĵ绰���봮
			    String inputStr = et.getText().toString();
			    //������벻Ϊ�մ�����绰��Intent
			    if(inputStr.trim().length()!=0)
			    {
			     Intent phoneIntent = new Intent("android.intent.action.CALL",
			       Uri.parse("tel:" + inputStr));
			     //����
			     startActivity(phoneIntent); 
			    }
			    //����Toast��ʾһ��
			    else{
			     Toast.makeText(PhoneCallDemo.this, "��������Ϊ��", Toast.LENGTH_LONG).show();
			    }
			   }
	         
	        });
	    }
	    public boolean onLongClick(View v)
	    {
	    	Integer imageButtoId = v.getId();
	    	deleteImageButtonId = imageButtoId;
	    	String phoneNum = map.get(imageButtoId);
	    	deletePhoneNum = phoneNum;
	    	new AlertDialog.Builder(this)
			.setTitle("�༭|ɾ��")
			.setNegativeButton("�༭", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
					Intent intent = new Intent();
					
					intent.putExtra("editPhoneNum",map.get(deleteImageButtonId));
					intent.putExtra("editId", deleteImageButtonId);
					intent.setClass(PhoneCallDemo.this,DeletePhone.class);
					
					startActivityForResult(intent, 1001);

				}
			})
			.setPositiveButton("ɾ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					ImageButton delImageButton = (ImageButton)findViewById((int)deleteImageButtonId);
					
					map.remove(deleteImageButtonId);
					String coverStr = FileOperate.composeMapStr(map);
					FileOperate.WriteFileCover(phoneBookFilePath, coverStr);
					String tmpImageFilePath = imagePath+deletePhoneNum+".png";
					File tmpImageFile = new File(tmpImageFilePath);
					tmpImageFile.delete();
					linerLayout.removeView(delImageButton);
				}
			}).show();
	    	return true;
	    }
	    public void onClick(View v){
	    	Integer imageButtoId = v.getId();
	    	String phoneNum = map.get(imageButtoId);
	    	Intent phoneIntent = new Intent("android.intent.action.CALL",
  			       Uri.parse("tel:" + phoneNum));
	    	startActivity(phoneIntent); 
	        
	    	switch(v.getId()){
	    	/*
	    			case 100:
	            	   String changjianPhoneNum = "18698868895";
		        		Intent phoneIntent = new Intent("android.intent.action.CALL",
		     			       Uri.parse("tel:" + changjianPhoneNum));
		        		//����
		        		startActivity(phoneIntent); 
	            	   break;
	            	   */
	               case R.id.top_add:
	            	   ShowPickDialog();
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
			
			super.onActivityResult(requestCode, resultCode, data);
			Log.e("num:","haaaaaaaaaaaaaaaaaaa");
			//�����ϵ�˵Ĳ������ش���
			if(requestCode == 1000 && resultCode == 1001)
			{
				String phoneNum = data.getStringExtra("phoneNum");
				//Log.e("num:",phoneNum);
				Bitmap photo = data.getParcelableExtra("bitPhoto");
				
				String imageFilePath = imagePath+phoneNum+".png";
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
				Drawable drawable = new BitmapDrawable(photo);
				ImageButton addImageButton = new ImageButton(this);
				increaseNum += 1;
				map.put(increaseNum, phoneNum);
				addImageButton.setId(increaseNum);
				addImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
				addImageButton.setLayoutParams(imagebtn_params);
				addImageButton.setImageDrawable(drawable);
				addImageButton.setOnClickListener(this);
		        linerLayout.addView(addImageButton);
		        FileOperate.WriteFileAppend(phoneBookFilePath,phoneNum+"\n");
			}
			//�޸���ϵ�˵���Ӵ���
			if(requestCode == 1001 && resultCode == 1001)
			{
				String phoneNum = data.getStringExtra("phoneNum");
				Bitmap photo = data.getParcelableExtra("bitPhoto");
				int editId = data.getIntExtra("editId", 0);
				String rawPhoneNum = data.getStringExtra("rawPhoneNum");
				
				boolean changedHeadImage = data.getBooleanExtra("changedHeadImage", false);
				boolean changedPhoneNum = data.getBooleanExtra("changedPhoneNum", false);
				if(changedHeadImage == true)
				{
					String imageFilePath = imagePath+phoneNum+".png";
					Log.e("phoneNum:",imageFilePath);
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
					Drawable drawable = new BitmapDrawable(photo);
					ImageButton editImageButton = (ImageButton)findViewById(editId);
					editImageButton.setImageDrawable(drawable);
				}
				if(changedPhoneNum == true)
				{
					map.put(editId,phoneNum);
					String coverStr = FileOperate.composeMapStr(map);
					FileOperate.WriteFileCover(phoneBookFilePath, coverStr);
					
					String imageFilePath = imagePath+phoneNum+".png";
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
					String deleteFilePath = imagePath+rawPhoneNum+".png";
					File deleteFile = new File(deleteFilePath);
					deleteFile.delete();
				}
			}
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
				//��Bitmap����ΪpngͼƬ
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
				ImageButton dynamicButton = new ImageButton(this);
				dynamicButton.setScaleType(ImageView.ScaleType.FIT_XY);
				dynamicButton.setLayoutParams(imagebtn_params);
				dynamicButton.setBackground(drawable);
				linerLayout.addView(dynamicButton);
				//ib.setBackgroundDrawable(drawable);
				//iv.setBackgroundDrawable(drawable);
			}
		}
		private void makeProjectPath()
		{
			File dirFirstFile=new File(phoneCallProjectPath);//�½�һ����Ŀ¼
			if(!dirFirstFile.exists()){//�ж��ļ���Ŀ¼�Ƿ����  
				dirFirstFile.mkdir();//����������򴴽�  
			}
			File dirSecondFile=new File(imagePath);//�½�������Ŀ¼ 
			if(!dirSecondFile.exists()){//�ж��ļ���Ŀ¼�Ƿ����
				dirSecondFile.mkdir();//����������򴴽�  
			}
		}
	}
class StartClick implements OnClickListener{
	public void onClick(View v){
        switch(v.getId()){
               case 100:
            	   String changjianPhoneNum = "18698868895";
	        		Intent phoneIntent = new Intent("android.intent.action.CALL",
	     			       Uri.parse("tel:" + changjianPhoneNum));
	        		//����
	        		//startActivity(phoneIntent); 
            	   break;
               default:;
                      
        }
 }
}