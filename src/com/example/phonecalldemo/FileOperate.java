package com.example.phonecalldemo;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileWriter;

import android.util.Log;

public class FileOperate {
	
	public static HashMap<Integer,String> ReadTxtFile(String strFilePath)
    {
        String path = strFilePath;
        Integer count = 1000;
        String content = ""; //�ļ������ַ���
        HashMap<Integer,String> map = new HashMap<Integer,String>();
            //���ļ�
            File file = new File(path);
            //���path�Ǵ��ݹ����Ĳ�����������һ����Ŀ¼���ж�
            if (!file.exists())
            {
                try{
                	file.createNewFile();
                }catch(IOException e){
                	e.printStackTrace();
                }
            }
            else
            {
                try {
                    InputStream instream = new FileInputStream(file); 
                    if (instream != null) 
                    {
                        InputStreamReader inputreader = new InputStreamReader(instream);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        //���ж�ȡ
                        while (( line = buffreader.readLine()) != null) {
                            content += line + "\n";
                            map.put(count, line);
                            count += 1;
                        }
                        instream.close();
                    }
                }
                catch (java.io.FileNotFoundException e) 
                {
                    
                } 
                catch (IOException e) 
                {
                     
                }
            }
            return map;
            //return content;
    }
	public static void WriteFileCover(String strFilePath,String content)
	{
		
		//FileOutputStream fout =openFileOutput(strFilePath, Context.MODE_PRIVATE);
		try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�,false��ʾ����
            FileWriter writer = new FileWriter(strFilePath, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void WriteFileAppend(String strFilePath,String content)
	{
		
		//FileOutputStream fout =openFileOutput(strFilePath, Context.MODE_PRIVATE);
		try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�,false��ʾ����
            FileWriter writer = new FileWriter(strFilePath, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	//��hashmap�е�������ϳ��ַ�������
	public static String composeMapStr(HashMap<Integer,String> map)
	{
		String content = "";
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext())
		{
			Object key = iter.next();
			Object val = map.get(key);
			content += (String)val + "\n";
		}
		return content;
	}
}
