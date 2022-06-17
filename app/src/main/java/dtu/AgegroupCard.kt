package dtu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dtu.engtech.DB3_3.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController

@Composable
fun AgegroupCard(
    agegroup: Agegroup,
    modifier: Modifier = Modifier,
) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Text(
                text = agegroup.age.toString()
            )
        }
    }
}

@Composable
fun StartsideView(navController: NavController) {
    Column(modifier = Modifier.padding(5.dp)) {
        Image(
            painter = painterResource(R.drawable.startside),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { navController.navigate("Agegroup") }
                .clip(shape = RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Button(
            onClick = { navController.navigate("Agegroup") },
            modifier = Modifier.padding(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(text = "Start skattejagten")
        }
    }
}

@Composable
fun AgegroupView(navController: NavController) {
    Image(painter = painterResource(R.drawable.alder), contentDescription = null,
        modifier = Modifier.padding(4.dp)
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable { navController.navigate("Agegroup") }
        .clip(shape = RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop)
    Column(modifier = Modifier.padding(10.dp)) {
        Row(){
            Text(text = "Vælg din alder")
        }
        Row() {

            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Yellow,
                contentColor = Color.Black
            ),
                onClick = { navController.navigate("GameBarn") }) {
                Text(text = "3 til 5 år")
            }
        }
        Row(modifier = Modifier) {
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green,
                contentColor = Color.Black
            ),
                onClick = { navController.navigate("GameBarn") }) {
                Text(text = "6 til 9 år")
            }
        }
        Row() {
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.Black
            ),
                onClick = { navController.navigate("GameTeen") }) {
                Text(text = "10 til 13 år")
            }
        }
        Row(modifier = Modifier) {
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.Black
            ),
                onClick = { navController.navigate("GameTeen") }) {
                Text(text = "14 til 17 år")
            }
        }
    } 
}

    @Composable
        fun GameViewBarn(navController: NavController) {
        Column(modifier = Modifier.padding(5.dp)) {
            Image(painter = painterResource(R.drawable.spilbarn1),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { navController.navigate("Game") }
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Button(
                onClick = { navController.navigate("GameBarn") },
                modifier = Modifier.padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(text = "Videre")

            }
        }
    }

        @Composable
        fun GameViewTeen(navController: NavController) {
            Column(modifier = Modifier.padding(5.dp)) {
                Image(painter = painterResource(R.drawable.spilteen),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { navController.navigate("Game") }
                        .clip(shape = RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = { navController.navigate("GameTeen") },
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(text = "Videre")

                }
            }
        }
