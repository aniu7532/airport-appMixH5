package com.tencent.shadow.sample.plugin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import com.tencent.shadow.sample.plugin.utils.FileUtil;
import com.tencent.shadow.sample.plugin.utils.LogUtils;
import com.tencent.shadow.sample.plugin.utils.UriToPathUtils;

import java.io.File;
import java.util.List;

/**
 * 获取权限的测试
 */
public class PermissionsTestActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_title;

    private TextView tvGetPermissions;
    private TextView tvGetLocalPic;
    private TextView tvCheckPermissionsCamera;
    private TextView tvCamera;

    private ImageView imgShow;


    private ActivityResultLauncher<Boolean> activityResultLocalPic; //访问本地图库的回调
    private ActivityResultLauncher<Boolean> activityResultTakePhoto; //拍照的回调

    public static void launch(Context context) {
        Intent intent = new Intent(context, PermissionsTestActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_permissions_test);

        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText("权限相关的测试案例");

        tvGetPermissions = findViewById(R.id.tvGetPermissions);
        tvGetPermissions.setOnClickListener(this);

        tvGetLocalPic = findViewById(R.id.tvGetLocalPic);
        tvGetLocalPic.setOnClickListener(this);

        tvCheckPermissionsCamera = findViewById(R.id.tvCheckPermissionsCamera);
        tvCheckPermissionsCamera.setOnClickListener(this);

        tvCamera = findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(this);

        imgShow = findViewById(R.id.imgShow);


        /**
         * 访问本地图片的startActivityForResult
         */
        activityResultLocalPic = registerForActivityResult(new ActivityResultContract<Boolean, Uri>() {

            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Boolean input) {
                Intent intent = new Intent();
                intent.setType("image/*");//开启Pictures画面Type设定为image
                intent.setAction(Intent.ACTION_GET_CONTENT); //使用Intent.ACTION_GET_CONTENT这个Action
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK) {
                    return intent.getData();
                } else {
                    return null;
                }

            }
        }, uri -> {
            if (uri == null) {
                LogUtils.logD("你已放弃选择图片,无法为你修改头像");
                return;
            }
            if (null == UriToPathUtils.getRealPathFromUri(context, uri)) {
                LogUtils.logD("未成功获取到原图");
            } else {
                String path = UriToPathUtils.getRealPathFromUri(context, uri);
                LogUtils.logD("绝对地址：" + path);
                LogUtils.logD("绝对uri：" + uri);

                Glide.with(context).load(new File(path)).into(imgShow);
            }
        });


        /**
         * 相机拍照
         */
        activityResultTakePhoto = registerForActivityResult(new ActivityResultContract<Boolean, String>() {

            private String filePath;

            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Boolean input) {
                String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
                if (!state.equals(Environment.MEDIA_MOUNTED)) { // 直接调用系统的照相机
                    Toast.makeText(context, "请检查手机是否有SD卡", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                filePath = FileUtil.getFileName(context);
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    //插件化运行就是这个
                    //uri = FileProvider.getUriForFile(context, "com.caet.asapp".concat(".FileProvider"), new File(filePath));

                    //独立运行就是自己的
                    //uri = FileProvider.getUriForFile(context, "com.tencent.shadow.sample.plugin".concat(".FileProvider"), new File(filePath));

                    //这样写的目的是可以灵活读取，不需要手动改变代码，可以自行根据独立运行或者插件化运行读取对应authority
                    uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID.concat(".FileProvider"), new File(filePath));

                    LogUtils.logD("打印FileProvider");
                    LogUtils.logD(BuildConfig.APPLICATION_ID.concat(".FileProvider"));

                } else {
                    uri = Uri.fromFile(new File(filePath));
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                return intent;
            }

            @Override
            public String parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK) {
                    return filePath;
                } else {
                    return null;
                }
            }
        }, new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String imgFilePath) {
                if (imgFilePath == null) {
                    Toast.makeText(context, "你已放弃拍照,无法为你修改头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                LogUtils.logD(imgFilePath);

                Glide.with(context).load(new File(imgFilePath)).into(imgShow);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tvGetPermissions:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (XXPermissions.isGranted(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
                        LogUtils.logD("插件已经获取到了存储权限");
                    } else {
                        LogUtils.logD("插件未获取到了存储权限");
                    }

                } else {

                    if (XXPermissions.isGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        LogUtils.logD("插件已经获取到了存储权限");
                    } else {
                        LogUtils.logD("插件未获取到了存储权限");
                    }
                }
                break;
            case R.id.tvGetLocalPic:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    XXPermissions.with(this).permission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                            .request(new OnPermissionCallback() {
                                @Override
                                public void onGranted(List<String> permissions, boolean all) {
                                    LogUtils.logD("XXPermissions_onGranted");
                                    activityResultLocalPic.launch(true);
                                }

                                @Override
                                public void onDenied(List<String> permissions, boolean never) {
                                    LogUtils.logD("XXPermissions_onDenied");
                                }
                            });


                } else {

                    XXPermissions.with(this).permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .request(new OnPermissionCallback() {
                                @Override
                                public void onGranted(List<String> permissions, boolean all) {
                                    LogUtils.logD("XXPermissions_onGranted");
                                    activityResultLocalPic.launch(true);
                                }

                                @Override
                                public void onDenied(List<String> permissions, boolean never) {
                                    LogUtils.logD("XXPermissions_onDenied");
                                }
                            });
                }
                break;
            case R.id.tvCheckPermissionsCamera:
                if (XXPermissions.isGranted(this, Manifest.permission.CAMERA)) {
                    LogUtils.logD("插件已经获取到了相机权限");
                } else {
                    LogUtils.logD("插件未获取到了相机权限");
                }
                break;
            case R.id.tvCamera://启动相机拍照
                XXPermissions.with(this).permission(Manifest.permission.CAMERA)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(List<String> permissions, boolean all) {
                                activityResultTakePhoto.launch(true);
                            }

                            @Override
                            public void onDenied(List<String> permissions, boolean never) {

                            }
                        });


                break;
            default:
                break;
        }
    }
}
