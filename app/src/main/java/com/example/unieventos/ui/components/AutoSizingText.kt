
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineLarge.copy(fontSize = 50.sp),
    modifier: Modifier = Modifier,
    color: Color = style.color
) {
    var resizedTextStyle by remember { mutableStateOf(style) }
    var shouldDraw by remember { mutableStateOf(false) }
    var initialLayoutComplete by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth && resizedTextStyle.fontSize > 12.sp) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.80
                )
                shouldDraw = false
            } else {
                shouldDraw = true
                initialLayoutComplete = true
            }
        }
    )
}

