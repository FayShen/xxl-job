package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobRegistry;
import groovy.util.logging.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobRegistryDao {

    Logger log = LoggerFactory.getLogger(XxlJobRegistryDao.class);

    public List<Integer> findDead(@Param("nowTime") Date nowTime);

    public int removeDead(@Param("ids") List<Integer> ids);

    public List<XxlJobRegistry> findAll(@Param("nowTime") Date nowTime);

    public default int registrySaveOrUpdate(@Param("registryGroup") String registryGroup,
                                    @Param("registryKey") String registryKey,
                                    @Param("registryValue") String registryValue,
                                    @Param("updateTime") Date updateTime) {

        try {
            return registrySave(registryGroup, registryKey, registryValue, updateTime);
        } catch (DuplicateKeyException ex) {
            log.debug("DuplicateKeyException registryGroup={},registryKey={},registryValue={},updateTime={}",
                    registryGroup, registryKey, registryValue, updateTime);
            return registryUpdate(registryGroup, registryKey, registryValue, updateTime);
        }
    }

    public int registryUpdate(@Param("registryGroup") String registryGroup,
                              @Param("registryKey") String registryKey,
                              @Param("registryValue") String registryValue,
                              @Param("updateTime") Date updateTime);

    public int registrySave(@Param("registryGroup") String registryGroup,
                            @Param("registryKey") String registryKey,
                            @Param("registryValue") String registryValue,
                            @Param("updateTime") Date updateTime);

    public int registryDelete(@Param("registryGroup") String registryGroup,
                          @Param("registryKey") String registryKey,
                          @Param("registryValue") String registryValue);

}
