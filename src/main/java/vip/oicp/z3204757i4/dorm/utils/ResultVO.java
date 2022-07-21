package vip.oicp.z3204757i4.dorm.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class ResultVO {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //构造方法私有
    private ResultVO(){}

    //成功静态方法
    public static ResultVO ok(){
        ResultVO resultVo=new ResultVO();
        resultVo.setSuccess(true);
        resultVo.setCode(ResultCode.SUCCESS);
        resultVo.setMsg("成功");
        return resultVo;
    }
    //失败静态方法
    public static ResultVO error(){
        ResultVO resultVo=new ResultVO();
        resultVo.setSuccess(false);
        resultVo.setCode(ResultCode.ERROR);
        resultVo.setMsg("失败");
        return resultVo;
    }

    public ResultVO success(Boolean success){
        this.setSuccess(success);
        return this;

    }
    public ResultVO message(String message){
        this.setMsg(message);
        return this;
    }

    public ResultVO code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultVO data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultVO data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
