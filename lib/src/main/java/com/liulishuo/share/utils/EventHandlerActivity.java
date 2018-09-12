package com.liulishuo.share.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liulishuo.share.ShareLoginLib;

/**
 * Created by echo on 5/19/15.
 * 用来处理微信登录、微信分享的activity。这里真不知道微信非要个activity干嘛，愚蠢的设计!
 * 参考文档: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
 *
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
 */
public class EventHandlerActivity extends Activity {

    public static final String KEY_REQUEST_CODE = "share_login_lib_key_request_code";

    public static final String KEY_RESULT_CODE = "share_login_lib_key_result_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 内存不足杀死后重建时的onCreate()
        if (savedInstanceState != null) {
            ShareLoginLib.printLog("EventHandlerActivity:onCreate(2) intent:" + getIntent());
            handleResp(getIntent());
        } else {
            ShareLoginLib.onActivityCreate(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ShareLoginLib.printLog("EventHandlerActivity:onNewIntent() intent:" + intent);

        handleResp(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            intent.putExtra(KEY_REQUEST_CODE, requestCode);
            intent.putExtra(KEY_RESULT_CODE, resultCode);
        }

        ShareLoginLib.printLog("EventHandlerActivity:onActivityResult() intent:" + intent);

        handleResp(intent);

        finish();
    }

    @Override
    protected void onDestroy() {
        ShareLoginLib.printLog("EventHandlerActivity:onDestroy()");
        ShareLoginLib.destroy();
        super.onDestroy();
    }

    private void handleResp(Intent data) {
        if (ShareLoginLib.getCurPlatform() != null) {
            ShareLoginLib.getCurPlatform().onResponse(this, data);
        } else {
            ShareLoginLib.printLog("ShareLoginLib.curPlatform is null");
        }
    }

    public interface OnCreateListener {

        void onCreate(EventHandlerActivity activity);
    }

}