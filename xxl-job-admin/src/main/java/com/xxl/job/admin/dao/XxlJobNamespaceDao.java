package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XxlJobNamespaceDao {

    List<XxlJobNamespace> selectAllNamespace();

    @Select("select * from xxl_job_namespace where namespace=#{name}")
    XxlJobNamespace selectByName(@Param("name") String name);
}
