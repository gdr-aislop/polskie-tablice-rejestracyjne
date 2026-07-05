package pl.tablice.lookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import pl.tablice.lookup.ui.PlateSearchScreen
import pl.tablice.lookup.ui.theme.TabliceLookupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TabliceLookupTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PlateSearchScreen()
                }
            }
        }
    }
}
