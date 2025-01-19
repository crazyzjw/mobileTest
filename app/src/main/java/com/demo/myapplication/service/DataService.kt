package com.demo.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 通用持久化数据服务
 */
class DataService:Service(){
    private val binder=DataBinder()
    private val dataManager by lazy {
        DataManager(this)
    }
    private val mainScope= MainScope()

    inner class DataBinder:Binder(){
        fun getService():DataService=this@DataService
    }
    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mainScope.cancel()
        return super.onUnbind(intent)

    }
    fun provider(callback:(String?)->Unit){
        mainScope.launch {
            val data=dataManager.provider()
            callback.invoke(data)
        }
    }
}
