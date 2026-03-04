package com.example.demo.features.home.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var pidData by remember { mutableStateOf("Waiting for Capture...\n\nData yahan print hoga.") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val resultXml = result.data?.getStringExtra("PID_DATA") ?: "No XML returned"
            pidData = resultXml
        } else {
            val data = result.data
            val errorHint = data?.getStringExtra("dnc") ?: "Hardware issue ya user cancel"
            pidData = "Capture Failed! \nResult Code: ${result.resultCode}\nHint: $errorHint"
        }
    }

    fun getPidOptionsXml(type: String): String {
        return when (type) {
            "FINGERPRINT" -> """<PidOptions ver="1.0"><Opts fCount="1" fType="2" iCount="0" pCount="0" format="0" pidVer="2.0" timeout="10000" env="P" wadh="" posh="UNKNOWN"/></PidOptions>"""
            "IRIS" -> """<PidOptions ver="1.0"><Opts fCount="0" iCount="1" iType="0" pCount="0" format="0" pidVer="2.0" timeout="10000" env="P" wadh="" posh="UNKNOWN"/></PidOptions>"""
            "FACE" -> """<PidOptions ver="1.0"><Opts fCount="0" iCount="0" pCount="1" pType="0" format="0" pidVer="2.0" timeout="10000" env="P" wadh="" posh="UNKNOWN"/></PidOptions>"""
            else -> ""
        }
    }

    fun captureBiometric(action: String, scannerType: String) {
        try {
            val xmlOptions = getPidOptionsXml(scannerType)
            val intent = Intent(action)
            intent.putExtra("PID_OPTIONS", xmlOptions)
            launcher.launch(intent)
        } catch (e: Exception) {
            pidData = "Crash: ${e.message}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Biometric Testing", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { captureBiometric("in.gov.uidai.rdservice.fp.CAPTURE", "FINGERPRINT") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capture Morpho/Mantra (FP)")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { captureBiometric("in.gov.uidai.rdservice.iris.CAPTURE", "IRIS") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capture Mantra (Iris)")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { captureBiometric("in.gov.uidai.rdservice.face.CAPTURE", "FACE") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capture Face RD")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = pidData,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
