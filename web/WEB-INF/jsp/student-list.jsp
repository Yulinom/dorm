<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2022/7/20
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>table 模块快速使用</title>
    <jsp:include page="include.jsp"/>
</head>
<body>

<table id="demo" lay-filter="test"></table>

<script type="text/html" id="toolEventDemo">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>
    layui.use('table', function () {
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            , toolbar: '#toolbarDemo'
            , height: 312
            , url: '/dorm/student/list' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {field: 'id', title: 'ID', width: 150, sort: true, fixed: 'left', align: 'center'}
                , {field: 'studentName', title: '学生名', align: 'center'}
                , {field: 'dormId', title: '宿舍ID', align: 'center'}
                , {title: '操作', toolbar: '#toolEventDemo', align: 'center'}
            ]]
            , parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.data.item //解析数据列表
                };
            }
        });

        //触发事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    layer.msg('添加');
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            }
            ;
        });
    });
</script>
</body>
</html>
