<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2022/7/25
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>宿舍管理</title>
    <jsp:include page="include.jsp"/>
</head>
<body>

<table id="demo" lay-filter="test"></table>

<script type="text/html" id="toolEventDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">宿舍编辑</a>
</script>

<script type="text/html" id="operationBar">
    <div class="layui-row">

        <form class="layui-form">
            <div class="layui-col-md9">
                <div class="layui-inline">
                    <label class="layui-form-label">学生ID</label>
                    <div class="layui-input-inline">
                        <input type="text" id="id" name="id" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label" style="white-space:nowrap">学生姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" id="studentName" name="studentName" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <button type="button" class="layui-btn " style="margin: 20px 10px" id="searchBtn">
                        <i class="layui-icon layui-icon-search"></i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</script>

<script>
    layui.use(['table', 'jquery', 'form'], function () {
        var table = layui.table;
        var form = layui.form;
        var $ = layui.$;

        form.render();

        //第一个实例
        table.render({
            elem: '#demo'
            , toolbar: '#operationBar'
            , height: 312
            , url: '/dorm/student/pageStudent' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 150, sort: true, fixed: 'left', align: 'center'}
                , {field: 'studentName', title: '学生名', align: 'center'}
                , {field: 'dormId', title: '宿舍ID', align: 'center'}
                , {title: '操作', toolbar: '#toolEventDemo', align: 'center'}
            ]]
            , parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.item //解析数据列表
                };
            }
        });

        //单元格工具事件
        table.on('tool(test)', function (obj) { // 注：test 是 table 原始标签的属性 lay-filter="对应的值"
            var data1 = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
            var newdormid = data1.id; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'edit') { //编辑
                layer.prompt({
                    formType: 2,
                    value: data1.dormid,
                    title: '请输入宿舍id',
                    area: ['400px', '100px'] //自定义文本域宽高
                }, function(value, index, elem){
                   newdormid = value;

                    layer.close(index);
                    $.ajax({
                        url: '/dorm/dormset/edit',
                        type: 'POST',
                        data: {
                            id: data1.id,
                            dormid: newdormid,
                        },
                        success: function () {
                            layer.msg(data1);
                            location.reload();
                        }

                    });
                    table.reloadData('demo', {
                        scrollPos: 'fixed'  // 保持滚动条位置不变 - v2.7.3 新增
                    });

                });

            }

            $("body").on('click', '#searchBtn', function () {
                var studentName = $('#studentName').val();
                var id = $('#id').val();
                var studentQueryVO = {
                    'studentName': studentName
                    , 'id': id
                }

                table.reload('demo', {
                    method: 'post'
                    , url: '/dorm/student/pageStudentCondition'
                    , contentType: 'application/json'
                    , where: studentQueryVO
                    , page: {
                        curr: 1
                    }
                })
            });
        });
    })
</script>
</body>
</html>
