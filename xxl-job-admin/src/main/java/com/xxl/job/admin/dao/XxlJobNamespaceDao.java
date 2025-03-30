package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobNamespace;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface XxlJobNamespaceDao {

    List<XxlJobNamespace> selectAllNamespace();

    @Select("select * from xxl_job_namespace where namespace=#{name}")
    XxlJobNamespace selectByName(@Param("name") String name);

    List<XxlJobNamespace> pageList(@Param("name") String name, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int selectCount(@Param("name") String name);

    int insert(XxlJobNamespace namespace);

    @Select("select * from xxl_job_namespace where id=#{id}")
    XxlJobNamespace selectById(Long id);

    @Delete("delete from xxl_job_namespace where id=#{id} ")
    int deleteById(Long id);

    @Update("update xxl_job_namespace set description = #{description} where id = #{id}")
    int updateById(Long id, String description);
}
