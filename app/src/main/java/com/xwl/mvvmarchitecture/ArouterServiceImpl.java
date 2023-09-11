package com.xwl.mvvmarchitecture;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xwl.common_lib.service.IArouterService;


/**
 * @author lxw
 * @date 2022/9/23
 * descripe
 */
@Route(path = "/app/arouterServiceImpl")
public class ArouterServiceImpl implements IArouterService {
    @Override
    public Context getApplicationContext() {
        return new App().getContext();
    }

    @Override
    public void init(Context context) {

    }
}
