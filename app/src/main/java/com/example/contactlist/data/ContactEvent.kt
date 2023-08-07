package com.example.contactlist.data

sealed interface ContactEvent{

    object SaveContact: ContactEvent
    object CancelContact: ContactEvent
    data class EditContact(val firstName: String, val lastName: String, val phoneNumber: String, val id: Int): ContactEvent
    data class SetFirstName(val firstName: String): ContactEvent
    data class SetLastName(val lastName: String): ContactEvent
    data class SetPhoneNumber(val phoneNumber: String): ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog: ContactEvent
    data class SortContacts(val sortType: SortType): ContactEvent
    data class DeleteContact(val contact: Contact): ContactEvent

}