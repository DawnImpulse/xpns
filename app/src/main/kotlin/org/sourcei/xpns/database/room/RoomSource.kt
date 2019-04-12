package org.sourcei.xpns.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.sourcei.xpns.utils.convertors.DateConvertor
import org.sourcei.xpns.utils.convertors.IconConvertor
import org.sourcei.xpns.utils.convertors.ListConvertor
import org.sourcei.xpns.database.room.dao.CategoryDao
import org.sourcei.xpns.database.room.dao.TransactionDao
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.utils.others.SingletonHolder


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
@Database(entities = [CategoryObject::class, TransactionPojo::class], version = 1)
@TypeConverters(DateConvertor::class, IconConvertor::class, ListConvertor::class)
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