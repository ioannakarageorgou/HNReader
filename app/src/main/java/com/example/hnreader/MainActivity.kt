package com.example.hnreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hnreader.ui.HNNavHost
import com.example.hnreader.ui.theme.HNReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HNReaderTheme {
                HNNavHost()
            }
        }
    }
}
