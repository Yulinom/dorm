<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2022/7/22
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="include.jsp"/>
</head>

<style>
    div.checkin {
        margin-left: auto;
        margin-right: auto;
        margin-top: 15%;
        width: 400px;
    }
</style>
<body>
<div class="checkin">

    <form class="layui-form" action="/login" type="POST">
        <div class="layui-form-item">
            <label class="layui-form-label">账号</label>
            <div class="layui-input-inline">
                <input type="text" id="user" name="user" required lay-verify="required" placeholder="请输入管理员账号"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密&nbsp;&nbsp;码</label>
            <div class="layui-input-inline">
                <input type="password" id="password" name="password" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="login">登陆</button>
                <button type="reset" class="layui-btn layui-btn-primary">重新输入</button>
            </div>
        </div>
    </form>

    <script type="text/javascript">
        layui.use(['form', 'jquery', 'layedit', 'laydate'], function () {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , $ = layui.jquery
                , laydate = layui.laydate;
            form.render();
            //监听提交
            form.on('submin(login)');        });

    </script>
</div>
</body>
</html>
