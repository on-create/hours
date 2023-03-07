package com.example.hours.domain;

import com.example.hours.entity.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TreeSelect 树结构实体类
 */
@Data
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点id
     */
    private Integer id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(Menu menu) {
        this.id = menu.getId();
        this.label = menu.getName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
