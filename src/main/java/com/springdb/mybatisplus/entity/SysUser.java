package com.springdb.mybatisplus.entity;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import com.springdb.mybatisplus.common.SuperEntity;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.mapper.SqlCondition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author niyue
 * @since 2018-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends SuperEntity<SysUser> {

	private static final long serialVersionUID = 1L;

	public SysUser() {

	}

	public SysUser(Long id) {
		this.id = id;
	}

	/**
	 * 用户ID
	 */
	private Long id;
	/**
	 * 用户名
	 */
	@TableField(condition = SqlCondition.LIKE)
	private String name;
	/**
	 * 用户年龄
	 */
	@TableField(update="%s+1")
	private Integer age;
	/**
	 * 0、禁用 1、正常,
	 * 如果使用tinyint(1)，mysql连接没加tinyInt1isBit=false，默认mysql驱动会把值转成boolean
	 */
	private Integer type;
	/**
	 * 乐观锁
	 */
	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;
	/**
	 * 逻辑删除
	 */
	@TableLogic
	private String delete;
	/**
	 * 自定义填充的创建时间
	 */
	private Date ctime;
	
	@TableField(update="now()")
	private Date utime;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String AGE = "age";

	public static final String TYPE = "type";

	public static final String VERSION = "version";

	public static final String DELETE = "delete";

	public static final String CTIME = "ctime";

	public static final String UTIME = "utime";

	// @Override
	// protected Serializable pkVal() {
	// return this.id;
	// }

}
