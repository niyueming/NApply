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

package net.nym.napply.library.recycler;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 11:09
 */
public class SimpleAnimator extends BaseItemAnimator {
    @Override
    protected ViewPropertyAnimatorCompat getAddAnimator(RecyclerView.ViewHolder item) {
        ViewCompat.setTranslationX(item.itemView, -item.itemView.getWidth());
        return ViewCompat.animate(item.itemView).translationX(0);
    }
    @Override
    protected ViewPropertyAnimatorCompat getRemoveAnimator(RecyclerView.ViewHolder item) {
        return ViewCompat.animate(item.itemView).translationX(item.itemView.getWidth());
    }
}
