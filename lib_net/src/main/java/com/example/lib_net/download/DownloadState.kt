package com.example.lib_net.download

import java.io.File

/**
 * @author  lxw
 * @date 2023/9/20
 * descripe
 */
sealed class DownloadState {
    data class InProgress(val progress: Int) : DownloadState()
    data class Success(val file: File) : DownloadState()
    data class Error(val throwable: Throwable) : DownloadState()
}
