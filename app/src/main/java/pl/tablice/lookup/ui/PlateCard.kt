package pl.tablice.lookup.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.tablice.lookup.data.PlateEntry
import pl.tablice.lookup.ui.theme.voivodeshipContainerColor
import pl.tablice.lookup.ui.theme.voivodeshipContentColor

@Composable
fun PlateCard(entry: PlateEntry, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.kod,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = entry.nazwaJednostki,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            val containerColor = voivodeshipContainerColor(entry.wojewodztwo)
            Surface(
                shape = RoundedCornerShape(50),
                color = containerColor
            ) {
                Text(
                    text = entry.wojewodztwo,
                    style = MaterialTheme.typography.labelMedium,
                    color = voivodeshipContentColor(containerColor),
                    modifier = Modifier.padding(PaddingValues(horizontal = 12.dp, vertical = 6.dp))
                )
            }
        }
    }
}
