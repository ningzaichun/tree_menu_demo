package com.nzc.tree.service.impl;

import com.nzc.tree.entity.Category;
import com.nzc.tree.mapper.CategoryMapper;
import com.nzc.tree.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author nzc
 * @since 2022-07-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<Category> listWithTree() {
        //1、查询出所有的分类
        List<Category> allCategory = baseMapper.selectList(null);
        //2、组装成父子的树形结构
        //2.1、找到所有的一级分类
        List<Category> categoriesLevel1 = allCategory.stream().filter(category ->
                        category.getParentCid() == 0
                ).map((menu) -> {
                    menu.setCategoryChild(getChildrens(menu, allCategory));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return categoriesLevel1;
    }


    private List<Category> getChildrens(Category root, List<Category> all) {
        List<Category> collect = all.stream().filter(category -> {
                    return category.getParentCid() == root.getCatId();
                }).map(category -> {
                    category.setCategoryChild(getChildrens(category, all));

                    return category;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }
}
