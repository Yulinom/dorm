package vip.oicp.z3204757i4.dorm.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.oicp.z3204757i4.dorm.entity.Student;
import vip.oicp.z3204757i4.dorm.service.StudentService;
import vip.oicp.z3204757i4.dorm.utils.ResultVO;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
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
    public ResultVO list(@RequestParam("page") String page, @RequestParam("limit") String limit){
        List<Student> students = studentService.list(null);
        System.out.println(students);
        return ResultVO.ok().data("item",students);
    }

    @ApiOperation(value = "分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "limit", value = "每页记录")
    })
    @GetMapping("/pageStudent")
    public ResultVO pageListStudent(@RequestParam long page, @RequestParam long limit) {
        //创建page对象
        Page<Student> fruitPage = new Page<>(page, limit);

        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        studentService.page(fruitPage, null);
        //总记录数
        long total = fruitPage.getTotal();
        //数据list集合
        List<Student> records = fruitPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

}

