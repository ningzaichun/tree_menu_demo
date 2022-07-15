package com.nzc.tree.service;

import com.nzc.tree.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author nzc
 * @since 2022-07-13
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 封装成三级菜单形式
     * @return
     */
    List<Category> listWithTree();
}
