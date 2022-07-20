package vip.oicp.z3204757i4.dorm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ulinom
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Dorm对象")
public class Dorm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "宿舍标识")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "楼房号，如:西三404")
    private String dormNumber;

    @ApiModelProperty(value = "水费")
    private BigDecimal waterBill;

    @ApiModelProperty(value = "电费")
    private BigDecimal electricBill;


}
