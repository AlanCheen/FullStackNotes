# APK 瘦身













- minifyEnabled true
- shrinkResources true

```groovy
android {
buildTypes {
    release {
        debuggable false
        minifyEnabled true
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android.txt'),'proguard-project.txt'
        zipAlignEnabled true
    }
}
}
```

