package com.example.weather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R

import com.example.weather.ui.theme.*

@Composable
@Preview(showBackground = true)
fun MainScreen() {
    Image(painter = painterResource(id = R.drawable.clouds),
        contentDescription = "clouds",
        modifier = Modifier.fillMaxSize().alpha(1f),
        contentScale = ContentScale.Crop

        )
    Column(
        modifier = Modifier.fillMaxSize().padding(5.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        MainCard()
        Spacer(modifier = Modifier.height(8.dp))
        TabLayout()
    }
}

@Composable
fun MainCard(){
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
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = "20 Jun 2025 13:00",
                    fontSize = 15.sp,
                    color = Color.White
                )

                AsyncImage(
                    model = "http://cdn.weatherapi.com/weather/64x64/day/116.png",
                    contentDescription = "sign",
                    modifier = Modifier.size(35.dp)
                )

            }

            Text(
                text = "London",
                fontSize = 30.sp,
                color = Color.White
            )

            Text(
                text = "23°C",
                fontSize = 50.sp,
                color = Color.White
            )

            Text(
                text = "Cloudy",
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

                })
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "im3",
                        tint = Color.White
                    )
                }

                Text(
                    text = "23°C - 12°C",
                    fontSize = 15.sp,
                    color = Color.White
                )

                IconButton(onClick = {

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
fun TabLayout(){

    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.clip(RoundedCornerShape(10.dp))
    )
    {
        TabRow(
            selectedTabIndex = 0,
            containerColor = Blue_bg,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[0])
                )
            }
        ){
            tabList.forEachIndexed{
                index, text ->
                Tab(
                    selected = false,
                    onClick = {

                },
                    text = {
                    Text(text = text)
                })
            }
        }
    }
}