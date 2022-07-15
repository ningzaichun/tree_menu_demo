package com.nzc.tree.controller;


import com.nzc.tree.commons.Result;
import com.nzc.tree.commons.ResultUtil;
import com.nzc.tree.entity.Category;
import com.nzc.tree.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author nzc
 * @since 2022-07-13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping
    public Result list() {
        return ResultUtil.success(categoryService.list());
    }


    /**
     * 查询出所有的数据，以树形结构组装起来
     * @return
     */
    @GetMapping("/tree")
    public Result listTree() {
        List<Category> entities= categoryService.listWithTree();
        return ResultUtil.success(entities);

    }


}

