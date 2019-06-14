# ART and Dalvik

### ART

>  Android Runtime (ART) 是 Android 上的应用和部分系统服务使用的托管式运行时。

ART 及其前身 Dalvik 最初是专为 Android 项目打造的。作为运行时的 ART 可执行 Dalvik 可执行文件并遵循 Dex 字节码规范。

ART 和 Dalvik 是运行 Dex 字节码的兼容运行时，因此针对 Dalvik 开发的应用也能在 ART 环境中运作。不过，Dalvik 采用的一些技术并不适用于 ART。



#### AOT

ART 采用 AOT(ahead of time)技术，也即预编译，会在应用第一次安装的时候就预先把字节码编译成机器码，这样能够加速应用的启动，可以说是用空间换时间。



ART 同时也提升了性能、垃圾回收



**dex2oat**：把 APK 里的 `.dex` 文件转化成 `OAT` 文件，OAT文件是一种 Android 私有的 `ELF` 文件格式，它不仅包含有从 dex 文件翻译而来的本地机器指令，还包含有原来的 dex 文件内容。这使得我们无需重新编译原有的APK 就可以让它正常地在ART里面运行。



### Dalvik



在 4.4 以及之前的版本，Android 跑在 Dalvik 虚拟机上，也称 DVM。



### 资料

https://source.android.com/devices/tech/dalvik/

https://source.android.com/devices/tech/dalvik/jit-compiler