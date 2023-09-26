package com.tencent.shadow.sample.plugin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



/**
 * 测试插件发起的通知栏在宿主的运行情况
 */
public class CreateNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_submit;
    private TextView tv_submit2;

    //通知管理者
    private NotificationManager notificationManager;
    private Notification notification;
    private int notificationId = 1;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tv_submit=findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_submit2=findViewById(R.id.tv_submit2);
        tv_submit2.setOnClickListener(this);


        //发送通知首先要通过通知服务得到通知管理者
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initNotification();
    }

    /**
     * 初始化通知
     */
    private void initNotification() {

        Intent intent = new Intent(this, MainActivity1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title", "打工人");
        intent.putExtra("content", "我要搞钱");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("test", "测试通知", NotificationManagerCompat.IMPORTANCE_MAX);
            notification = new NotificationCompat.Builder(this, "test")
                    .setSmallIcon(R.mipmap.home_s)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.home_s))
                    .setAutoCancel(true)
                    .setContentTitle("普通通知")
                    .setContentText("我要搞钱")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)//设置自动取消
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText("我要搞钱！！！富强、明主、文明、和谐、自由、平等、公正、法治、爱国、敬业、诚信、友善我要搞钱！！！富强、明主、文明、和谐、自由、平等、公正、法治、爱国、敬业、诚信、友善"))
                    //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.img_0_yuan_gou_big_pic)))
                    .build();

            //setStyle 在Android10测试是通过的但是在Android11的小米上测试并没有通过
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.home_s)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.home_s))
                    .setAutoCancel(true)
                    .setContentTitle("普通通知")
                    .setContentText("普通通知")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)//设置自动取消
                    .build();
        }
    }

    /**
     * 创建通知渠道
     * 因为通知渠道是Android8.0才有的，因此我们添加一个注解
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, importance));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_submit:
                notificationManager.notify(notificationId, notification);
                break;
            case R.id.tv_submit2:

                break;
            default:
                break;
        }
    }
}
