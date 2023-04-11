package com.tg.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tg.admin.entity.Role;
import com.tg.admin.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);

}
