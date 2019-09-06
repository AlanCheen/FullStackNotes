# Awesome Shell Scripts



记录一些有用个 Shell Script。



### Android



查看当前的 Activity 类名等信息。

```shell
#!/bin/sh
adb shell dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'
```



查看 SurfaceFlinger 信息

```shell
#!/bin/sh
adb shell dumpsys SurfaceFlinger
```



