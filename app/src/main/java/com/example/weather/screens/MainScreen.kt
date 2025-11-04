package com.example.weather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.data.WeatherModel
import com.example.weather.ui.theme.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

@Composable
fun MainScreen(daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>, onClickSync: () -> Unit, onClickSearch: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.clouds),
        contentDescription = "clouds",
        modifier = Modifier
            .fillMaxSize()
            .alpha(1f),
        contentScale = ContentScale.Crop

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        MainCard(currentDay,onClickSync,onClickSearch)
        Spacer(modifier = Modifier.height(8.dp))
        TabLayout(daysList,currentDay)
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun MainCard(currentDay: MutableState<WeatherModel>, onClickSync: () -> Unit, onClickSearch: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue_bg
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = currentDay.value.time,
                    fontSize = 15.sp,
                    color = Color.White
                )

                AsyncImage(
                    model = "https:"+ currentDay.value.icon,
                    contentDescription = "sign",
                    modifier = Modifier.size(35.dp)
                )

            }

            Text(
                text = currentDay.value.city,
                fontSize = 30.sp,
                color = Color.White
            )

            Text(
                text = if(currentDay.value.currentTemp.isNotEmpty())
                    currentDay.value.currentTemp + "°C"
                else currentDay.value.minTemp + "/" + currentDay.value.maxTemp,
                fontSize = 50.sp,
                color = Color.White
            )

            Text(
                text = currentDay.value.condition,
                fontSize = 15.sp,
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                IconButton(onClick = {
                    onClickSearch.invoke()
                })
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "im3",
                        tint = Color.White
                    )
                }

                Text(
                    text = if(currentDay.value.currentTemp.isEmpty())
                        ""
                    else currentDay.value.minTemp + "/" + currentDay.value.maxTemp,
                    fontSize = 15.sp,
                    color = Color.White
                )

                IconButton(onClick = {
                        onClickSync.invoke()
                })
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_sync),
                        contentDescription = "im3",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {

    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState(pageCount = { tabList.size })
    val tabIndex = pagerState.currentPage
    val corroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.clip(RoundedCornerShape(10.dp)).fillMaxHeight(0.93f)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Blue_bg,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.customPagerTabIndicatorOffset(pagerState, tabPositions),
                    color = none
                    // если убрать бесцевтность то тогда он будет заполнять все пространство
                    // нужно убрать черную полосу
                )
            }
        ) {
            tabList.forEachIndexed { index, text ->
                val isSelected = tabIndex == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        corroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = text,
                            color = if (isSelected) Color.White else Color.LightGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { page ->
            val list = when(page){
                0 -> getWeatherByHours(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }
            MainList(list, currentDay)

        }
    }
}

private fun getWeatherByHours(hours: String):List<WeatherModel>{
    if(hours.isEmpty())return listOf()

    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()

    for(i in 0 until hoursArray.length()){
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                "",
                item.getString("time"),
                item.getString("temp_c"),
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""

            )
        )
    }
    return list
}


fun Modifier.customPagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>
): Modifier = layout { measurable, constraints ->
    val currentPage = minOf(pagerState.currentPage, tabPositions.size - 1)
    val currentTab = tabPositions[currentPage]
    val placeable = measurable.measure(constraints)

    layout(constraints.maxWidth, placeable.height) {
        placeable.place(
            x = currentTab.left.roundToPx(),
            y = 0
        )
    }
}