package com.demo.myapplication.service

import android.content.Context
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

class DataManager(private val context: Context) {
    private val cacheFileName = "booking_cache.json"
    private val cacheFile = getCacheFile()
    private val cacheExpirationTime = 5 * 60 * 1000L //缓存有效:5 minutes
    private val mockJsonFile = "booking.json"
    private val lock = Mutex()

    private fun getCacheFile(): File {
        val cacheFile = File(context.cacheDir, cacheFileName)
        val isExists = cacheFile.exists()
        if (isExists) {
            return cacheFile
        } else {
            cacheFile.createNewFile()
            return cacheFile
        }
    }

    /**
     * 模拟API数据提供方
     */
    private fun fetchFromApi(): String {
        return try {
            context.assets.open(mockJsonFile).bufferedReader(charset = Charsets.UTF_8).readText()
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch data from API")
        }
    }

    /**
     *     从内存中获取数据
     *
     */
    private fun getDataFromCached(): String? {
        return if (cacheFile.exists()) {
            val lastModified = cacheFile.lastModified()
            if (System.currentTimeMillis() - lastModified < cacheExpirationTime) {
                cacheFile.readText()//正常读取缓存
            } else {
                null//缓存过期
            }
        } else {
            null//没缓存
        }

    }

    private suspend fun saveCache(date: String) {
        lock.withLock {
            cacheFile.writeText(date)
            cacheFile.setLastModified(System.currentTimeMillis())
        }
    }

    /**
     * 自动刷新+对完提供数据
     */
    suspend fun provider(): String {
        val cacheData = getDataFromCached()
        return if (cacheData.isNullOrEmpty()) {
            val newData = fetchFromApi()
            saveCache(newData)
            newData
        } else {
            cacheData
        }
    }
}