包含块（containing block）是 CSS 中一个重要的概念，它指的是一个元素的布局上下文，决定了该元素的定位和尺寸。对于绝对定位元素和一些其他定位属性的计算，包含块是至关重要的。

以下是包含块的一些常见规则：

根元素（html）： 根元素是包含整个文档的初始包含块。

定位为 relative、absolute、fixed 或 sticky 的元素： 这些元素的包含块是距离最近的祖先定位元素（position 不是 static）或根元素。

浮动元素： 对于浮动元素，其包含块是距离最近的祖先元素，其 display 值不是 none。

块级元素： 对于普通的块级元素，其包含块是包含它的块级父元素的内容框。

行内元素： 对于行内元素，其包含块是包含它的块级父元素的内容框。

这里有一个简单的例子：

html
Copy code
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="styles.css">
  <title>Containing Block Example</title>
</head>
<body>

  <div class="container">
    <div class="box"></div>
  </div>

</body>
</html>
CSS（styles.css）：

css
Copy code
.container {
position: relative;
width: 300px;
height: 200px;
background-color: #eee;
}

.box {
position: absolute;
top: 20px;
left: 20px;
width: 100px;
height: 100px;
background-color: #3498db;
}
在这个例子中，.container 元素是一个相对定位的块级元素，它包含了一个绝对定位的子元素 .box。.container 元素就是 .box 元素的包含块。包含块的选择对于定位元素的位置计算非常重要，它们相互关联，影响元素在页面中的布局。