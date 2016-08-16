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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.ContextCompatApi24;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.Window;


import net.nym.napply.library.entity.SimpleInfo;
import net.nym.napply.library.recycler.BaseRecyclerAdapter;
import net.nym.napply.library.recycler.RecyclerViewLinearDivider;
import net.nym.napply.library.recycler.SimpleAnimator;
import net.nym.napply.adapter.SimpleAdapter;
import net.nym.napply.library.utils.ContextUtils;
import net.nym.napply.library.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;
    List<SimpleInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.setDebug(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewLinearDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new SimpleAnimator());
        mData = new ArrayList<SimpleInfo>();
        SimpleInfo info = new SimpleInfo();
        info.setName(HeadBottomScrollShowHideBehaviorActivity.class.getSimpleName());
        info.setClazz(HeadBottomScrollShowHideBehaviorActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FollowAppBarBehaviorActivity.class.getSimpleName());
        info.setClazz(FollowAppBarBehaviorActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(HeadScrollSampleActivity.class.getSimpleName());
        info.setClazz(HeadScrollSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FrescoSampleActivity.class.getSimpleName());
        info.setClazz(FrescoSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FrescoListSampleActivity.class.getSimpleName());
        info.setClazz(FrescoListSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(OkHttpRequestSampleActivity.class.getSimpleName());
        info.setClazz(OkHttpRequestSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(SceneTransitionAnimationActivity.class.getSimpleName());
        info.setClazz(SceneTransitionAnimationActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName("");
        mData.add(info);

        mAdapter = new SimpleAdapter(this,mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
//                Snackbar.make(view, "position=" + position, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                SimpleInfo info = new SimpleInfo();
//                info.setName("add");
//                mData.add(position,info);
//                mAdapter.notifyItemInserted(position);
                SimpleInfo info = mData.get(position);
                if (info.getName().equals(SceneTransitionAnimationActivity.class.getSimpleName())){
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this
                            ,new Pair<View, String>(view.findViewById(R.id.text),"text")
                            );
                    ActivityCompat.startActivity(MainActivity.this
                            ,new Intent(MainActivity.this,info.getClazz())
                                    .putExtra("name",info.getName())
                            ,optionsCompat.toBundle());
                }else {

                    startActivity(new Intent(MainActivity.this,info.getClazz()));
                }


            }
        });
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position, long id) {
                mData.remove(position);
                mAdapter.notifyItemRemoved(position);
                return true;
            }
        });
    }

}
