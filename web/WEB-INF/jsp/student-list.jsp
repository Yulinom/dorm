<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<script type="text/html" id="operationBar">
    <div class="layui-row">
        <div class="layui-col-md3">
            <button type="button" class="layui-btn layui-btn-danger"
                    id="deleteObjectsByIdList" style="margin-left: 20px; margin-top: 20px">批量移除
            </button>
        </div>

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
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'detail') { //查看
                //do somehing
            } else if (layEvent === 'del') { //删除
                layer.confirm('确定删除吗？', function (index) {
                    obj.del(); // 删除对应行（tr）的 DOM 结构，并更新缓存
                    layer.close(index);

                    // 向服务端发送删除请求，执行完毕后，可通过 reloadData 方法完成数据重载
                    $.ajax({
                        url: '/dorm/student/' + data.id
                        , type: 'delete'
                        , success: function (res) {
                            if (res.code == '0') {
                                layer.msg(res.msg, {icon: 1});
                                setTimeout(function () {
                                    location.reload();
                                }, 1000)
                            } else {
                                layer.msg(res.msg, {icon: 2, time: 3000})
                            }
                        }
                    })

                    table.reloadData(id, {
                        scrollPos: 'fixed'  // 保持滚动条位置不变 - v2.7.3 新增
                    });
                });
            } else if (layEvent === 'edit') { //编辑
                //do something

                // 同步更新缓存对应的值
                // 该方法仅为前端层面的临时更新，在实际业务中需提交后端请求完成真实的数据更新。
                obj.update({
                    username: '123',
                    title: 'abc'
                });
                // 若需更新其他包含自定义模板并可能存在关联的列视图，可在第二个参数传入 true
                obj.update({
                    username: '123'
                }, true); // 注：参数二传入 true 功能为 v2.7.4 新增

                // 当发送后端请求成功后，可再通过 reloadData 方法完成数据重载
                /*
                table.reloadData(id, {
                  scrollPos: 'fixed'  // 保持滚动条位置不变 - v2.7.3 新增
                });
                */
            }
        });

        $('#deleteObjectsByIdList').on('click', function () { //获取选中数据
            var checkStatus = table.checkStatus('demo')
                , data = checkStatus.data, ids = '';
            if (data.length == 0) {
                layer.msg('请先选择要删除的数据行！', {icon: 2});
                return;
            }
            for (var i = 0; i < data.length; i++) {
                ids += data[i].id + ","; //没有使用json数组，拼接的字符窜，需要接收后处理
            }
            layer.confirm('您确定要删除所选学生吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: '/dorm/student/removeList'
                    , type: 'delete'
                    , contentType: 'application/json'
                    , data: ids
                    , dataType: 'json'
                    , success: function (res) {
                        if (res.code == '0') {
                            layer.msg(res.msg, {icon: 1});
                            setTimeout(function () {
                                location.reload();
                            }, 1000)
                        } else {
                            layer.msg(res.msg, {icon: 2, time: 3000})
                        }
                    }
                })
            })

        });

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
                , contentType : 'application/json'
                , where: studentQueryVO
                , page: {
                    curr: 1
                }
            })
        });

    })
    ;
</script>
</body>
</html>
