# MAD EX8 – Image Grid, Photo Picker & WebView

## 📱 Project Overview

This project is **Experiment 8** for Mobile Application Development (MAD). It is an Android application developed using **Kotlin** and **Jetpack Compose**.

The application demonstrates how to:

* Display images in a **3 × 3 grid**
* Load images from the application's drawable resources
* Select multiple images from the phone's gallery
* Display images using **URI-based image loading**
* Load images directly from **internet URLs**
* Implement a **Dropdown Menu**
* Open a website using an Android **WebView**

---

## 🎯 Objective

The objective of this experiment is to understand and implement different methods of displaying images in an Android application using Jetpack Compose.

The application combines three different image sources:

1. **Local drawable images**
2. **Images selected from phone storage**
3. **Images loaded from online URLs**

It also demonstrates navigation using a dropdown menu and embedding a website inside the application using WebView.

---

## 🛠️ Technologies Used

| Technology          | Purpose                              |
| ------------------- | ------------------------------------ |
| Kotlin              | Programming language                 |
| Android Studio      | Development environment              |
| Jetpack Compose     | UI development                       |
| Material 3          | UI components                        |
| Coil                | Loading images from URI and URLs     |
| WebView             | Displaying web content               |
| LazyVerticalGrid    | Creating the 3 × 3 image grid        |
| Activity Result API | Selecting images from device storage |

---

## ✨ Features

### 1. 3 × 3 Image Grid

The home screen contains a nine-image grid divided into three sections:

* **Row 1:** Three images from drawable resources
* **Row 2:** Three images selected from the phone
* **Row 3:** Three images loaded from internet URLs

The grid is created using `LazyVerticalGrid` with three fixed columns.

---

### 2. Local Drawable Images

Three images are stored inside the application's `drawable` resource folder:

```text
res/drawable/
├── image1
├── image2
└── image3
```

These images are displayed using Jetpack Compose's `Image` composable.

---

### 3. Select Images From Phone

The application allows the user to select up to **three images** from the device gallery.

The Android Activity Result API is used with:

```kotlin
ActivityResultContracts.PickMultipleVisualMedia(
    maxItems = 3
)
```

Only image files can be selected.

The selected images are stored as `Uri` objects and displayed using Coil's `AsyncImage`.

---

### 4. Online Images

The third row contains three images loaded from online URLs.

Example:

```text
https://picsum.photos/id/1015/500/500
https://picsum.photos/id/1016/500/500
https://picsum.photos/id/1018/500/500
```

Coil is used to asynchronously load these images.

---

### 5. Dropdown Menu

A **MENU** button is provided at the top of the application.

The menu contains:

* Home
* Open WebView
* About

Selecting **Home** displays the image grid.

Selecting **Open WebView** opens the WebView screen.

---

### 6. WebView

The application contains a WebView screen that loads:

```text
https://www.google.com
```

The WebView uses:

```kotlin
WebViewClient()
```

to keep navigation inside the application.

JavaScript is also enabled for the WebView.

---

## 🏗️ Application Structure

The main components of the application are:

```text
MainActivity
│
└── Experiment8App
    │
    ├── Drop
```
