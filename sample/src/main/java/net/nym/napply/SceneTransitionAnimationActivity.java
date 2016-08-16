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

package net.nym.napply;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import net.nym.napply.library.utils.ContextUtils;
import net.nym.napply.library.utils.Log;

/**
 * 1、style为material主题
 * <style name="myTheme" parent="android:Theme.Material">
 ＊ <!-- 允许使用transitions -->
 ＊ <item name="android:windowContentTransitions">true</item>

 ＊ <!-- 指定进入和退出transitions -->
 ＊ <item name="android:windowEnterTransition">@transition/explode</item>
 ＊ <item name="android:windowExitTransition">@transition/explode</item>
 ＊
 ＊ <!-- 指定shared element transitions -->
 ＊ <item name="android:windowSharedElementEnterTransition">
 ＊ @transition/change_image_transform</item>
 ＊ <item name="android:windowSharedElementExitTransition">
 ＊ @transition/change_image_transform</item>
 ＊ </style>
 ＊ 或者
 ＊ 2、代码设置
 ＊ // 允许使用transitions
 ＊ getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
 ＊
 ＊ // 设置一个exit transition
 ＊ getWindow().setExitTransition(new Explode());//new Slide()  new Fade()
 *
 * @author niyueming
 * @date 2016-08-16
 * @time 11:26
 */
public class SceneTransitionAnimationActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (ContextUtils.isLollipopOrLater()){
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            getWindow().setEnterTransition(new Explode());
//            getWindow().setExitTransition(new Explode());
//            getWindow().setSharedElementEnterTransition(new Explode());
//            getWindow().setSharedElementExitTransition(new Explode());
//        }
        setContentView(R.layout.scene_transition_animation_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        text = (TextView) findViewById(R.id.text);
        text.setText(getIntent().getStringExtra("name"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.setAlpha(0f);
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    Log.e("onTransitionStart");
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    text.animate().setDuration(1000).alpha(1f);
                    Log.e("onTransitionEnd");
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    Log.e("onTransitionCancel");
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    Log.e("onTransitionPause");
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    Log.e("onTransitionResume");
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
