package com.study.bigevent.controller;

import com.study.bigevent.pojo.Category;
import com.study.bigevent.pojo.Result;
import com.study.bigevent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据创建人的id信心来添加元素
     *
     * @param category
     * @return
     */
    @PostMapping("") //上面这个就是表示当前是使用是这个Add这个接口
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        //Validated这个对应pojo类里面定义的属性
        categoryService.add(category);
        return Result.success();

    }

    /**
     * 根据创建人的id来进行查找元素
     * 并且需要返回数据值
     *
     * @return
     */
    @GetMapping("")
    public Result<List<Category>> findByCreateUserId() {
        //因为需要返回值，所以用用参数来进行接收
        List<Category> list = categoryService.findByCreateUserId();
        return Result.success(list);
    }

    /**
     * 根据主键id来进行查找元素
     * @param id
     * @return 返回这个列表，然后可以显示到这个data数据的相应对象里面去
     */
    @GetMapping("/detail")
    public Result<List<Category>> findById( Integer id) {
        List<Category> list = categoryService.findById(id);
        return Result.success(list);
    }

    /**
     * 根据主键id来进行修改元素
     * @param category
     * @return
     */
    @PutMapping("")
    public Result updateById(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 根据主键id来进行删除元素
     * @param id
     * @return
     */
    @DeleteMapping("")
    public Result deleteById(Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }
}
