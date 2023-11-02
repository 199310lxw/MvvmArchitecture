package com.xwl.mvvmarchitecture;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xwl.common_lib.constants.RoutMap;
import com.xwl.common_lib.service.IContextService;


/**
 * @author lxw
 * @date 2022/9/23
 * descripe
 */
@Route(path = RoutMap.APP_SERVICE_CONTEXT)
public class AppService implements IContextService {
    @Override
    public Context getApplicationContext() {
        return App.Companion.getApplicationContext();
    }

    @Override
    public void init(Context context) {

    }
}
