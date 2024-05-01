package com.example.midtermwork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView mdate;
    ImageView mSecondOnes,mSecondTenth,mMinuteOnes,mMinuteTenth,mHourOnes,mHourTenth;
    private static final int MESSAGE_CODE = 1; // Handler 訊息代碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 獲取當前時間
        mSecondOnes = findViewById(R.id.ViewSecondOnes);
        mSecondTenth = findViewById(R.id.ViewSecondTenth);
        mMinuteOnes = findViewById(R.id.ViewMinuteOnes);
        mMinuteTenth = findViewById(R.id.ViewMinuteTenth);
        mHourOnes = findViewById(R.id.ViewHourOnes);
        mHourTenth = findViewById(R.id.ViewHourTenth);

        // 更新日期
        mdate = findViewById(R.id.Date);
        Get_Date(mdate);

        // 創建 Handler
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE_CODE) {
                    // 更新圖片
                    switchImagesScondOnes(updateTime());
                    switchImageSecondTenth(updateTime());
                    switchImageMinuteOnes(updateTime());
                    switchImageMinuteTenth(updateTime());
                    switchImageHourOnes(updateTime());
                    switchImageHourTenth(updateTime());
                }
            }
        };

        // 定義 Runnable
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        Message message = new Message();
                        message.what = MESSAGE_CODE;
                        handler.sendMessage(message); // 發送消息到 Handler
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 創建並啟動執行緒
        Thread thread = new Thread(r);
        thread.start();
    }

    // 獲取日期
    public void Get_Date(TextView mdate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String s = new String("現在日期：" + year + "年" +
                month + "月" + dayOfMonth + "日 ");
        mdate.setText(s);
    }

    // 獲取時間
    private int[] updateTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new int[]{hour, minute, second};
    }

    // 切換圖片
    private void switchImagesScondOnes(int[] second) {
        int currentTimesecond = second[2] % 10;
        int imageResourceId = getImageResourceId(currentTimesecond);
        mSecondOnes.setImageResource(imageResourceId);
    }
    private void switchImageSecondTenth(int[] second) {
        int currentTimeTenth = (second[2] / 10) % 10;
        int imageResourceId = getImageResourceId(currentTimeTenth);
        mSecondTenth.setImageResource(imageResourceId);

    }

    private void switchImageMinuteOnes(int[] second) {
        int currentTimesecond = second[1] % 10;
        int imageResourceId = getImageResourceId(currentTimesecond);
        mMinuteOnes.setImageResource(imageResourceId);

    }
    private void switchImageMinuteTenth(int[] second) {
        int currentTimeTenth = (second[1] / 10) % 10;
        int imageResourceId = getImageResourceId(currentTimeTenth);
        mMinuteTenth.setImageResource(imageResourceId);

    }

    private void switchImageHourOnes(int[] second) {
        int currentTimesecond = second[0] % 10;
        int imageResourceId = getImageResourceId(currentTimesecond);
        mHourOnes.setImageResource(imageResourceId);
    }
    private void switchImageHourTenth(int[] second) {
        int currentTimesecond = (second[0] / 10) % 10;
        int imageResourceId = getImageResourceId(currentTimesecond);
        mHourTenth.setImageResource(imageResourceId);
    }


    // 獲取圖片資源 ID
    private int getImageResourceId(int index) {
        String imageName = "image" + index;
        return mSecondOnes.getResources().getIdentifier(imageName, "drawable", mSecondOnes.getContext().getPackageName());
    }
}
