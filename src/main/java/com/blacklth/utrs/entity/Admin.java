package com.blacklth.utrs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.blacklth.utrs.entity.pojo.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
@TableName("sys_admin")
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private String adminPwd;



    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "adminId=" + adminId +
            ", adminPwd=" + adminPwd +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", isDelete=" + isDelete +
        "}";
    }
}
