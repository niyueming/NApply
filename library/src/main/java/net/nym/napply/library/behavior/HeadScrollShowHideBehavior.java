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

package net.nym.napply.library.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by niyueming on 16/8/4.
 */
public class HeadScrollShowHideBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean isAnimate;//动画是否在进行
    private int mTouchSlop;
    public HeadScrollShowHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
//        ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (isAnimate){
            return;
        }
        //dy大于0是向上滚动 小于0是向下滚动
        if (dy >= mTouchSlop  && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dy < -mTouchSlop && child.getVisibility() == View.GONE) {
            show(child);
        }
    }

    private void show(final View child) {
        if (child == null){
            return;
        }
        final float translationY = child.getTranslationY();
        //hide -> show
        if (translationY >= 0 || isAnimate) {
            return;
        }
        ViewPropertyAnimatorCompat animator = ViewCompat.animate(child);
        animator.translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        child.setVisibility(View.VISIBLE);
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimate = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        hide(child);
                    }
                }).start();
    }

    private void hide(final View child) {
        if (child == null){
            return;
        }
        final float translationY = child.getTranslationY();
        //show -> hide
        if (translationY < 0 || isAnimate) {
            return;
        }
        ViewPropertyAnimatorCompat animator = ViewCompat.animate(child);
        animator.translationY(-child.getHeight())
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        child.setVisibility(View.GONE);
                        isAnimate = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        show(child);
                    }
                }).start();
    }
}
