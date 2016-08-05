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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.nym.napply.adapter.FrescoAdapter;
import net.nym.napply.entity.ImageVo;
import net.nym.napply.library.recycler.RecyclerViewGridDivider;
import net.nym.napply.library.recycler.SimpleAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 15:30
 */
public class FrescoListSampleActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FrescoAdapter mAdapter;
    List<ImageVo> mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fresco_list_sample_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new RecyclerViewGridDivider(this));
        mRecyclerView.setItemAnimator(new SimpleAnimator());
        String[] images = {"http://h.hiphotos.baidu.com/image/h%3D200/sign=066ddaa1ae014c08063b2fa53a7b025b/023b5bb5c9ea15ced99ab701b1003af33a87b246.jpg"
                ,"http://g.hiphotos.baidu.com/image/h%3D200/sign=838884f57c899e51678e3d1472a6d990/b999a9014c086e06abe7684305087bf40ad1cb6f.jpg"
                ,"http://img1.imgtn.bdimg.com/it/u=3887720204,1129814829&fm=11&gp=0.jpg"
                ,"http://img3.imgtn.bdimg.com/it/u=3686883058,3663113&fm=11&gp=0.jpg"
                ,"http://img0.imgtn.bdimg.com/it/u=3870427516,84150452&fm=11&gp=0.jpg"
                ,"http://img1.imgtn.bdimg.com/it/u=1192353147,917742328&fm=11&gp=0.jpg"
                ,"http://img1.imgtn.bdimg.com/it/u=1875144327,3687369188&fm=23&gp=0.jpg"
                ,"http://img5.imgtn.bdimg.com/it/u=1768231472,1574793081&fm=11&gp=0.jpg"
                ,"http://b.hiphotos.baidu.com/image/h%3D200/sign=bea54b927e310a55db24d9f487444387/503d269759ee3d6d1c6856aa44166d224f4ade21.jpg"
                ,"http://c.hiphotos.baidu.com/image/h%3D300/sign=5bffe3d59b82d158a4825fb1b00b19d5/0824ab18972bd407bf0cb6c67c899e510eb309c5.jpg"
                ,"http://a.hiphotos.baidu.com/image/h%3D300/sign=da03ef026c63f624035d3f03b744eb32/203fb80e7bec54e71ecd7e25be389b504fc26ae3.jpg"
                ,"http://img1.imgtn.bdimg.com/it/u=2127748542,2235355925&fm=21&gp=0.jpg"
                ,"http://img2.imgtn.bdimg.com/it/u=2414938863,502254713&fm=23&gp=0.jpg"
        };
        mData = new ArrayList<ImageVo>();
        for (int i = 0 ;i < 30;i++){
            ImageVo info = new ImageVo();
            info.setUrl(images[i%images.length]);
            mData.add(info);
        }

        mAdapter = new FrescoAdapter(this,mData);
        mRecyclerView.setAdapter(mAdapter);
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
