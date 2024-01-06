function openModal() {
    document.getElementById('myModal').style.display = 'block';
    document.getElementById('overlay').style.display = 'block'; // 显示蒙层
}

function closeModal() {
    document.getElementById('myModal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none'; // 隐藏蒙层
}

// 点击蒙层外部区域关闭弹窗
window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target === modal) {
        modal.style.display = 'none';
        document.getElementById('overlay').style.display = 'none'; // 隐藏蒙层
    }
}
