package org.sourcei.xpns.source

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import org.sourcei.xpns.convertors.DateConvertor
import org.sourcei.xpns.convertors.IconConvertor
import org.sourcei.xpns.dao.CategoryDao
import org.sourcei.xpns.dao.TransactionDao
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.TransactionPojo
import org.sourcei.xpns.utils.SingletonHolder


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
@TypeConverters(DateConvertor::class, IconConvertor::class)
abstract class RoomSource : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionsDao(): TransactionDao

    // singleton pattern for using DAO
    companion object : SingletonHolder<RoomSource, Context, String>({ context, name ->
        Room.databaseBuilder(context,
                RoomSource::class.java, "$name.db")
                .allowMainThreadQueries()
                .build()
    })
}