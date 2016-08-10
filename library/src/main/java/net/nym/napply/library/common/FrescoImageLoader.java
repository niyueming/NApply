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

import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * @author niyueming
 * @date 2016-08-08
 * @time 13:28
 */
public class FrescoImageLoader {
    private static final String TAG = FrescoImageLoader.class.getSimpleName();
    private static FrescoImageLoader frescoImageLoader;
    private FrescoImageLoader(){
        ;
    }

    public static FrescoImageLoader getInstance(){
        if (frescoImageLoader == null){
            synchronized (FrescoImageLoader.class){
                if (frescoImageLoader == null){
                    frescoImageLoader = new FrescoImageLoader();
                }
            }
        }
        return frescoImageLoader;
    }

    public void setImageURI(@NonNull SimpleDraweeView draweeView, @Nullable String uri){
        draweeView.setImageURI(uri);
    }

    public void setController(@NonNull SimpleDraweeView draweeView, @Nullable String uri){
       setController(draweeView,uri,null);
    }

    public void setController(@NonNull SimpleDraweeView draweeView, @Nullable String uri,@Nullable ControllerListener<ImageInfo> controllerListener){
        DraweeController draweeController =  initControllerBuilder()
                .setOldController(draweeView.getController())
                .setUri(uri)
                .setControllerListener(controllerListener)
                .build();
        draweeView.setController(draweeController);
    }

    public void setImageRequest(@NonNull SimpleDraweeView draweeView, @Nullable ImageRequest imageRequest){
        setImageRequest(draweeView,imageRequest,null);
    }

    public void setImageRequest(@NonNull SimpleDraweeView draweeView, @Nullable ImageRequest imageRequest, @Nullable ControllerListener<ImageInfo> controllerListener){
        DraweeController draweeController =  initControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(imageRequest)
                .setControllerListener(controllerListener)
                .build();
        draweeView.setController(draweeController);
    }

    public void setImageRequest(@NonNull SimpleDraweeView draweeView, @Nullable String uri){
        setImageRequest(draweeView,uri,null);
    }

    public void setImageRequest(@NonNull SimpleDraweeView draweeView, @Nullable String uri, @Nullable ControllerListener<ImageInfo> controllerListener){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .build();
        setImageRequest(draweeView,imageRequest,controllerListener);
    }

    public ControllerListener<ImageInfo> getViewSizeControllerListener(@NonNull final SimpleDraweeView draweeView){
        return new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }
                updateViewSize(draweeView,imageInfo,imageInfo.getWidth());
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
                updateViewSize(draweeView,imageInfo,imageInfo.getWidth());
            }
        };
    }

    public static ControllerListener<ImageInfo> getViewSizeControllerListener(@NonNull final SimpleDraweeView draweeView,final int width){
        return new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }
                updateViewSize(draweeView,imageInfo,width);
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
                updateViewSize(draweeView,imageInfo,width);
            }
        };
    }

    private static void updateViewSize(@NonNull SimpleDraweeView draweeView, @Nullable ImageInfo imageInfo,int width) {
        if (imageInfo != null) {
            draweeView.getLayoutParams().width = width;
            draweeView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            draweeView.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }

    /**
     *类型	                     SCHEME	                     示例
     远程图片	                  http://, https://	          HttpURLConnection 或者参考 使用其他网络加载方案
     本地文件	                  file://	                  FileInputStream
     Content provider	        content://	                ContentResolver
     asset目录下的资源	        asset://	                AssetManager
     res目录下的资源	            res://	                    Resources.openRawResource
     Uri中指定图片数据	         data:mime/type;base64;	     数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
     */
    private ImageRequestBuilder initImageRequestBuilder(Uri uri){
        return ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(true)
//                .setImageDecodeOptions()
//                .setPostprocessor()           //后处理器(修改图片)
//                .setResizeOptions()       //图片缩放
                ;
    }


    private PipelineDraweeControllerBuilder initControllerBuilder(){
        return Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .setRetainImageOnFailure(true)
                ;
    }

    /**
     *对于同一个View，请不要多次调用setHierarchy，即使这个View是可回收的。创建 DraweeHierarchy 的较为耗时的一个过程，应该多次利用。
     *注意：一个DraweeHierarchy 是不可以被多个 View 共用的！
     *如果要改变所要显示的图片可使用setController 或者 setImageURI。
     */
    private GenericDraweeHierarchyBuilder initGenericDraweeHierarchyBuilder(Resources resources){
        return new GenericDraweeHierarchyBuilder(resources)
                .setFadeDuration(300)
//                .setProgressBarImage()    //进度条
                ;
    }
}
