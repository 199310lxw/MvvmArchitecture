package com.xwl.common_lib.ext

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
/**
 * String转RMB格式输出
 */
fun String.toRMB(): String {
    val formatter = DecimalFormat("¥#,##0.00")
    val number = BigDecimal(this)
    return formatter.format(number)
}

fun String.toRMBWithOutUnit(): String {
    val formatter = DecimalFormat("#,##0.00")
    val number = BigDecimal(this)
    return formatter.format(number)
}

/**
 * 判断String是不是手机号
 */
fun String.isPhoneNumber(): Boolean {
    val regex = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}