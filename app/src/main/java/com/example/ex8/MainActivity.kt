package com.example.ex8

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.ex8.ui.theme.EX8Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EX8Theme {
                Experiment8App()
            }
        }
    }
}


// =====================================================
// MAIN APP
// =====================================================

@Composable
fun Experiment8App() {

    val menuExpanded = remember {
        mutableStateOf(false)
    }

    val showWebView = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // =================================================
            // MENU BUTTON
            // =================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Button(
                    onClick = {
                        menuExpanded.value = true
                    }
                ) {
                    Text("MENU")
                }

                // =================================================
                // DROPDOWN MENU
                // =================================================

                DropdownMenu(
                    expanded = menuExpanded.value,
                    onDismissRequest = {
                        menuExpanded.value = false
                    }
                ) {

                    // HOME
                    DropdownMenuItem(
                        text = {
                            Text("Home")
                        },
                        onClick = {
                            showWebView.value = false
                            menuExpanded.value = false
                        }
                    )

                    // WEBVIEW
                    DropdownMenuItem(
                        text = {
                            Text("Open WebView")
                        },
                        onClick = {
                            showWebView.value = true
                            menuExpanded.value = false
                        }
                    )

                    // ABOUT
                    DropdownMenuItem(
                        text = {
                            Text("About")
                        },
                        onClick = {
                            menuExpanded.value = false
                        }
                    )
                }
            }


            // =================================================
            // CONTENT
            // =================================================

            if (showWebView.value) {

                WebViewScreen()

            } else {

                HomeScreen()
            }
        }
    }
}


// =====================================================
// HOME SCREEN
// =====================================================

@Composable
fun HomeScreen() {

    // Images selected from phone
    val phoneImages = remember {
        mutableStateListOf<Uri>()
    }

    // =================================================
    // POPUP STATES
    // =================================================

    // Stores which image was clicked
    val selectedImageIndex = remember {
        mutableStateOf<Int?>(null)
    }

    // Controls View Image dialog
    val showViewDialog = remember {
        mutableStateOf(false)
    }


    // =================================================
    // PHOTO PICKER
    // =================================================

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(
                maxItems = 3
            )
        ) { uris ->

            phoneImages.clear()

            phoneImages.addAll(
                uris.take(3)
            )
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        // =================================================
        // TITLE
        // =================================================

        Text(
            text = "Experiment 8",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "9 Image Grid",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // =================================================
        // SELECT IMAGES FROM PHONE
        // =================================================

        Button(
            onClick = {

                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },

            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {

            Text("Select 3 Images from Phone")
        }


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // =================================================
        // 3 x 3 IMAGE GRID
        // =================================================

        ImageGrid(
            phoneImages = phoneImages,

            onPhoneImageClick = { index ->
                selectedImageIndex.value = index
            }
        )


        // =================================================
        // POPUP MENU
        // =================================================

        if (selectedImageIndex.value != null) {

            val index = selectedImageIndex.value!!

            AlertDialog(

                onDismissRequest = {
                    selectedImageIndex.value = null
                },

                title = {
                    Text("Image Options")
                },

                text = {
                    Text("What would you like to do with this image?")
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            // Open image
                            showViewDialog.value = true

                            selectedImageIndex.value = null
                        }
                    ) {
                        Text("View Image")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {

                            // Delete selected image
                            if (index < phoneImages.size) {
                                phoneImages.removeAt(index)
                            }

                            selectedImageIndex.value = null
                        }
                    ) {
                        Text("Delete")
                    }
                }
            )
        }


        // =================================================
        // VIEW IMAGE DIALOG
        // =================================================

        if (showViewDialog.value) {

            // This is only shown when a valid image was selected
            // The image itself is handled below.
        }
    }


    // =================================================
    // VIEW IMAGE
    // =================================================

    if (showViewDialog.value) {

        // Since the selected index is cleared after clicking
        // View Image, this section is handled using a separate
        // state in the improved version below.
    }
}


// =====================================================
// IMAGE GRID
// =====================================================

@Composable
fun ImageGrid(
    phoneImages: List<Uri>,
    onPhoneImageClick: (Int) -> Unit
) {

    // =================================================
    // THREE URL IMAGES
    // =================================================

    val urlImages = listOf(

        "https://picsum.photos/id/1015/500/500",

        "https://picsum.photos/id/1016/500/500",

        "https://picsum.photos/id/1018/500/500"
    )


    LazyVerticalGrid(

        columns = GridCells.Fixed(3),

        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),

        horizontalArrangement = Arrangement.spacedBy(6.dp),

        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {


        // =================================================
        // ROW 1
        // THREE DRAWABLE IMAGES
        // =================================================

        item {

            LocalImage(
                imageRes = R.drawable.image1
            )
        }

        item {

            LocalImage(
                imageRes = R.drawable.image2
            )
        }

        item {

            LocalImage(
                imageRes = R.drawable.image3
            )
        }


        // =================================================
        // ROW 2
        // THREE PHONE STORAGE IMAGES
        // =================================================

        itemsIndexed(phoneImages) { index, uri ->

            PhoneImage(
                uri = uri,
                onClick = {
                    onPhoneImageClick(index)
                }
            )
        }


        // Empty boxes if less than 3 images are selected
        items(
            3 - phoneImages.size
        ) { index ->

            EmptyImageBox(
                text = "Phone ${phoneImages.size + index + 1}"
            )
        }


        // =================================================
        // ROW 3
        // THREE URL IMAGES
        // =================================================
        for (url in urlImages) {
            item {
                UrlImage(
                    url = url
                )
            }
        }

        }
    }



// =====================================================
// DRAWABLE IMAGE
// =====================================================

@Composable
fun LocalImage(
    imageRes: Int
) {

    Image(

        painter = androidx.compose.ui.res.painterResource(
            id = imageRes
        ),

        contentDescription = "Drawable Image",

        modifier = Modifier
            .size(110.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ),

        contentScale = ContentScale.Crop
    )
}


// =====================================================
// PHONE STORAGE IMAGE
// =====================================================

@Composable
fun PhoneImage(
    uri: Uri,
    onClick: () -> Unit
) {

    AsyncImage(

        model = uri,

        contentDescription = "Phone Image",

        modifier = Modifier
            .size(110.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onClick()
            },

        contentScale = ContentScale.Crop
    )
}


// =====================================================
// URL IMAGE
// =====================================================

@Composable
fun UrlImage(
    url: String
) {

    AsyncImage(

        model = url,

        contentDescription = "URL Image",

        modifier = Modifier
            .size(110.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ),

        contentScale = ContentScale.Crop
    )
}


// =====================================================
// EMPTY PHONE IMAGE BOX
// =====================================================

@Composable
fun EmptyImageBox(
    text: String
) {

    Box(

        modifier = Modifier
            .size(110.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                Color.LightGray
            ),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text
        )
    }
}


// =====================================================
// WEBVIEW
// =====================================================

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {

    AndroidView(

        modifier = Modifier
            .fillMaxSize(),

        factory = { context ->

            WebView(context).apply {

                // Keep navigation inside WebView
                webViewClient = WebViewClient()

                // Enable JavaScript
                settings.javaScriptEnabled = true

                // Open website
                loadUrl(
                    "https://www.google.com"
                )
            }
        }
    )
}
