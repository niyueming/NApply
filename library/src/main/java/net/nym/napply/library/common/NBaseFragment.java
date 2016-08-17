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

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author niyueming
 * @date 2016-08-16
 * @time 10:37
 */
public class NBaseFragment extends Fragment {

    protected ViewGroup mContainer;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isAdded()){
            if(getUserVisibleHint()) {
                onVisible();
            } else {
                onInvisible();
            }

        }
    }


    public <T extends View> T findViewById(@IdRes int id){
        if (mContainer == null){
            return null;
        }

        View view = mContainer.findViewById(id);
        return view == null ? null : (T)view;
    }
    /**
     * 可见
     */
    protected void onVisible() {
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }
}
