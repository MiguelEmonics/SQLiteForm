package com.example.sqliteform.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object UserContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PHONE = "phone"
        const val COLUMN_NAME_ADDRESS = "address"
        const val COLUMN_NAME_CITY = "city"
        const val COLUMN_NAME_STATE = "state"
    }

    private const val SQL_CREATE_ENTRIES = "CREATE TABLE ${UserEntry.TABLE_NAME} ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
            "${UserEntry.COLUMN_NAME_USERNAME} TEXT,"+
            "${UserEntry.COLUMN_NAME_PASSWORD} TEXT,"+
            "${UserEntry.COLUMN_NAME_EMAIL} TEXT,"+
            "${UserEntry.COLUMN_NAME_PHONE} TEXT,"+
            "${UserEntry.COLUMN_NAME_ADDRESS} TEXT,"+
            "${UserEntry.COLUMN_NAME_CITY} TEXT,"+
            "${UserEntry.COLUMN_NAME_STATE} TEXT)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"

    class UserContractDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "UserContract.db"
        }
    }
}