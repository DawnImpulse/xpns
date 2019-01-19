package org.sourcei.xpns.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.sourcei.xpns.convertors.DateConvertor
import org.sourcei.xpns.convertors.IconConvertor
import org.sourcei.xpns.convertors.ListConvertor
import org.sourcei.xpns.dao.CategoryDao
import org.sourcei.xpns.dao.TransactionDao
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.TransactionPojo
import org.sourcei.xpns.utils.SingletonHolder


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-12 by Saksham
 * @tnote Updates :
 */
@Database(entities = [CategoryPojo::class, TransactionPojo::class], version = 1)
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