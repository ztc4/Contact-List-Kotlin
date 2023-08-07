package com.example.contactlist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactlist.data.Contact
import com.example.contactlist.data.ContactDao


@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {
    abstract val dao: ContactDao
}