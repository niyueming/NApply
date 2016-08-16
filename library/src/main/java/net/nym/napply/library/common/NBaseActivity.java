/*
 * Copyright (c) 2016  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.napply.library.common;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import net.nym.napply.library.R;
import net.nym.napply.library.https.okhttp.OkHttpClientManager;
import net.nym.napply.library.utils.Log;

import java.util.Arrays;

/**
 * @author niyueming
 * @date 2016-08-11
 * @time 10:23
 */
public class NBaseActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CONTACTS = 1;
    private static final int REQUEST_CALENDAR = 2;
    private static final int REQUEST_LOCATION = 3;
    private static final int REQUEST_STORAGE = 4;
    private static final int REQUEST_MICROPHONE = 5;
    private static final int REQUEST_SENSORS = 6;
    private static final int REQUEST_PHONE = 7;
    private static final int REQUEST_SMS = 8;
    private static final int REQUEST_CAMERA_AND_STORAGE = 9;
    /**
     * Permissions required to read and write contacts
     * 通讯录
     */
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,Manifest.permission.GET_ACCOUNTS};
    /**
     * 日历
     */
    private static String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    /**
     * 摄像头
     */
    private static String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 地理位置
     */
    private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 存储空间
     */
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 存储空间和摄像头
     */
    private static String[] PERMISSIONS_CAMERA_AND_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    /**
     * 麦克风
     */
    private static String[] PERMISSIONS_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    /**
     * 身体传感器
     */
    private static String[] PERMISSIONS_SENSORS = {Manifest.permission.BODY_SENSORS};
    /**
     * 电话
     */
    private static String[] PERMISSIONS_PHONE = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
//            Manifest.permission.READ_CALL_LOG,
//            Manifest.permission.WRITE_CALL_LOG,
//            Manifest.permission.USE_SIP,
//            Manifest.permission.PROCESS_OUTGOING_CALLS,
//            Manifest.permission.ADD_VOICEMAIL
    };
    /**
     * 短信
     */
    private static String[] PERMISSIONS_SMS = {Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
//            "android.permission.READ_CELL_BROADCASTS",
            Manifest.permission.ADD_VOICEMAIL
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showTitle(boolean isShow)
    {
        if (getSupportActionBar() == null){
            return;
        }
        if (isShow){
            getSupportActionBar().show();
        }else {
            getSupportActionBar().hide();
        }
    }

    public void showBack(boolean isShow)
    {
        if (getSupportActionBar() == null){
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        OkHttpClientManager.cancelByTag(this);
        super.onDestroy();
    }

    public <T extends View> T findView(@IdRes int id) {
         View view = (T)super.findViewById(id);
        return view == null ? null : (T)view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 权限
     * */

    private void shouldShowRequestDialog(final @NonNull String[] permissions, final int requestCode, final String permission){

        final Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.napply_hint)
                .setMessage(String.format("允许使用%s",permission))
                .setNegativeButton(R.string.napply_cancel, null)
                .setPositiveButton(R.string.napply_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(NBaseActivity.this, permissions,
                                requestCode);
                    }
                })
                .create();
        dialog.show();
    }

    public void requestCamera(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_CAMERA[0])
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.
            requestCameraPermission();
        } else {
            // Camera permissions is already available, show the camera preview.
            showCamera();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                PERMISSIONS_CAMERA[0])) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_CAMERA,REQUEST_CAMERA,"手机摄像头");
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA,
                    REQUEST_CAMERA);
        }
    }

    public void showCamera() {

    }

    public void requestContacts(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_CONTACT[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_CONTACT[1])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_CONTACT[2])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestContactsPermission();
        } else {
            showContacts();
        }
    }

    private void requestContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CONTACT[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CONTACT[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CONTACT[2])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_CONTACT,REQUEST_CONTACTS,"通讯录");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT,
                    REQUEST_CONTACTS);
        }
    }

    public void showContacts() {

    }

    public void requestCalendar(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_CALENDAR[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_CALENDAR[1])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestCalendarPermission();
        } else {
            showCalendar();
        }
    }

    private void requestCalendarPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CALENDAR[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CALENDAR[1])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_CALENDAR,REQUEST_CALENDAR,"日历");

        } else {

            ActivityCompat.requestPermissions(this, PERMISSIONS_CALENDAR,
                    REQUEST_CALENDAR);
        }
    }

    public void showCalendar() {

    }

    public void requestLocation(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_LOCATION[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_LOCATION[1])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestLocationPermission();
        } else {
            showLocation();
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_LOCATION[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_LOCATION[1])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_LOCATION,REQUEST_LOCATION,"地理位置信息");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION,
                    REQUEST_LOCATION);
        }
    }

    public void showLocation() {

    }

    public void requestStorage(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_STORAGE[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_STORAGE[1])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestStoragePermission();
        } else {
            showStorage();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_STORAGE[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_STORAGE[1])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_STORAGE,REQUEST_STORAGE,"存储空间");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_STORAGE);
        }
    }

    public void showStorage() {

    }

    public void requestMicrophone(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_MICROPHONE[0])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestMicrophonePermission();
        } else {
            showMicrophone();
        }
    }

    private void requestMicrophonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_MICROPHONE[0])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_MICROPHONE,REQUEST_MICROPHONE,"麦克风");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_MICROPHONE,
                    REQUEST_MICROPHONE);
        }
    }

    public void showMicrophone() {

    }


    public void requestSensors(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_SENSORS[0])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestSensorsPermission();
        } else {
            showSensors();
        }
    }

    private void requestSensorsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SENSORS[0])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_SENSORS,REQUEST_SENSORS,"身体传感器");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_SENSORS,
                    REQUEST_SENSORS);
        }
    }

    public void showSensors() {

    }

    public void requestPhone(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[1])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[2])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[3])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[4])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[5])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_PHONE[6])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestPhonePermission();
        } else {
            showPhone();
        }
    }

    private void requestPhonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[1])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[2])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[3])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[4])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[5])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_PHONE[6])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_PHONE,REQUEST_PHONE,"电话");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_PHONE,
                    REQUEST_PHONE);
        }
    }

    public void showPhone() {

    }

    public void requestSms(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[1])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[2])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[3])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[4])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[5])!= PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_SMS[6])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestSmsPermission();
        } else {
            showSms();
        }
    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[4])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[5])
//                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_SMS[6])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_SMS,REQUEST_SMS,"短信");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_SMS,
                    REQUEST_SMS);
        }
    }

    public void showSms() {

    }


    public void requestCameraAndStorage(){
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS_CAMERA_AND_STORAGE[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_CAMERA_AND_STORAGE[1])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISSIONS_CAMERA_AND_STORAGE[2])!= PackageManager.PERMISSION_GRANTED
                ) {
            requestCameraAndStoragePermission();
        } else {
            showCameraAndStorage();
        }
    }

    private void requestCameraAndStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CAMERA_AND_STORAGE[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CAMERA_AND_STORAGE[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSIONS_CAMERA_AND_STORAGE[2])
                ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            shouldShowRequestDialog(PERMISSIONS_CAMERA_AND_STORAGE,REQUEST_CAMERA_AND_STORAGE,"手机相机和存储空间");

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA_AND_STORAGE,
                    REQUEST_CAMERA_AND_STORAGE);
        }
    }

    public void showCameraAndStorage() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (verifyPermissions(grantResults)) {
            switch (requestCode){
                case REQUEST_CAMERA:
                    // Camera permission has been granted, preview can be displayed
                    showCamera();
                    break;
                case REQUEST_CONTACTS:
                    showContacts();
                    break;
                case REQUEST_CALENDAR:
                    showCalendar();
                    break;
                case REQUEST_LOCATION:
                    showLocation();
                    break;
                case REQUEST_STORAGE:
                    showStorage();
                    break;
                case REQUEST_MICROPHONE:
                    showMicrophone();
                    break;
                case REQUEST_SENSORS:
                    showSensors();
                    break;
                case REQUEST_PHONE:
                    showPhone();
                    break;
                case REQUEST_SMS:
                    showSms();
                    break;
                case REQUEST_CAMERA_AND_STORAGE:
                    showCameraAndStorage();
                    break;

                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    break;
            }

        } else {

//                    Log.i(TAG, "CAMERA permission was NOT granted.");
            Log.i("获取权限%s失败", Arrays.toString(permissions));
            String permission = "";
            switch (requestCode) {
                case REQUEST_CAMERA:
                    permission = "摄像头";
                    break;
                case REQUEST_CONTACTS:
                    permission = "通讯录";
                    break;
                case REQUEST_CALENDAR:
                    permission = "日历";
                    break;
                case REQUEST_LOCATION:
                    permission = "地理位置";
                    break;
                case REQUEST_STORAGE:
                    permission = "存储空间";
                    break;
                case REQUEST_MICROPHONE:
                    permission = "麦克风";
                    break;
                case REQUEST_SENSORS:
                    permission = "身体传感器";
                    break;
                case REQUEST_PHONE:
                    permission = "电话";
                    break;
                case REQUEST_SMS:
                    permission = "短信";
                    break;
                case REQUEST_CAMERA_AND_STORAGE:
                    permission = "摄像头和存储空间";
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    break;
            }

            final Dialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.napply_hint)
                    .setMessage(String.format("未获得授权使用%s",permission))
                    .setNegativeButton(R.string.napply_cancel, null)
                    .setPositiveButton(R.string.napply_toSet, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getApplicationInfo().packageName, null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                    .create();
            dialog.show();
        }

    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
