package com.xxl.job.admin.controller;

import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobNamespace;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.*;
import com.xxl.job.core.biz.model.ReturnT;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hfy 2025/3/20 22:07
 */
@Controller
@RequestMapping("/namespace")
public class NamespaceController {

    @Resource
    private XxlJobNamespaceDao xxlJobNamespaceDao;

    @Resource
    private XxlJobInfoDao xxlJobInfoDao;

    @Resource
    private XxlJobLogDao xxlJobLogDao;

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;

    @Resource
    private XxlJobRegistryDao xxlJobRegistryDao;

    @GetMapping
    public String index(Model model) {
        return "namespace/namespace.index";
    }

    @ResponseBody
    @GetMapping("pageList")
    public Map<String, Object> pageList(@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                   @RequestParam("namespace") String namespace) {

        List<XxlJobNamespace> list = xxlJobNamespaceDao.pageList(namespace, offset, pageSize);
        int total = xxlJobNamespaceDao.selectCount(namespace);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>(4);
        maps.put("recordsTotal", total);		// 总记录数
        maps.put("recordsFiltered", total);	// 过滤后的总记录数
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @ResponseBody
    @PostMapping("add")
    @Transactional
    public ReturnT<String> add(XxlJobNamespace xxlJobNamespace) {

        if (!StringUtils.hasText(xxlJobNamespace.getNamespace())) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("namespace_empty"));
        }
        xxlJobNamespace.setId(null);
        try {
            xxlJobNamespaceDao.insert(xxlJobNamespace);
            //新增一个默认group
            XxlJobGroup defaultGroup = new XxlJobGroup();
            defaultGroup.setAppname("default_group");
            defaultGroup.setTitle("default");
            defaultGroup.setAddressType(0);
            defaultGroup.setUpdateTime(new Date());
            defaultGroup.setNamespace(xxlJobNamespace.getNamespace());
            xxlJobGroupDao.save(defaultGroup);
            return ReturnT.SUCCESS;
        } catch (DuplicateKeyException ex) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("namespace_exist"));
        }

    }

    @ResponseBody
    @PostMapping("remove")
    @Transactional
    public ReturnT<String> remove(Long id) {
        if (id == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "id can't be null");
        }
        XxlJobNamespace namespace = xxlJobNamespaceDao.selectById(id);
        if (namespace == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "namespace not exist");
        }
        //判断是否有任务
        String name = namespace.getNamespace();
        int i = xxlJobInfoDao.countByNamespace(name);
        if (i != 0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请先删除该命名空间下的任务");
        }
        xxlJobLogDao.deleteByNamespace(name);
        xxlJobGroupDao.deleteByNamespace(name);
        xxlJobRegistryDao.deleteByNamespace(name);
        xxlJobNamespaceDao.deleteById(id);
        return ReturnT.SUCCESS;
    }

    @ResponseBody
    @PostMapping("update")
    @Transactional
    public ReturnT<String> update(@RequestParam("id") Long id, @RequestParam(value = "description", required = false) String description) {
        xxlJobNamespaceDao.updateById(id, description);
        return ReturnT.SUCCESS;
    }

    //TODO job group为什么不需要唯一索引
}
