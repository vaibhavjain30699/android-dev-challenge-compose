/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                ComposeNavigation()
            }
        }
    }
}

@Composable
fun ComposeNavigation() {

    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = "puppiesList"
    ) {
        composable("puppiesList") {
            MyApp(navController = navController)
        }
        composable(
            "puppyDetails/{name}/{index}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("index") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            backStackEntry?.arguments?.getString("name")?.let {
                PuppyDetails(
                    name = it,
                    index = backStackEntry.arguments!!.getInt("index")
                )
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(names: List<String> = List(1000) { "Puppy $it" }, navController: NavController) {

    val pics: List<Int> =
        listOf(R.mipmap.puppy_example1, R.mipmap.puppy_example2, R.mipmap.puppy_example3);

    Surface(color = MaterialTheme.colors.background) {
        LazyColumn {
            items(names.size) { index ->
                PuppyView(
                    name = names[index],
                    age = ((index % 3) + 1),
                    pics[index % 3],
                    navController
                )
            }
        }
    }
}

@Composable
fun PuppyView(name: String, age: Int, resource: Int, navController: NavController) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                navController.navigate("puppyDetails/$name/$resource")
            }
            .fillMaxWidth()
            .height(125.dp)
            .padding(vertical = 4.dp)
            .background(Color.Blue.copy(alpha = 0.3f))
    ) {
        Image(
            painter = painterResource(id = resource),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp)
                .height(75.dp)
                .width(75.dp)
                .clip(shape = RoundedCornerShape(size = 4.dp)),
            contentScale = ContentScale.Fit
        )

        Column {
            Text(
                text = name,
                style = typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            TextBox(label = "Age : $age yrs", modifier = Modifier.fillMaxWidth())
        }

    }

//    Column (
//        modifier = Modifier
//            .padding(16.dp)
//            .background(MaterialTheme.colors.background)
//        ) {
//        Image(
//            painter = painterResource(id = R.mipmap.puppy_example),
//            contentDescription = null,
//            modifier = Modifier
//                .padding(4.dp)
//                .height(150.dp)
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(8.dp)),
//            contentScale = ContentScale.FillBounds
//        )
//        Spacer(modifier = Modifier.height(5.dp))
//        TextBox(label = name,modifier = Modifier.fillMaxWidth())
////        TextBox(label = "2.5 yrs", modifier = Modifier.fillMaxWidth())
//    }
}

@Composable
fun TextBox(label: String, modifier: Modifier) {
    Text(
        text = label,
        style = typography.body1,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun PuppyDetails(name: String, index: Int) {

//    val pics : List<Int> = listOf(R.mipmap.puppy_example1,R.mipmap.puppy_example2,R.mipmap.puppy_example3);

    var age: Int = 0;
    if (index == R.mipmap.puppy_example1) age = 1;
    else if (index == R.mipmap.puppy_example2) age = 2;
    else age = 3;

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = index),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .height(250.dp)
                .fillMaxWidth(),
        )
        Text(name, style = typography.h4)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Age : $age yrs")
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Adopt!")
        }
    }
}

//@Preview("Light Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun LightPreview() {
//    MyTheme {
//       ComposeNavigation()
//    }
//}

//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview() {
//    MyTheme(darkTheme = true) {
//        MyApp()
//    }
//}
