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

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import net.nym.napply.library.common.FrescoImageLoader;
import net.nym.napply.library.utils.Log;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 16:19
 */
public class FrescoSampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fresco_sample_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        FrescoImageLoader.getInstance().setImageURI(simpleDraweeView,"http://image.39.net/104/5/663072_1.jpg");
//        simpleDraweeView.setImageURI("http://image.39.net/104/5/663072_1.jpg");
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri("http://image.39.net/104/5/663072_1.jpg")
//                .setOldController(simpleDraweeView.getController())
//                .setControllerListener(new BaseControllerListener<ImageInfo>(){
//                    @Override
//                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                        if (imageInfo == null) {
//                            return;
//                        }
//                        QualityInfo qualityInfo = imageInfo.getQualityInfo();
//                        Log.i("Final image received! " +
//                                        "Size %d x %d" +
//                                "Quality level %d, good enough: %s, full quality: %s",
//                                imageInfo.getWidth(),
//                                imageInfo.getHeight(),
//                                qualityInfo.getQuality(),
//                                qualityInfo.isOfGoodEnoughQuality(),
//                                qualityInfo.isOfFullQuality());
//                    }
//
//                    @Override
//                    public void onFailure(String id, Throwable throwable) {
//                        super.onFailure(id, throwable);
//                    }
//                })
//                .build();
//        simpleDraweeView.setController(controller);

        SimpleDraweeView gifDraweeView = (SimpleDraweeView) findViewById(R.id.gifImage);
//        simpleDraweeView.setImageURI("http://image.39.net/104/5/663072_1.jpg");
//        DraweeController gifController = Fresco.newDraweeControllerBuilder()
//                .setUri("res:///" + R.mipmap.gif)
//                .setOldController(gifDraweeView.getController())
//                .setAutoPlayAnimations(true)
//                .setControllerListener(new BaseControllerListener<ImageInfo>(){
//                    @Override
//                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                        if (imageInfo == null) {
//                            return;
//                        }
//                        QualityInfo qualityInfo = imageInfo.getQualityInfo();
//                        Log.i("Final image received! " +
//                                        "Size %d x %d" +
//                                        "Quality level %d, good enough: %s, full quality: %s",
//                                imageInfo.getWidth(),
//                                imageInfo.getHeight(),
//                                qualityInfo.getQuality(),
//                                qualityInfo.isOfGoodEnoughQuality(),
//                                qualityInfo.isOfFullQuality());
//                    }
//
//                    @Override
//                    public void onFailure(String id, Throwable throwable) {
//                        super.onFailure(id, throwable);
//                    }
//                })
//                .build();
//        gifDraweeView.setController(gifController);
        FrescoImageLoader.getInstance().setController(gifDraweeView,"res:///" + R.mipmap.gif,new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                Log.i("Final image received! " +
                                "Size %d x %d" +
                                "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
            }
        });

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
}

