package com.henry.runtime.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SimpleButton(onClick: () -> Unit, text: String, modifier: Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun CircleButton(onClick: () -> Unit, icon: ImageVector, description: String) {
    IconButton (
        onClick = onClick,
    )  {
        Icon(
            imageVector = icon,
            contentDescription = description,
        )
    }
}