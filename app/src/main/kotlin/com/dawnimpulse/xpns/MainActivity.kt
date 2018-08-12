package com.dawnimpulse.xpns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.widget.toast
import com.dawnimpulse.xpns.pojo.Transactions
import com.dawnimpulse.xpns.utils.MainDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var mainDatabase: MainDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainDatabase = MainDatabase.getInstance(applicationContext)
        mainDatabase
                .transactionsDao()
                .insert(Transactions(1))
        toast(
                mainDatabase
                        .transactionsDao()
                        .fetchAll()[0]
                        .id
                        .toString()
        )

    }
}
