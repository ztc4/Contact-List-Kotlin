package com.example.contactlist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.contactlist.R
import com.example.contactlist.data.ContactEvent
import com.example.contactlist.data.SortType
import com.example.contactlist.viewModelContact.ContactState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    state: ContactState,
    onEvent :(ContactEvent)->Unit,
    controller: NavController
){
    Scaffold(floatingActionButton ={
        FloatingActionButton(onClick = { controller.navigate("add")}) {
            Icon(imageVector = Icons.Default.Add, contentDescription ="Add Contact" )
        }
    }, modifier = Modifier.padding(16.dp)
    ) {padding->

        LazyColumn(
            contentPadding = padding,
        modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.values().forEach {sortType ->
                        Row(modifier = Modifier.clickable { onEvent(ContactEvent.SortContacts(sortType)) }, 
                            verticalAlignment = CenterVertically){
                            RadioButton(selected = state.sortType == sortType, onClick = {
                                onEvent(ContactEvent.SortContacts(sortType))

                            })
                            Text(text = sortType.name)

                        }
                    }

                }
            }
            items(state.contacts){contact->
                ElevatedCard() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(
                                    ContactEvent.EditContact(
                                        contact.firstName,
                                        contact.lastName,
                                        contact.phoneNumber,
                                        contact.id
                                    )
                                );controller.navigate("add")
                            }
                            .padding(10.dp),
                        verticalAlignment = CenterVertically
                    ) {
                       Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Center){
                           Image(
                               painter = painterResource(R.drawable.ic_launcher_background),
                               contentDescription = null,
                               modifier = Modifier.clip(CircleShape).size(30.dp),)
                       }
                        Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(40.dp,0.dp,0.dp,0.dp), verticalArrangement = Arrangement.Center ) {
                            Text(text = "${contact.firstName}",
                                fontSize = 20.sp)



                        }
                        IconButton(onClick = { onEvent(ContactEvent.DeleteContact(contact))}, modifier = Modifier) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete contact")

                        }

                    }

                }

            }

        }

    }

}