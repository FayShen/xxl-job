package com.xxl.job.admin.core.model;

/**
 * 命名空间
 * @author hfy 2025/3/15 13:20
 */
public class XxlJobNamespace {

    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 描述
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
