package com.dawnimpulse.xpns.utils

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.dawnimpulse.xpns.dao.TransactionsDao
import com.dawnimpulse.xpns.pojo.Transactions


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
@Database(entities = [Transactions::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao

    // singleton pattern for using DAO
    companion object : SingletonHolder<MainDatabase, Context>({
        Room.databaseBuilder(it,
                MainDatabase::class.java, "Main.db")
                .allowMainThreadQueries()
                .build()
    })
}