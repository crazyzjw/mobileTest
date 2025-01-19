package com.demo.myapplication.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.demo.myapplication.common.adapter.ViewPagerAdapter
import com.demo.myapplication.databinding.ActivityMainBinding
import com.demo.myapplication.service.DataService
import com.demo.myapplication.utils.LogUtil
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragments= mutableListOf<Fragment>()
    private val title= listOf("tab1","tab2","tab3")
    private var dataService:DataService?=null
    private var dataBinder: DataService.DataBinder? = null

    private var isServiceBound=false
    private val myOnPageChangeCallback=MyOnPageChangeCallback()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindDataService()
        initTab()
        initViewPage()
        binding.let {
            TabLayoutMediator(it.tabMain, it.viewpageMain) { tab, position ->
                tab.text = title[position]
            }.attach()

        }

    }
    override fun onResume() {
        super.onResume()
        fetchDataFromService()

    }

    private fun bindDataService() {
        val intent=Intent(this,DataService::class.java)
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
    }

    private fun initViewPage() {
        binding.viewpageMain.let {
            it.adapter= ViewPagerAdapter(this, fragments)
            it.registerOnPageChangeCallback(myOnPageChangeCallback)
        }
    }

    private fun initTab(){
        title.forEach {
            fragments.add(HomeFragment.newInstance(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        binding.viewpageMain.unregisterOnPageChangeCallback(myOnPageChangeCallback)
    }

    private fun fetchDataFromService(){
        if (isServiceBound){
            dataService?.provider {
                println("Fetched Data: $it")
            }
        }
    }
    private inner class MyOnPageChangeCallback:OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            LogUtil.d(position)
        }
    }

    private val serviceConnection=object :ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            isServiceBound=true
            dataBinder = (service as DataService.DataBinder)
            dataService = dataBinder?.getService()
            fetchDataFromService()
            Toast.makeText(this@MainActivity, "Service Connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceBound=false
            dataService=null
        }

    }
}