package com.xhl.testandroidflatbuffer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

import flatbuffer.FResultInfo;
import json.ResultInfo;

public class MainActivity extends AppCompatActivity {


    private long currentMs;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                long time = (long) msg.obj;
                Bundle bundle = msg.getData();
                String result = bundle.getString("result", "");
                Toast.makeText(MainActivity.this, time + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                long time = (long) msg.obj;
                Bundle bundle = msg.getData();
                String result = bundle.getString("result", "");
                Toast.makeText(MainActivity.this, time + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void getJson(View v) {
        currentMs = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL jsonUrl = new URL("http://192.168.43.48:8080/reJson");
                    HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    connection.connect();

                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String strRead = null;
                    StringBuffer sbf = new StringBuffer();
                    while ((strRead = reader.readLine()) != null) {
                        sbf.append(strRead);
                        sbf.append("\r\n");
                    }
                    reader.close();
                    is.close();
                    String result = sbf.toString();
                    ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
                    long resMs = System.currentTimeMillis();
                    long time = resMs - currentMs;
                    Message msg = Message.obtain();
                    msg.obj = time;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", resultInfo.getCode() + " " + resultInfo.getData().getDebrisHelp());
                    msg.setData(bundle);
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    public void getFlatBuffer(View v) {
        currentMs = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL jsonUrl = new URL("http://192.168.43.48:8080/reFlatBuffer");
                    HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    connection.connect();
                    is = connection.getInputStream();
                    byte[] bytes = readStream(is);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                    FResultInfo fResultInfo = FResultInfo.getRootAsFResultInfo(byteBuffer);
                    long resMs = System.currentTimeMillis();
                    long time = resMs - currentMs;
                    Message msg = Message.obtain();
                    msg.obj = time;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", fResultInfo.code() + " " + fResultInfo.data().debrisHelp());
                    msg.setData(bundle);
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(is!=null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
