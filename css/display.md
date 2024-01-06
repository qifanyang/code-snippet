position 是 CSS 中用于指定元素定位方式的属性，它的取值有：

static（默认值）： 元素在文档流中正常定位，即按照它在 HTML 中的顺序放置。
top、right、bottom、left 和 z-index 不会影响 static 定位的元素。
.element {
position: static;
}

relative： 元素相对于其正常位置进行定位，但仍然占据文档中的原始空间。
其他元素的布局不会受到影响，但可以通过 top、right、bottom、left 来相对调整元素的位置。
.element {
position: relative;
top: 10px;
left: 20px;
}
absolute： 元素相对于最近的非 static 定位的父元素进行定位，
如果没有非 static 定位的父元素，则相对于最初的包含块（通常是 <html> 元素）。
脱离文档流，不占据原始空间。
.element {
position: absolute;
top: 30px;
left: 40px;
}

fixed： 元素相对于浏览器窗口进行定位，即使页面滚动，元素位置也不会改变。也是脱离文档流的定位方式。
.element {
position: fixed;
top: 50px;
left: 60px;
}

sticky： 元素根据用户的滚动位置在相对定位和固定定位之间切换。相对于视窗或包含块中最近的滚动容器定位。
.element {
position: sticky;
top: 70px;
left: 80px;
}
position 属性的使用场景包括：

实现元素的精确定位，比如菜单、弹出框等。
创建层叠效果，控制元素在层叠上下文中的层级关系。
实现一些特殊布局，如固定导航栏、悬浮按钮等。

absolute/fixed脱离文档流实现特殊效果

