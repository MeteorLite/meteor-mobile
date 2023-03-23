package meteor.ui.composables.toolbar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import meteor.ui.preferences.background
import meteor.ui.preferences.uiColor

class ToolbarButton(
    var name: String, var icon: ImageVector?, var imageResource: Int? = null, var iconColor: MutableState<Color>? = uiColor,
    var backgroundColor: MutableState<Color> = mutableStateOf(background),
    var description: String? = "", var alignment: Alignment = Alignment.TopCenter,
    var bottom: Boolean = false, var onClick: () -> Unit,
    var position: Int = 999
) {

    //Required for java access
    constructor(
        name: String, imageResource: Int,
        description: String, alignment: Alignment,
        bottom: Boolean, onClick: () -> Unit
    ) :
            this(
                name,
                icon = null,
                imageResource = imageResource,
                description = description,
                alignment = alignment,
                bottom = bottom,
                onClick = onClick,
                iconColor = null
            )

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun CreateComponent() {
        Box(
                modifier = Modifier.padding(vertical = 3.dp).clip(RoundedCornerShape(size = 7.dp)).background(background).width(35.dp)
                        .height(35.dp),
                contentAlignment = alignment,
        ) {
            IconButton(
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.Center)
            ) {
                if (icon != null)
                    Icon(
                            icon!!,
                            contentDescription = description,
                            tint = iconColor?.value ?: uiColor.value,
                    )
                else if (imageResource != null)
                    Image(
                            painter = painterResource(imageResource!!),
                            contentDescription = description,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(uiColor.value, BlendMode.Modulate)
                    )
            }
        }
        Spacer(
                Modifier.height(10.dp)
                        .background(backgroundColor.value)
        )
    }
}