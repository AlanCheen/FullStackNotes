# JavaScript













JS 的问题：

1. 语法太灵活导致开发大型 Web 项目困难；解法：TypeScript
2. 性能不能满足一些场景的需要；解法：Google 的 V8





### TypeScript



![image-20190724223645739](http://ww1.sinaimg.cn/large/006tNc79ly1g5bb0zs0r4j31pi0bkab7.jpg)



### Google : V8

V8 是 Google 推出的 JS 引擎，使用 JIT 技术提升JS 的执行速度。并且成功地使性能提升了数十倍。





### WebAssembly

WebAssembly 是一份`字节码标准`，以字节码的形式依赖虚拟机在浏览器中运行。

注意：`它并不是汇编`，只是看起来像汇编而已。



可以依赖 Emscripten 等编译器将 C++/Golang/Rust/Kotlin 等`强类型`语言编译成 WebAssembly 字节码（`.wasm` 文件）。

（JS 不能直接编译成.wasm，因为它是弱类型）







### 优质资料

腾讯：

[WebAssembly 不完全指北](https://juejin.im/post/5d367656f265da1b904c2126)

https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/A_re-introduction_to_JavaScript