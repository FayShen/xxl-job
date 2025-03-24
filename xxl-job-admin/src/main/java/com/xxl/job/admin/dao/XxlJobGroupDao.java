package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobGroupDao {

    public List<XxlJobGroup> findAll();

    public List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

    public int save(XxlJobGroup xxlJobGroup);

    public int update(XxlJobGroup xxlJobGroup);

    public int remove(@Param("id") int id);

    public XxlJobGroup load(@Param("id") int id);

    public List<XxlJobGroup> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("appname") String appname,
                                      @Param("title") String title,
                                      @Param("namespace") String namespace);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title,
                             @Param("namespace") String namespace);


    /**
     * 根据命名空间查询
     * @param namespace namespace
     * @return List<XxlJobGroup>
     */
    @Select("select * from xxl_job_group where namespace = #{namespace}")
    List<XxlJobGroup> selectByNamespace(@Param("namespace") String namespace);

    /**
     * 统计同一namespace下的job_group数量
     * @param id job_group id
     * @return int
     */
    int selectCountOfNamespaceById(@Param("id") int id);

    /**
     * 根据命名空间删除
     * @param namespace 命名空间
     * @return int
     */
    @Delete("delete from xxl_job_group where namespace = #{namespace}")
    int deleteByNamespace(String namespace);

}
