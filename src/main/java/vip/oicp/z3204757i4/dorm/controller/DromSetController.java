package vip.oicp.z3204757i4.dorm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vip.oicp.z3204757i4.dorm.entity.Dorm;
import vip.oicp.z3204757i4.dorm.entity.Student;
import vip.oicp.z3204757i4.dorm.entity.vo.DormQueryVO;
import vip.oicp.z3204757i4.dorm.service.DormService;
import vip.oicp.z3204757i4.dorm.service.StudentService;
import vip.oicp.z3204757i4.dorm.utils.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dorm/dormset")
public class DromSetController {
    @Autowired
    private DormService dormService;
    @Autowired
    private StudentService stuSer;


    @RequestMapping("/ini")
    public ModelAndView ini(){
        return new ModelAndView("dormedit");
    }

    @RequestMapping("/list")
    public ResultVO list(@RequestParam("page") String page, @RequestParam("limit") String limit) {
        List<Dorm> dorms = dormService.list(null);
        System.out.println(dorms);
        return ResultVO.ok().data("item", dorms);
    }

    //分页查询宿舍
    @ApiOperation(value = "分页查询宿舍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "limit", value = "每页记录")
    })
    @GetMapping("/pageStudent")
    public ResultVO pageListDorm(@RequestParam long page, @RequestParam long limit) {
        Page<Dorm> dormPage = new Page<>(page, limit);

        dormService.page(dormPage, null);
        long total = dormPage.getTotal();
        List<Dorm> records = dormPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    // 带条件分页查询
    @ApiOperation(value = "带条件分页查询dorm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "limit", value = "每页记录")
    })
    @PostMapping("/pageDormCondition")
    public ResultVO pageStudentCondition(@RequestBody(required = false) DormQueryVO dormQueryVO) {
        //创建page对象
        Page<Dorm> dormPage = new Page<>(dormQueryVO.getPage(), dormQueryVO.getLimit());
        //构建条件
        QueryWrapper<Dorm> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = dormQueryVO.getDormNumber();
        String id = dormQueryVO.getId();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("dorm_number", name);
        }
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到drompage对象里面
        dormService.page(dormPage, wrapper);
        //总记录数
        long total = dormPage.getTotal();
        //数据list集合
        List<Dorm> records = dormPage.getRecords();
        Map map = new HashMap();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    //修改学生宿舍
    @ApiOperation(value = "修改学生宿舍属性值")
    @PostMapping("/edit")
    public void Dormset( String id ,String dormid) {

        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        System.out.println("dormid = " + dormid);
        Student stu = new Student();
        stu.setDormId(dormid);
        boolean rows = stuSer.update(stu,updateWrapper);
        System.out.println("rows = " + rows);

    }
}
