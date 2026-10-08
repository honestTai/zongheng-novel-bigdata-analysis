import { post } from './ajax';
import { get } from './ajax';

// 后台接口地址
const baseUrl = 'http://127.0.0.1:8776/api/back/';
// const baseUrl = 'https://2o9j267797.imdo.co/api/back/';

// 登录
export const login = data => post(`${baseUrl}login`, data);

// 获取数据列表
export const list = data => post(`${baseUrl}dataList`, data);

// 获取数据集
export const dataSet = data => get(`${baseUrl}dataSet/` + data);
// 保存数据
export const saveData = data => post(`${baseUrl}saveData`, data);
// 上传文件
export const uploadFile = data => post(`${baseUrl}upload`, data);

// 获取脚本列表
export const scriptList = data => post(`${baseUrl}scriptList`, data);

// 保存脚本
export const saveScript = data => post(`${baseUrl}saveScript`, data);

// 删除脚本
export const delscript = data => get(`${baseUrl}script/` + data);

// 获取项目列表
export const projectLists = data => post(`${baseUrl}projectList`, data);

// 保存项目
export const saveProject = data => post(`${baseUrl}saveProject`, data);

// 删除项目
export const delProject = data => get(`${baseUrl}project/` + data);

// 获取任务列表
export const taskLists = data => post(`${baseUrl}taskList`, data);

// 保存任务
export const saveTask = data => post(`${baseUrl}saveTask`, data);

// 删除任务
export const deltask = data => get(`${baseUrl}task/` + data);

// 获取信息
export const infos = data => get(`${baseUrl}info`);

// 启动
export const start = data => get(`${baseUrl}start/` + data);

// 获取结果
export const resultget = data => get(`${baseUrl}result`);

// 获取月份列表
export const monthList = data => post(`${baseUrl}monthList`, data);

// 获取点击列表
export const clickList = data => post(`${baseUrl}clickList`, data);
// 获取通用列表
export const commonList = data => post(`${baseUrl}commonList`, data);

// 获取评论列表
export const commentsList = data => post(`${baseUrl}commentsList`, data);
// 获取书籍
export const books = data => post(`${baseUrl}books`, data);

// 获取书籍列表
export const getBookList = data => get(`${baseUrl}books`);

// 获取所有评论 by 书籍 id
export const getAllCommentsByBookId = data => post(`${baseUrl}books/`, data);

// 书籍分析接口，获取对应的词云图
export const booksAnalysis = data => get(`${baseUrl}books/`);

//月票分析接口，获取对应的词云图
export const monthsAnalysis = data => get(`${baseUrl}months/`);
export const monthsAnalysisLine = data => get(`${baseUrl}months/${data}`);
