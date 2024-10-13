package nvdhai2003.fpl.asm_ph57651.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar() {
    CircularProgressIndicator(
        progress = 0.75f,
        color = Color(0xFFFE724C),
        strokeWidth = 4.dp,
    )
}

