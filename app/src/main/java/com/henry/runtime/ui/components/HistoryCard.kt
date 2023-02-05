package com.henry.runtime.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Man
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.runtime.ui.history.ExpandableHistoryModel

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    expandableHistoryModel: ExpandableHistoryModel,
    isExpanded: Boolean,
    onExpand: () -> Unit,
) {
    Card(modifier = modifier
        .padding(8.dp)
    ) {
        Column {
            Row {
                Icon(
                    imageVector = Icons.Default.Man,
                    contentDescription = "Running Icon",
                )
                Text(expandableHistoryModel.name)
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = onExpand,
                    content = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop Down",
                        )
                    },
                )
            }

            HistoryCardExpandable(modifier, expandableHistoryModel, isExpanded)
        }
    }
}

@Composable
fun HistoryCardExpandable(
    modifier: Modifier,
    expandableHistoryModel: ExpandableHistoryModel,
    isExpanded: Boolean
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically(
            expandFrom = Alignment.Top,
        ) + fadeIn (),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
        ) + fadeOut(),
    ) {
        Row() {
            Text(expandableHistoryModel.duration.toString())
            Text(expandableHistoryModel.date.toString())
        }
    }
}