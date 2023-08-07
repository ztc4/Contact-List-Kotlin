package com.example.contactlist.viewModelContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.Contact
import com.example.contactlist.data.ContactDao
import com.example.contactlist.data.ContactEvent
import com.example.contactlist.data.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val dao: ContactDao
): ViewModel() {

    private val _state = MutableStateFlow(ContactState())

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _editStateId = MutableStateFlow(0)

    private val _contacts = _sortType
        .flatMapLatest { sortType->
            when(sortType){
                SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
                SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
                SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state,_sortType,_contacts){ state,sortType,contacts ->
        state.copy(
            contacts = contacts,sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


//    fun changeEditId(id: Int ){
//        _editStateId.value = id
//        val contact = _contacts.filter {id == _editStateId.value }
//        _state.update{it.copy(
//            firstName = contact.firstName
//        )
//
//
//
//    }

    fun onEvent(event: ContactEvent){
        when(event){
            is ContactEvent.DeleteContact -> {

                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update{it.copy(
                    isAddingContact = false
                )}
            }
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()){
                    return
                }
                else{
                    var contact: Contact;
                    if (_editStateId.value == 0) {
                        contact = Contact(
                            firstName = firstName, lastName = lastName, phoneNumber = phoneNumber
                        )
                    } else {
                        contact = Contact(
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                            id = _editStateId.value
                        )
                    }
                    viewModelScope.launch {
                        dao.upsertContact(contact)
                    }
                    _state.update {
                        it.copy(
                            isAddingContact = false,
                            firstName = "", lastName = "", phoneNumber = ""
                        )
                    }
                    _editStateId.value = 0
                }

            }
            ContactEvent.CancelContact ->{

                _state.update{it.copy(
                    isAddingContact = false,
                    firstName = "",lastName = "",phoneNumber = ""
                )}
                _editStateId.value = 0


            }
            is ContactEvent.EditContact -> {
                _editStateId.value = event.id

                _state.update{it.copy(
                    lastName  = event.lastName,
                    phoneNumber = event.phoneNumber,
                    firstName = event.firstName
                )}



            }
            is ContactEvent.SetFirstName -> {
                _state.update{it.copy(
                    firstName = event.firstName
                )}
            }
            is ContactEvent.SetLastName -> {
                _state.update{it.copy(
                    lastName  = event.lastName
                )}
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update{it.copy(
                    phoneNumber = event.phoneNumber
                )}
            }
            ContactEvent.ShowDialog -> {
                _state.update{it.copy(
                    isAddingContact = true
                )}
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }

}