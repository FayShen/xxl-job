package com.xxl.job.admin.controller;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobNamespace;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobNamespaceDao;
import com.xxl.job.core.biz.model.ReturnT;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ReturnT<String> add(XxlJobNamespace xxlJobNamespace) {

        if (!StringUtils.hasText(xxlJobNamespace.getNamespace())) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("namespace_empty"));
        }
        try {
            xxlJobNamespaceDao.insert(xxlJobNamespace);
            return ReturnT.SUCCESS;
        } catch (DuplicateKeyException ex) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("namespace_exist"));
        }
    }
}
