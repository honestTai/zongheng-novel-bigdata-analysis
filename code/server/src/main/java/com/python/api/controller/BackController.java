package com.python.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.python.api.bean.dto.BookAnalysis;
import com.python.api.entity.Script;
import com.python.api.entity.Task;
import com.python.api.entity.User;
import com.python.api.bean.general.result.Result;
import com.python.api.bean.general.result.ResultException;
import com.python.api.bean.general.result.ResultStatus;
import com.python.api.bean.vto.SearchVto;
import com.python.api.service.*;
import com.python.api.service.impl.BooksServiceImpl;
import com.python.config.filter.isLogin;
import com.python.util.encryption.MD5Util;
import com.python.util.jwt.JwtUtil;
import com.python.api.bean.dto.InfoDto;
import com.python.api.bean.dto.UserLoginDto;
import com.python.api.entity.*;
import com.python.util.spark.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/back")
public class BackController {

    @Autowired
    UserService userService;


    @Autowired
    ScriptService scriptService;
    @Autowired
    TaskService taskService;

    private String filePath = "G:/system/python/";

    /**
     * 用户登录
     * @param admin 用户信息
     * @return 登录成功后的用户信息和JWT签名
     * @throws ResultException 登录失败时抛出异常
     */
    @PostMapping("/login")
    public UserLoginDto userLoginDto(@RequestBody User admin) throws ResultException {
        User adminInfo = userService.getOne(new QueryWrapper<User>().eq("username", admin.getUsername()).eq("password", MD5Util.getMD5(admin.getPassword())));
        if (adminInfo == null) {
            throw new ResultException(ResultStatus.ERROR_NUM_PWD);
        } else {
            return new UserLoginDto(adminInfo, JwtUtil.sign(adminInfo.getUsername(), adminInfo.getPassword()));
        }
    }


    /**
     * 获取脚本列表
     * @param searchVto 搜索条件
     * @return 分页后的脚本列表
     */
    @PostMapping("/scriptList")
    @isLogin
    public Page<Script> scriptList(@RequestBody SearchVto searchVto) {
        QueryWrapper<Script> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().equals("")) {
            queryWrapper.like("script_name", searchVto.getLikeString());
        }
        return scriptService.page(new Page<Script>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
    }


    /**
     * 保存脚本
     *
     * 路径：/saveScript
     * 请求方法：POST
     *
     * @param script 要保存的脚本对象
     */
    @PostMapping("/saveScript")
    @isLogin
    public void saveScript(@RequestBody Script script) {
        scriptService.save(script);
    }



    @PostMapping("/upload")
    @ResponseBody
    public Result uploadImgAddUser(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        // 获取上传文件的原始文件名
        String fileName = uploadFile.getOriginalFilename();
        // 生成新的文件名，格式为日期时间+原始文件名
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        // 添加时间戳以避免文件名重复
        String path = filePath + fileName;
        // 创建文件路径
        java.io.File dest = new java.io.File(path);
        try {
            // 将上传文件保存到指定路径
            uploadFile.transferTo(dest);
            // 构造返回的文件路径Url
            return Result.success(path);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }



    /**
     * 根据脚本ID获取脚本信息
     *
     * @param id 脚本ID
     */
    @GetMapping("/script/{id}")
    @isLogin
    public void script(@PathVariable("id") Integer id) {
        scriptService.removeById(id);
    }


    /**
     * 根据项目ID获取项目信息并删除
     *
     * @param id 项目ID
     */
    @GetMapping("/project/{id}")
    @isLogin
    public void project(@PathVariable("id") Integer id) {
        scriptService.removeById(id);
    }



    /**
     * 根据任务ID获取任务信息并删除
     *
     * @param id 任务ID
     */
    @GetMapping("/task/{id}")
    @isLogin
    public void task(@PathVariable("id") Integer id) {
        taskService.removeById(id);
    }


    /**
     * 保存任务
     *
     * @param task - 要保存的任务对象
     */
    @PostMapping("/saveTask")
    @isLogin
    public void saveTask(@RequestBody Task task) {
        task.setTaskStatus("待执行");
        taskService.save(task);
    }


    /**
     * 任务列表
     * 根据搜索条件获取任务列表
     *
     * @param searchVto 搜索条件
     * @return 任务列表
     */
    @PostMapping("/taskList")
    @isLogin
    public Page<Task> taskList(@RequestBody SearchVto searchVto) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().equals("")) {
            queryWrapper.like("task_name", searchVto.getLikeString());
        }
        Page<Task> taskPage = taskService.page(new Page<Task>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
        for (Task record : taskPage.getRecords()) {
            record.setScript(scriptService.getById(record.getTaskScriptId()));
        }
        return taskPage;
    }


    /**
     * 获取所有脚本数据
     */
    @GetMapping("/info")
    @isLogin
    public InfoDto info() {
        return new InfoDto(scriptService.list());
    }


    /**
     * 执行python脚本
     */
    @GetMapping("/start/{id}")
    @isLogin
    public void start(@PathVariable("id") Integer id) {
        taskService.start(id);
    }

    @Autowired
    MonthService monthService;

    @Autowired
    ClickService clickService;

    @Autowired
    RecommendService recommendService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    BooksServiceImpl booksService;

    /******数据集的展示*******/
    @PostMapping("/monthList")
    @isLogin
    public Page<Month> monthList(@RequestBody SearchVto searchVto) {
        // 创建查询包装类
        QueryWrapper<Month> queryWrapper = new QueryWrapper();
        // 若搜索字符串不为空，则进行模糊查询
        if (!searchVto.getLikeString().isEmpty()) {
            queryWrapper.like("bookName", searchVto.getLikeString()).or().like("pseudonym", searchVto.getLikeString()).or().like("cateFineName", searchVto.getLikeString());
        }
        // 若排名号不为空，则进行等于查询
        if(!searchVto.getRankNo().isEmpty()){
            queryWrapper.eq("rankNo",searchVto.getRankNo());
        }
        // 根据排序方式确定查询顺序
        if(searchVto.getOrderBy().equals("1")){
            queryWrapper.orderByDesc("number").orderByDesc("rankNo");
        }else {
            queryWrapper.orderByAsc("number").orderByDesc("rankNo");
        }

        // 执行分页查询操作并返回结果
        return monthService.page(new Page<Month>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
    }



    /**
     * 点击列表页
     *
     * @param searchVto 搜索条件
     * @return 页面数据和总记录数
     */
    @PostMapping("/clickList")
    @isLogin
    public Page<Click> clickPage(@RequestBody SearchVto searchVto) {
        QueryWrapper<Click> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().isEmpty()) {
            queryWrapper.like("bookName", searchVto.getLikeString()).or().like("pseudonym", searchVto.getLikeString()).or().like("cateFineName", searchVto.getLikeString());
        }
        if(!searchVto.getIsPython().isEmpty()){
            queryWrapper.eq("isPython",searchVto.getIsPython());
        }
        if(!searchVto.getType().isEmpty()){
            queryWrapper.eq("type",Integer.valueOf(searchVto.getType()));
        }
        if(searchVto.getOrderBy().equals("1")){
            queryWrapper.orderByDesc("number").orderByDesc("rankNo");
        }else {
            queryWrapper.orderByAsc("number").orderByDesc("rankNo");
        }

        return clickService.page(new Page<Click>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
    }



    /**
     * 常用推荐列表
     *
     * @param searchVto 搜索VTO对象
     * @return 分页后的推荐列表
     */
    @PostMapping("/commonList")
    @isLogin
    public Page<Recommend> commonList(@RequestBody SearchVto searchVto) {
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().isEmpty()) {
            queryWrapper.like("bookName", searchVto.getLikeString()).or().like("pseudonym", searchVto.getLikeString()).or().like("cateFineName", searchVto.getLikeString());
        }
        if(!searchVto.getIsPython().isEmpty()){
            queryWrapper.eq("isPython",searchVto.getRankNo());
        }
        if(!searchVto.getType().isEmpty()){
            queryWrapper.eq("type",Integer.valueOf(searchVto.getType()));
        }
        if(searchVto.getOrderBy().equals("1")){
            queryWrapper.orderByDesc("number").orderByDesc("rankNo");
        }else {
            queryWrapper.orderByAsc("number").orderByDesc("rankNo");
        }

        return recommendService.page(new Page<Recommend>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
    }

    /**
     * 获取评论列表
     * @param searchVto 搜索条件
     * @return 评论列表的分页对象
     */
    @PostMapping("/commentsList")
    @isLogin
    public Page<Comments> commentsList(@RequestBody SearchVto searchVto) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().isEmpty()) {
            queryWrapper.like("title", searchVto.getLikeString()).or().like("ipRegion", searchVto.getLikeString()).or().like("content", searchVto.getLikeString());
        }
        Page<Comments> page = commentsService.page(new Page<Comments>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
        page.getRecords().forEach(x -> {
            Books books = booksService.getOne(new QueryWrapper<Books>().eq("bookId", x.getBookId()));
            if (Objects.nonNull(books)) {
                x.setBookName(books.getBookName());
            }
        });
        return page;
    }


    /**
     * 书籍的POST请求处理方法
     * @param searchVto 搜索条件
     * @return 符合搜索条件的书籍列表页签
     */
    @PostMapping("/books")
    @isLogin
    public Page<Books> books(@RequestBody SearchVto searchVto) {
        QueryWrapper<Books> queryWrapper = new QueryWrapper();
        if (!searchVto.getLikeString().isEmpty()) {
            queryWrapper.like("arthur", searchVto.getLikeString()).or().like("bookName", searchVto.getLikeString()).or().like("descinfo", searchVto.getLikeString());
        }
        Page<Books> page = booksService.page(new Page<Books>(searchVto.getPageIndex(), searchVto.getPageSize()), queryWrapper);
        return page;
    }


    /**
     * 获取所有图书列表
     *
     * @return 所有图书列表
     */
    @GetMapping("/books")
    @isLogin
    public List<Books> books() {
        return booksService.list();
    }


    @Autowired
    SentimentAnalysis sentimentAnalysis;


    /**
     * 根据书籍ID获取评论列表
     *
     * @param searchVto 搜索参数对象
     * @return 评论列表
     */
    @PostMapping("/books/")
    @isLogin
    public List getCommentsByBookId(@RequestBody SearchVto searchVto) {
        return sentimentAnalysis.run(searchVto.getBookId());

    }


    /**
     * 获取图书分析信息
     *
     * @return 返回图书分析信息
     */
    @GetMapping("/books/")
    @isLogin
    public BookAnalysis booksAnalysis() throws ExecutionException, InterruptedException {
        return booksService.BookAnalysis();
    }


    /**
     * 获取月票榜单趋势分析
     *
     * @return 返回月票榜单分析数据
     */
    @GetMapping("/months/")
    @isLogin
    public BookAnalysis months() throws ExecutionException, InterruptedException {
        return booksService.months();
    }

    /**
     * 获取月票榜单趋势分析
     *
     * @return 返回月票榜单分析数据
     */
    @GetMapping("/months/{bookId}")
    @isLogin
    public BookAnalysis months(@PathVariable("bookId") String bookId) throws ExecutionException, InterruptedException {
        return booksService.monthsLine(bookId);
    }
}
