# Android 运行时权限

> 迁移语雀 2021-2-22



从 Android 6.0（API 级别 23）开始，用户开始在应用运行时向其授予权限，而不是在应用安装时授予。



系统权限分为两类：*正常权限*和*危险权限*：

- **正常权限**不会直接给用户隐私权带来风险。如果您的应用在其清单中列出了正常权限，系统将自动授予该权限。
- **危险权限**会授予应用访问用户机密数据的权限。如果您的应用在其清单中列出了正常权限，系统将自动授予该权限。如果您列出了危险权限，则用户必须明确批准您的应用使用这些权限。



当涉及到危险权限时，需要在**运行时进行申请**，如果不，则会导致 crash。



处理步骤：

1. 检查所需的权限是否已经授权，`ContextCompat.checkSelfPermission()`；
2. 如果没有则请求，先检查是否被拒绝，
   1. 先通过`shouldShowRequestPermissionRationale()` 检查是否已经被拒绝了，如果返回了`true`，则表示用户在之前我们请求的时候拒绝了，得去设置里，如果`false`则可以`requestPermissions`去请求；
3. `requestPermissions`请求
4. 请求结果校验，重写`onRequestPermissionsResult`方法，



```java
// Here, thisActivity is the current activity
if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            Manifest.permission.READ_CONTACTS)) {

        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

    } else {

        // No explanation needed, we can request the permission.

        ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }
}
```



是否授权的结果：

- PackageManager.PERMISSION_GRANTED
- PackageManager.PERMISSION_DENIED



比如：

A）请求权限：

```java
String[] permissions = {
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
};

ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
```

B）接受结果：

```java
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}
```



### 资料

https://developer.android.com/training/permissions/requesting?hl=zh-cn

https://developer.android.com/guide/topics/permissions/overview?hl=zh-cn

https://developer.android.com/training/permissions/usage-notes?hl=zh-cn#testing

