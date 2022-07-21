package vip.oicp.z3204757i4.dorm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vip.oicp.z3204757i4.dorm.entity.Student;
import vip.oicp.z3204757i4.dorm.entity.vo.StudentQueryVO;
import vip.oicp.z3204757i4.dorm.service.StudentService;
import vip.oicp.z3204757i4.dorm.utils.ResultVO;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ulinom
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/dorm/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/list")
    public ResultVO list(@RequestParam("page") String page, @RequestParam("limit") String limit) {
        List<Student> students = studentService.list(null);
        System.out.println(students);
        return ResultVO.ok().data("item", students);
    }

    @ApiOperation(value = "分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "limit", value = "每页记录")
    })
    @GetMapping("/pageStudent")
    public ResultVO pageListStudent(@RequestParam long page, @RequestParam long limit) {
        //创建page对象
        Page<Student> studentPage = new Page<>(page, limit);

        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        studentService.page(studentPage, null);
        //总记录数
        long total = studentPage.getTotal();
        //数据list集合
        List<Student> records = studentPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    // 带条件分页查询讲师
    @ApiOperation(value = "带条件分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页"),
            @ApiImplicitParam(name="limit",value = "每页记录")
    })
    @PostMapping("/pageStudentCondition")
    public ResultVO pageStudentCondition(@RequestBody(required = false) StudentQueryVO studentQueryVO){
        //创建page对象
        Page<Student> teacherPage =new Page<>(studentQueryVO.getPage(),studentQueryVO.getLimit());
        //构建条件
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = studentQueryVO.getStudentName();
        String id = studentQueryVO.getId();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("student_name",name);
        }
        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id", id);
        }
        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        studentService.page(teacherPage,wrapper);
        //总记录数
        long total = teacherPage.getTotal();
        //数据list集合
        List<Student> records = teacherPage.getRecords();
        Map map=new HashMap();
        map.put("count",total);
        map.put("item",records);
        return ResultVO.ok().data(map);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable String id) {
        if (studentService.removeById(id)) {
            return ResultVO.ok().message("学生删除成功");
        } else return ResultVO.error();
    }

    @DeleteMapping("/removeList")
    public ResultVO removeList(@RequestBody String ids) {
        String[] idList = ids.split(",");
        if (studentService.removeByIds(Arrays.asList(idList))) {
            return ResultVO.ok();
        } else return ResultVO.error();
    }

}

