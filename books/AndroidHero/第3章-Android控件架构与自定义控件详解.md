# 第3章 Android控件架构与自定义控件详解

1. 整个界面的控件形成一个控件树,上层控件负责下层子控件的测量与绘制,并传递事件,并且每个控件树的顶点都有一个ViewParent对象,所有交互事件都由它控制
2. findViewById在控件树中以**深度优先遍历**来查找对应元素.
3. 每个Activity都包含一个Window对象(通常是PhoneWindow),它将DecorView设为整个应用窗口的根View
4. `requestWindowFeature()`方法一定要在调用`setContentView`之前才有效.
5. `onResume`之后,系统才将DecorView添加到PhoneWindow并显示.


## View的测量 onMeasure

**MeasureSpec**，一个32位的int值，高2位为测量的模式，低30位为测量的大小（位运算提高并优化效率）

模式|含义|条件 
:--:|:--:|:--:	 		
EXACTLY|精确模式|具体值（如30dp）/`match_parent`
AT_MOST|最大值模式|`wrap_content`
UNSPECIFIED|不限制模式|系统/自定义View


0. View默认的`onMeasure`只支持Exactly，所以自定义View如果需要支持`wrap_content`,则需要重写`onMeasure`  
1. 通过`Measpec.getMode/.getSize`可以获取测量模式与数值大小  
2. `setMeasuredDimension（int,int）`完成测量，这之后`getMeasuredHeight`和`getMeasuredWidth`才有值，不然都是0  


## View的绘制 onDraw

`Canvas canvas = new Canvas(bitmap)`  

1. Canvas的Bitmap与Canvas是紧密联系在一起的，这个过程称为装载画布   
2. 该Bitmap存储所有绘制在Canvas上的像素信息（即canvas.drawXXX()方法都发生在该Bitmap上） 
15. mTouchSlop = ViewConfiguration.getScaledTouchSlop()

## ViewGroup的测量与绘制

