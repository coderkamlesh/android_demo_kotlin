package com.example.demo.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ReportsScreen() {
    // 1. Saari states ekdum top pe (State Hoisting)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 2. Scroll state ko yaad rakhne ke liye variable
    val scrollState = rememberScrollState()
//    LazyColumn()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp) // Thoda side padding add kiya clean look ke liye
            .verticalScroll(scrollState), // 3. YAHAN SE SCROLLABLE BANA HAI
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Thoda top margin scroll view me acha lagta hai
        Surface(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 4.dp
        ) {
            Text("Main ek simple Surface hu (Raw Paper)", modifier = Modifier.padding(16.dp))
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text("Filled Card - Bina shadow wala modern look", modifier = Modifier.padding(16.dp))
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Text("Elevated Card - MUI ke Paper jaisa shadow ke saath", modifier = Modifier.padding(16.dp))
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Text("Outlined Card - Clean border wala paper", modifier = Modifier.padding(16.dp))
        }

        TextField(
            value = name,
            onValueChange = { newText -> name = newText },
            label = { Text("Tera Naam") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            placeholder = { Text("example@gmail.com") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Button(
            onClick = {
                Log.d("FormSubmit", "Name is: $name")
                Log.d("FormSubmit", "Email is: $email")
                Log.d("FormSubmit", "Password is: $password")
            },
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        ) {
            Text("Submit & Print to Terminal")
        }

        Button(
            onClick = {
                Toast.makeText(context, "Mera naam $name hai aur email $email", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Show Toast Pop-up")
        }

        Button(
            onClick = {
                println("====== FORM KA DATA ======")
                println("Name: $name")
                println("Email: $email")
                println("Password: $password")
                println("==========================")
            },
            // Thoda bottom padding taaki last button ekdum screen ke edge pe na chipke
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp)
        ) {
            Text("Print to Run Tab")
        }
    }
}