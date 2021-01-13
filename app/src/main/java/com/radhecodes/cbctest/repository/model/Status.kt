package com.radhecodes.cbctest.repository.model

import android.util.Log
import java.lang.ClassCastException

@Suppress("UNCHECKED_CAST")
class Status(val status: StatusType, val data: Any?) {

    fun <T> isInstanceOf(): T? {
        return try {
            data as T
        } catch (e: ClassCastException) {
            Log.i("MYA_INFO", "Casting of $data failed")
            null
        }
    }
}