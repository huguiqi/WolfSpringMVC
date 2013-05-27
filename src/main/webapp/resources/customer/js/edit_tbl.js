/**
 * 可编辑表格
 */
self.onmessage = function(event) {
  self.postMessage('Hi there!');
};

function tbl_dbclick() {
    //保存当前的td节点
   var td = $(this);
    //取出当前文本内容保存
    var text = td.text();
    //清空td里的内容
    td.empty();
    //创建一个input输入框
   var input = $("<input>");
    //将保存起来的文本放到td里去
    input.attr("value", text);
    td.append(input);
    //将文本框中的内容高亮显示
    var inputDom=input.get(0);
    inputDom.select();
    //解除绑定
    td.unbind();

    input.keyup(function(event){
            //解决不同浏览器事件处理的不同
            var myevent=event||window.event;
            var keycode=myevent.keyCode;
            if(keycode==13){
                 var inputNode=$(this);
                var inputText=inputNode.val();
                //得到td节点
                var tdNode=inputNode.parent();

                //清空td里的值
                tdNode.html(inputText);
                //让td重新拥有双击事件
                td.dblclick(tbl_dbclick);
            }

        });

}