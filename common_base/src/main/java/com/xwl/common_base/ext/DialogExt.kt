package com.xwl.common_base.ext

import androidx.fragment.app.FragmentActivity
import com.xwl.common_base.dialog.MessageDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


suspend fun showDialogAction(activity: FragmentActivity, content: String) =
    suspendCancellableCoroutine { continuation ->
        MessageDialog.Builder(activity)
            .setContent(content)
            .setOnCancelClickListener { continuation.resume(Unit) }
            .setOnConfirmClickListener { continuation.resume(Unit) }
            .show()
    }
