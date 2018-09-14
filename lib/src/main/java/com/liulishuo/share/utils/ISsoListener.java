package com.liulishuo.share.utils;

import android.support.annotation.CallSuper;

/**
 * @author Kale
 * @date 2018/9/10
 */
public interface ISsoListener {

    @CallSuper
    default void onError(String errorMsg){
        SlUtils.printErr("login or share error:" + errorMsg);
        onComplete();
    }

    @CallSuper
    default void onCancel() {
        SlUtils.printLog("login or share cancel:");
        onComplete();
    }

    default void onComplete() {
        
    }
}