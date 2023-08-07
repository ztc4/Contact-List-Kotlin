package com.example.contactlist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactlist.R
import com.example.contactlist.data.ContactEvent
import com.example.contactlist.viewModelContact.ContactState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    state: ContactState,
    onEvent: (ContactEvent)->Unit,
    controller: NavController
)

{
    Surface() {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(0.dp,30.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape),)
            }
            TextField(
                value = state.firstName,
                onValueChange = {
                    onEvent(ContactEvent.SetFirstName(it)) },
                placeholder = {
                    Text(text="First name")
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "First Name" )
                },
                modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done

                ),
                )
            TextField(
                value = state.lastName,
                onValueChange = {
                    onEvent(ContactEvent.SetLastName(it)) },
                placeholder = {
                    Text(text="Last name")
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "First Name" )
                },
                modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done

                ),



                )
            TextField(
                value = state.phoneNumber,
                onValueChange = {
                    onEvent(ContactEvent.SetPhoneNumber(it)) },
                placeholder = {
                    Text(text="Phone number")
                },
                leadingIcon = {
                    Icon(Icons.Default.Call, contentDescription = "Person Icon" )
                },
                modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done

                ),


                
            )

        }
        Row(modifier = Modifier
            .fillMaxSize(1f)
            .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom

        ){
            TextButton(onClick = {controller.navigate("home"); onEvent(ContactEvent.CancelContact)},
            modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text="Cancel",  fontSize = 18.sp)

            }
            TextButton(
                onClick = { onEvent(ContactEvent.SaveContact);controller.navigate("home")},
                modifier = Modifier.fillMaxWidth(1f)) {
                Text(text="Save", fontSize = 18.sp)

            }
        }

    }

}