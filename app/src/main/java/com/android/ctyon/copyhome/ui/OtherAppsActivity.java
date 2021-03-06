package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.ListviewAdapter;
import com.android.ctyon.copyhome.adapter.data.AppClassName;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OtherAppsActivity extends AppCompatActivity {
    private static final String                   TAG = "OtherAppsActivity";
    private              Context                  mContext;
    private              ListView                 mListView;
    private              ListviewAdapter          mListviewAdapter;
    private              LinkedList<AppClassName> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_list_layout);
        mContext = OtherAppsActivity.this;

        initData();
        mContext = OtherAppsActivity.this;
        mListView = (ListView)findViewById(R.id.other_listview);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>)mData, mContext);
        mListView.setAdapter(mListviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(mData.get(position).getPackageName(), mData.get(position).getClassName());
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                startCallNumber(keyCode - KeyEvent.KEYCODE_0);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void queryAppInfo() {
        Log.d(TAG, "queryAppinfo");
        PackageManager pm = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.GET_RESOLVED_FILTER);


    }

    private void initData() {
        mData = new LinkedList<AppClassName>();
        mData.add(new AppClassName("浏览器", "com.android.browser", "com.android.browser.BrowserActivity"));
        mData.add(new AppClassName("客服中心", "com.android.browser", "com.android.browser.BrowserActivity"));
    }

    private void startCallNumber(int num) {
        Intent intent = new Intent();
        intent.putExtra("number", String.valueOf(num));
        ComponentName componentName = new ComponentName("com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.dialer.CallActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
