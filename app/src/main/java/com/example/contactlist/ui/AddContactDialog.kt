package com.example.contactlist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactlist.data.ContactEvent
import com.example.contactlist.viewModelContact.ContactState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog (
    state: ContactState,
    onEvent: (ContactEvent)->Unit,
    modifier: Modifier = Modifier

    ){
    AlertDialog(modifier = modifier,onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        title = { Text(text = "Add contact") }, 
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.firstName,
                    onValueChange = {
                    onEvent(ContactEvent.SetFirstName(it)) },
                    placeholder = {
                        Text(text="First name")
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(ContactEvent.SetLastName(it)) },
                    placeholder = {
                        Text(text="Last name")
                    }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetPhoneNumber(it)) },
                    placeholder = {
                        Text(text="Phone number")
                    }
                )

            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Button(onClick = { onEvent(ContactEvent.SaveContact)}) {
                    Text(text="Save")
                    
                }
            }
        } )

    }


