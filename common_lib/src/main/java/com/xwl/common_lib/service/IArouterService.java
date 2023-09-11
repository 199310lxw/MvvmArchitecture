package com.xwl.common_lib.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author lxw
 * @date 2022/9/23
 * descripe
 */
public interface IArouterService extends IProvider {
    Context getApplicationContext();
}
