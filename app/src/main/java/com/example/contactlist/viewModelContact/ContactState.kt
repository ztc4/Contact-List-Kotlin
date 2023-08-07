package com.example.contactlist.viewModelContact

import com.example.contactlist.data.Contact
import com.example.contactlist.data.ContactEvent
import com.example.contactlist.data.SortType

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME,

)
