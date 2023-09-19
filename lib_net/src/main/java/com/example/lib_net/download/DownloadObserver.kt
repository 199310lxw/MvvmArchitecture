package com.example.lib_net.download

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.lib_net.manager.ApiManager
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.KeyConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe
 */
open class DownloadObserver: LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
         if(event == Lifecycle.Event.ON_START) {

         }
    }


}