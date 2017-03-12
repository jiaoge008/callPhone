package com.example.phonecalldemo;
import java.io.File;
import java.io.FileInputStream;
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
import android.graphics.BitmapFactory;
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
public class DeletePhone extends Activity implements OnClickListener{

	private DisplayMetrics dm;
	private LinearLayout.LayoutParams imagebtn_params;
	private ImageButton deleteHeadImageButton;
	private EditText phoneNumEditText;
	private Button modifyButton;
	
	private Bitmap backPicMap=null;
	
	private String editPhoneNum = "";
	private int editId;
	private boolean changedHeadImage = false;
	private boolean changedPhoneNum = false;
	
	private final static String projectName = "dadianhua";
	private final static String phoneCallProjectPath = Environment.getExternalStorageDirectory()+File.separator+projectName+File.separator;
	private final static String imagePath = phoneCallProjectPath+"images"+File.separator;
	private final static String phoneBookFilePath = phoneCallProjectPath+"dianhuaben.txt";
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.delete_phone);
		//��ȡPhoneCallDemo���ݹ����Ĳ���
		Intent intent = getIntent();
		editPhoneNum = intent.getStringExtra("editPhoneNum");
		editId = intent.getIntExtra("editId", 0);
		
		deleteHeadImageButton = (ImageButton)findViewById(R.id.delete_head_image);
		phoneNumEditText = (EditText)findViewById(R.id.delete_phone_num);
		modifyButton = (Button)findViewById(R.id.modify_person);
		
		phoneNumEditText.setText(editPhoneNum);
		
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		imagebtn_params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imagebtn_params.height = dm.heightPixels/2;
        imagebtn_params.width = dm.widthPixels;
        deleteHeadImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        deleteHeadImageButton.setLayoutParams(imagebtn_params);
        String imageFilePath = imagePath+editPhoneNum+".png";
        File tmpImageFile = new File(imageFilePath);
        try{
			FileInputStream in = new FileInputStream(tmpImageFile); 
			try{
				backPicMap = BitmapFactory.decodeStream(in);
				 Drawable drawable = new BitmapDrawable(backPicMap);
				 deleteHeadImageButton.setImageDrawable(drawable);
				 in.close();
			}catch (IOException e){
				
			}
			
		}catch (FileNotFoundException e){
			
		}
        deleteHeadImageButton.setOnClickListener(this);
        modifyButton.setOnClickListener(this);
        //deleteHeadImageButton.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		switch(v.getId()){
		
			case R.id.delete_head_image:
				ShowPickDialog();
				break;
			case R.id.modify_person:
				//����޸ĺ�ĵ绰����
				String inputStr = phoneNumEditText.getText().toString();
				if(inputStr.trim().length()==0)
				{
					new AlertDialog.Builder(DeletePhone.this)
   					.setIcon(android.R.drawable.ic_dialog_alert)
   					.setTitle("��ʾ")
   					.setMessage("�绰����Ϊ��")
   					.setPositiveButton("ȷ��", null)
   					.show();
   					return;
				}
				Log.e("editNum:",editPhoneNum);
				Log.e("inputstr:",inputStr);
				if(!editPhoneNum.equals(inputStr))
				{
					changedPhoneNum = true;
				}
				//����绰�����ͷ��û�иı�Ļ��������ʼ���ڻش�����
				if(changedPhoneNum==false && changedHeadImage==false)
				{
					finish();
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("bitPhoto", backPicMap);
				intent.putExtra("phoneNum", inputStr);
				intent.putExtra("editId", editId);
				intent.putExtra("rawPhoneNum", editPhoneNum);
				intent.putExtra("changedPhoneNum",changedPhoneNum);
				intent.putExtra("changedHeadImage", changedHeadImage);
				Log.d("deletephone:","jkll");
				setResult(1001, intent);
				break;
			default:;
		}
		finish();

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
			changedHeadImage = true;
			Drawable drawable = new BitmapDrawable(photo);

			deleteHeadImageButton = (ImageButton)findViewById(R.id.delete_head_image);
			deleteHeadImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
			deleteHeadImageButton.setLayoutParams(imagebtn_params);
			deleteHeadImageButton.setImageDrawable(drawable);
			//addHeadImage.setBackground(drawable);
		}
	}
}

