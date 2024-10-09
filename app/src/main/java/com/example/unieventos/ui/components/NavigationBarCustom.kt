package com.example.unieventos.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.unieventos.models.ui.BottomNavigationItem

@Composable
fun NavigationBarCustom(
    navController: NavHostController,
    items: List<BottomNavigationItem> = listOf()
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = Modifier.background(Color(0xFF161616)),
        containerColor = Color(0xFF161616),
        contentColor = Color.White
    ){
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                selectedItemIndex = index
                navController.navigate(item.route)
            }, label = {
                Text(text = item.title)
            },
                icon = {
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
