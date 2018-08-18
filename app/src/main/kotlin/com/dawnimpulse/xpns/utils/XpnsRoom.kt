package com.dawnimpulse.xpns.utils

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.dawnimpulse.xpns.convertors.DateConvertor
import com.dawnimpulse.xpns.dao.CategoryDao
import com.dawnimpulse.xpns.dao.TransactionDao
import com.dawnimpulse.xpns.pojo.CategoryPojo
import com.dawnimpulse.xpns.pojo.TransactionPojo


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
@Database(entities = [CategoryPojo::class, TransactionPojo::class], version = 1)
@TypeConverters(DateConvertor::class)
abstract class XpnsRoom : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionsDao(): TransactionDao

    // singleton pattern for using DAO
    companion object : SingletonHolder<XpnsRoom, Context, String>({ context, name ->
        Room.databaseBuilder(context,
                XpnsRoom::class.java, "$name.db")
                .allowMainThreadQueries()
                .build()
    })
}