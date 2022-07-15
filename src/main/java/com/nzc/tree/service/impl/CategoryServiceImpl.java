package com.nzc.tree.service.impl;

import com.nzc.tree.entity.Category;
import com.nzc.tree.mapper.CategoryMapper;
import com.nzc.tree.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    CategoryMapper categoryMapper;

    @Override
    public List<Category> listWithTree() {
        //1、查询出所有的分类
        List<Category> allCategory = baseMapper.selectList(null);
        //2、组装成父子的树形结构
        //2.1、找到所有的一级分类
        List<Category> categoriesLevel1 = allCategory.stream()
                // filter 方法就如名称一样，就是用来过滤的
                // 满足条件的会留下，到最后会返回一个流式对象
                .filter(category ->
                        category.getParentCid() == 0
                )
                // map 的理解百话点说就是可以对传入的对象做出修改
                // 这里的意思就是找出当前一级分类中的二级分类，然后再set进去
                // 最后再返回一个流式对象
                .map((menu) -> {
                    menu.setCategoryChild(getChildrens(menu, allCategory));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return categoriesLevel1;
    }


    private List<Category> getChildrens(Category root, List<Category> all) {
        List<Category> collect = all.stream()
                .filter(category -> {
                    return category.getParentCid().equals(root.getCatId());
                }).map((menu) -> {
                    menu.setCategoryChild(getChildrens(menu, all));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }
}
