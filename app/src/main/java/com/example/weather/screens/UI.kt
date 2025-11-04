package com.example.weather.screens

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.ui.theme.Blue_bg

@Preview(showBackground = true)
@Composable
fun ListItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .padding(horizontal = 10.dp),
        backgroundColor = Blue_bg,
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth().padding(7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column()
            {
                Text(
                    text = "12:00",
                    color = Color.White
                )
                Text(
                    text = "Sunny",
                    color = Color.White
                )
            }
            Text(
                text = "35",
                color = Color.White,
                fontSize = 25.sp
            )
            AsyncImage(
                model = "http://cdn.weatherapi.com/weather/64x64/day/116.png",
                contentDescription = "ui",
                modifier = Modifier.size(35.dp)
            )
        }
    }
}