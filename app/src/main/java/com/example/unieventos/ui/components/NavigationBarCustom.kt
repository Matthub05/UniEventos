package com.example.unieventos.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.unieventos.models.BottomNavigationItem

@Composable
fun NavigationBarCustom(
    items: List<BottomNavigationItem> = listOf()
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                selectedItemIndex = index
                // navController.navigate(item.title)
            }, label = {
                Text(text = item.title)
            }, icon = {
                BadgedBox(badge = {
                    if (item.badgeCount != null) {
                        Badge {
                            Text(text = item.badgeCount.toString())
                        }
                    } else if (item.hasNews) {
                        Badge()
                    }

                }) {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon, contentDescription = item.title
                    )

                }
            })
        }
    }
}
