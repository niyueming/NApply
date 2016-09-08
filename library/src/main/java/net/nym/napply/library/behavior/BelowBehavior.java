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
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import net.nym.napply.library.R;
import net.nym.napply.library.utils.Log;

/**
 * @author niyueming
 * @date 2016-09-07
 * @time 17:57
 */
public class BelowBehavior extends CoordinatorLayout.Behavior<View>  {

    private int targetId;
    public BelowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BelowBehavior);
        targetId = a.getResourceId(R.styleable.BelowBehavior_belowTo,-1);
//        for (int i = 0; i < a.getIndexCount(); i++) {
//            int attr = a.getIndex(i);
//            if(a.getIndex(i) == R.styleable.BelowBehavior_target){
//                targetId = a.getResourceId(attr, -1);
//            }
//        }
        a.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == targetId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY()+dependency.getHeight());
//        ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop()));
        return true;
    }
}
