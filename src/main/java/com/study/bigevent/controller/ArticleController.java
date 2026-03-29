package com.study.bigevent.controller;

import com.study.bigevent.pojo.Article;
import com.study.bigevent.pojo.PageBean;
import com.study.bigevent.pojo.Result;
import com.study.bigevent.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 根据创建人的id来添加文章
     *
     * @param article
     * @return
     */
    @PostMapping("")
    public Result addByCreateId(@RequestBody @Validated Article article) {
        articleService.addByCreateId(article);
        return Result.success();

    }

    /**
     * 根据主键id来更新元素
     *
     * @param article
     * @return
     */
    @PutMapping("")
    public Result updateById(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.updateById(article);
        return Result.success();
    }

    /**
     * 根据主键id来进行查找元素
     *
     * @param id
     * @return 返回这个List<Article>数据
     */
    @GetMapping("/detail")
    public Result<List<Article>> getDataById(Integer id) {
        List<Article> list = articleService.getDataById(id);
        return Result.success(list);
    }

    /**
     * 根据主键id来删除元素
     *
     * @param id
     * @return
     */
    @DeleteMapping("")
    public Result deleteById(Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return 返回PageBean这个对象
     */
    @GetMapping("")
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize, //没有加上这个@RequestParam这个注解的话就是表示当前就是一个必须的，如果加上required就是表示当前是可以省略掉
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state) {

        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }

}
