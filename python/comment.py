# 导入模块
import json
from sqlite3 import IntegrityError

import pandas as pd
import requests
import threading
import queue
import sqlalchemy
from sqlalchemy import create_engine, MetaData, Table, Column, Integer, String, DateTime, Boolean
from sqlalchemy.orm import sessionmaker
# 从 config.py 中导入 db_config 和 engine
from config import db_config, engine
from concurrent.futures import ThreadPoolExecutor



# 从数据库中查询 books 表，获取所有的 bookid
def get_bookids():
    # 创建一个元数据对象，用于反射数据库的表结构
    metadata = MetaData()
    # 反射 books 表
    books = Table('books', metadata, autoload=True, autoload_with=engine)
    # 创建一个会话对象，用于执行 SQL 语句
    Session = sessionmaker(bind=engine)
    session = Session()
    # 定义查询语句
    query = session.query(books.c.bookId)
    # 执行查询
    result = query.all()
    # 关闭会话
    session.close()
    # 返回 bookid 列表
    return [row[0] for row in result]


# 根据 bookid 构造 url，并发送请求，获取评论数据，然后解析数据，提取评论的内容，作者，时间等信息，并返回一个字典
def get_comment_data(bookid):
    # 初始化 mark
    mark = None
    # 初始化评论数据列表
    comment_data = []
    # 连续请求10次接口
    for _ in range(10):
        # 构造请求 URL
        url = f"https://forum.zongheng.com/api/forums/postlist?bookId={bookid}"
        if mark is not None:
            url += f"&mark={mark}"

        # 发送请求
        response = requests.get(url)

        # 判断响应状态码
        if response.status_code == 200:
            # 获取响应内容
            data = response.json()

            # 判断数据是否有效
            if data and data['data']:
                # 获取评论列表
                comments = data['data']['ThreadList']

                # 遍历评论列表
                for comment in comments:
                    # 提取评论的内容，作者，时间等信息
                    content = comment['content']
                    author = comment['nickName']
                    createTime = comment['createTime']

                    # 将信息组合成一个字典，包含接口返回的数据中的其他字段
                    comment_dict = {
                        'bookid': bookid,
                        'content': content,
                        'nickName': author,
                        'createTime': createTime,
                        'forumsId': comment['forumsId'],
                        'threadId': comment['threadId'],
                        'userId': comment['userId'],
                        'type': comment['type'],
                        'title': comment['title'],
                        'authorStatus': comment['authorStatus'],
                        'upvoteNum': comment['upvoteNum'],
                        'checkStatus': comment['checkStatus'],
                        'sticky': comment['sticky'],
                        'rsuv': comment['rsuv'],
                        'lockStatus': comment['lockStatus'],
                        'refThreadId': comment['refThreadId'],
                        'imageUrl': comment['imageUrl'],
                        'lastPostTime': comment['lastPostTime'],
                        'orderNum': comment['orderNum'],
                        'opStatus': comment['opStatus'],
                        'refPostId': comment['refPostId'],
                        # 'nickName': comment['nickName'],
                        'userImgUrl': comment['userImgUrl'],
                        'postNum': comment['postNum'],
                        'contentType': comment['contentType'],
                        'donateUnit': comment['donateUnit'],
                        'replyPostParentId': comment['replyPostParentId'],
                        'beRepliedUserId': comment['beRepliedUserId'],
                        'beRepliedNickName': comment['beRepliedNickName'],
                        'rpList': comment['rpList'],
                        'beRefPost': comment['beRefPost'],
                        'threadDonateType': comment['threadDonateType'],
                        'mentionedUsers': comment['mentionedUsers'],
                        'mentionedNickNames': comment['mentionedNickNames'],
                        'fansScoreLevel': comment['fansScoreLevel'],
                        'scoreLevelNickName': comment['scoreLevelNickName'],
                        'forumLeaderStatus': comment['forumLeaderStatus'],
                        'userLevel': comment['userLevel'],
                        'isClickSupport': comment['isClickSupport'],
                        'speakForbid': comment['speakForbid'],
                        'markRed': comment['markRed'],
                        'refChapterName': comment['refChapterName'],
                        'refChapterContent': comment['refChapterContent'],
                        'heatNumber': comment['heatNumber'],
                        'heatIgnore': comment['heatIgnore'],
                        'heatNumMark': comment['heatNumMark'],
                        'redPacketId': comment['redPacketId'],
                        'includeThreadList': comment['includeThreadList'],
                        'trendIds': comment['trendIds'],
                        'trendViews': comment['trendViews'],
                        'ipRegion': comment['ipRegion']
                    }
                    # 将字典添加到列表中
                    comment_data.append(comment_dict)
                # 更新 mark
                mark = data['data']['mark']
        else:
            # 数据无效，返回空
            return None
    # 返回评论数据列表
    return comment_data


# 将评论数据的字典插入到数据库的 comments 表中
def save_comment_data(comment_data):
    # 创建一个元数据对象，用于定义数据库的表结构
    metadata = MetaData()
    # 定义 comments 表
    comments = Table('comments', metadata,
                     Column('bookid', Integer, nullable=False),
                     Column('content', String(5000), nullable=False),
                     Column('nickName', String(20), nullable=False),
                     Column('createTime', String(200), nullable=False),
                     Column('forumsId', Integer, nullable=False),
                     Column('threadId', Integer, primary_key=True),
                     Column('userId', Integer, nullable=False),
                     Column('type', Integer, nullable=False),
                     Column('title', String(100), nullable=False),
                     Column('authorStatus', Integer, nullable=False),
                     Column('upvoteNum', Integer, nullable=False),
                     Column('checkStatus', Integer, nullable=False),
                     Column('sticky', Integer, nullable=False),
                     Column('rsuv', Integer, nullable=False),
                     Column('lockStatus', Integer, nullable=False),
                     Column('refThreadId', Integer, nullable=True),
                     Column('imageUrl', String(200), nullable=True),
                     Column('lastPostTime', String(200), nullable=False),
                     Column('orderNum', Integer, nullable=False),
                     Column('opStatus', Integer, nullable=False),
                     Column('refPostId', Integer, nullable=True),
                     Column('userImgUrl', String(200), nullable=True),
                     Column('postNum', Integer, nullable=False),
                     Column('contentType', Integer, nullable=False),
                     Column('donateUnit', Integer, nullable=False),
                     Column('replyPostParentId', Integer, nullable=True),
                     Column('beRepliedUserId', Integer, nullable=True),
                     Column('beRepliedNickName', String(20), nullable=True),
                     Column('rpList', String(500), nullable=True),
                     Column('beRefPost', String(500), nullable=True),
                     Column('threadDonateType', Integer, nullable=False),
                     Column('mentionedUsers', String(500), nullable=True),
                     Column('mentionedNickNames', String(500), nullable=True),
                     Column('fansScoreLevel', Integer, nullable=False),
                     Column('scoreLevelNickName', String(20), nullable=True),
                     Column('forumLeaderStatus', Integer, nullable=False),
                     Column('userLevel', Integer, nullable=False),
                     Column('isClickSupport', Integer, nullable=False),
                     Column('speakForbid', Integer, nullable=False),
                     Column('markRed', Integer, nullable=False),
                     Column('refChapterName', String(100), nullable=True),
                     Column('refChapterContent', String(500), nullable=True),
                     Column('heatNumber', Integer, nullable=False),
                     Column('heatIgnore', Integer, nullable=False),
                     Column('heatNumMark', Integer, nullable=False),
                     Column('redPacketId', Integer, nullable=True),
                     Column('includeThreadList', String(500), nullable=True),
                     Column('trendIds', String(500), nullable=True),
                     Column('trendViews', Integer, nullable=False),
                     Column('ipRegion', String(20), nullable=True)
                     )
    # 创建一个会话对象，用于执行 SQL 语句
    Session = sessionmaker(bind=engine)
    session = Session()
    # 尝试执行插入操作
    try:
        # 执行多条插入语句
        session.execute(comments.insert(), comment_data)
        # 提交事务
        session.commit()
        # 打印成功信息
        print(f"成功插入{len(comment_data)}条评论数据")
    except Exception as e:
        # 出现异常，回滚事务
        session.rollback()
        # 打印错误信息
        print(f"插入评论数据失败，原因：{e}")

    # 关闭会话
    session.close()


# 创建一个任务队列，将所有的 bookid 作为任务放入队列中，然后创建多个线程，每个线程从队列中获取一个任务，执行爬取和存储的函数，直到队列为空
# def multi_thread_crawl():
#     # 创建一个任务队列
#     q = queue.Queue()
#     # 获取所有的 bookid
#     bookids = get_bookids()
#     # 将 bookid 作为任务放入队列中
#     for bookid in bookids:
#         q.put(bookid)
#
#     # 定义一个线程的执行函数
#     def worker():
#         # 从队列中获取一个任务
#         bookid = q.get()
#         # 执行爬取和存储的函数
#         comment_data = get_comment_data(bookid)
#         if comment_data:
#             save_comment_data(comment_data)
#         else:
#             print(f"未获取到 bookid 为 {bookid} 的评论数据")
#         # 任务完成，通知队列
#         q.task_done()
#
#     # 定义线程的数量
#     num_threads = 10
#     # 创建多个线程
#     for i in range(num_threads):
#         # 创建一个线程对象
#         t = threading.Thread(target=worker)
#         # 设置为守护线程，主线程结束时，子线程也结束
#         t.setDaemon(True)
#         # 启动线程
#         t.start()
#     # 等待队列中的任务全部完成
#     q.join()

def worker(bookid):
    # 执行爬取和存储的函数
    comment_data = get_comment_data(bookid)
    if comment_data:
        save_comment_data(comment_data)
    else:
        print(f"未获取到 bookid 为 {bookid} 的评论数据")


def main():
    # 调用多线程爬取和存储的函数
    # 获取 bookid 列表
    bookids = get_bookids()

    # 创建一个线程池
    with ThreadPoolExecutor(max_workers=10) as executor:
        # 为每个 bookid 创建一个线程
        executor.map(worker, bookids)
    print(f"爬取完毕")

if __name__ == '__main__':
    main()
