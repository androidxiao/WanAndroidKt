package com.black.multi.videosample.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by wei.
 * Date: 2020/6/2 10:03
 * Desc:
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()