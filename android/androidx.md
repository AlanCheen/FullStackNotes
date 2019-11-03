# AndroidX





作为 Jetpack 的一部分，我们正在将 Android 支持库迁移到使用 androidx 命名空间的全新 Android 扩展库。如需了解详情，请参阅 AndroidX 概览。

Android Studio 3.2 利用一项新的迁移功能来帮助您完成此过程。

要将现有项目迁移到 AndroidX，请依次选择 Refactor > Migrate to AndroidX。如果您有任何尚未迁移到 AndroidX 命名空间的 Maven 依赖项，则 Android Studio 编译系统还会自动转换这些项目依赖项。



Android Gradle 插件提供以下可在 `gradle.properties` 文件中设置的全局标记：

1. `android.useAndroidX`：当设为 true 时，此标记表示您想立即开始使用 AndroidX。如果缺少此标记，则 Android Studio 会假定此标记已设为 false。
2. `android.enableJetifier`：当设为 true 时，此标记表示您想要获得相关的工具支持（通过 Android Gradle 插件），以便将现有第三方库当作针对 AndroidX 编写的库进行自动转化。如果缺少此标记，则 Android Studio 会假定此标记已设为 false。



当您使用 **Migrate to AndroidX** 命令时，这两个标记都会设为 true。



如果您想立即开始使用 AndroidX 库，而不需要转换现有的第三方库，只需将 `android.useAndroidX` 标记设为 true，并将 `android.enableJetifier` 标记设为 false。



### 资料

AndroidX 概览：https://developer.android.com/jetpack/androidx

迁移到 AndroidX：https://developer.android.com/jetpack/androidx/migrate