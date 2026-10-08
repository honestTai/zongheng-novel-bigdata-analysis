# 获取书籍的详细信息,纵横返回的是页面，采用be进行值的获取
# 主要获取书籍名称，书籍类型，总点击数，总推荐数，总周推荐，总字数，盟主数
# 导入requests和BeautifulSoup模块
import time
from sqlite3 import IntegrityError

import pandas as pd
import requests
from bs4 import BeautifulSoup

from config import engine
import threading
from sqlalchemy import create_engine


def get_book_details(bookId,desc):
    # 定义接口地址
    url = "https://www.zongheng.com/detail/" + bookId

    # 发送请求，获取响应
    response = requests.get(url)
    data = {
        'bookId': '',
        'totalclick': '',
        'totalrecommend': '',
        'weekrecommend': '',
        'words': '',
        'fans': '',
        'desc': '',
        'arthur': '',
        'link': '',
        'pic': '',
        'bookName': '',
        'bookType': ''
    }
    # 判断响应状态码是否为200
    if response.status_code == 200:
        # 解析HTML内容，创建BeautifulSoup对象
        soup = BeautifulSoup(response.text, "html.parser")
        data['bookId'] = bookId
        # 获取书籍名称
        book_name = soup.find("div", class_="book-info--title").span.text
        data['bookName'] = book_name
        print("书籍名称：", book_name)
        # 获取书籍类型
        book_type = soup.find("span", class_="cateFineId").text.replace("\n",'').replace(" ",'')
        data['bookType'] = book_type
        print("书籍类型：", book_type)
        # 获取包含数字和单位的div标签
        soupTotal = soup.find("div", "book-info--nums")
        # 获取所有的span标签，存储数字
        spans = soupTotal.find_all("span")
        # 获取所有的i标签，存储单位
        i = soupTotal.find_all("i")
        # 定义一个列表，存储数字和单位的对应关系
        nums = []
        # 遍历span标签和i标签，拼接数字和单位
        for span, i in zip(spans, i):
            # 获取数字
            number = span.text
            # 获取单位
            unit = i.text
            # 拼接数字和单位
            num = number + unit
            # 添加到列表中
            nums.append(num)
        # 打印列表中的元素
        data['totalclick'] = nums[0]
        data['totalrecommend'] = nums[1]
        data['weekrecommend'] = nums[2]
        data['words'] = nums[3]
        data['status'] = soup.find("span", class_="serialStatus").text.replace("\n",'').replace(" ",'')
        # 获取盟主数,有可能没有，没有就给0
        try:
            leader_count = soup.find("div", class_="detail-rank-fans-mz").span.text
        except AttributeError:
            leader_count = '0'
        data['fans'] = leader_count
        data['desc'] = desc
        data['arthur'] = soup.find("div", class_="author-info--name").text.replace("\n",'').replace(" ",'')
        data['link'] = url
        data['pic'] = soup.find("img", class_="animation-img book-info--coverImage-img").attrs['src']
        df = pd.DataFrame(data,index=[0])
        try:
            df.to_sql(name='books', con=engine, if_exists='append', index=False)
        except IntegrityError:
            print("数据存在")

    else:
        # 响应状态码不为200，打印错误信息
        print("请求失败，错误码：", response.status_code)



def main():
    # 从 recommend，month，click 表中获取 bookid 和 description 字段
    df1 = pd.read_sql('SELECT bookid, description FROM recommend', engine)
    df2 = pd.read_sql('SELECT bookid, description FROM month', engine)
    df3 = pd.read_sql('SELECT bookid, description FROM click', engine)

    # 合并数据并根据 bookid 去重
    df = pd.concat([df1, df2, df3]).drop_duplicates(subset='bookid')

    # 使用多线程获取书籍详情
    threads = []
    for _, row in df.iterrows():
        bookId = row['bookid']
        desc = row['description']
        thread = threading.Thread(target=get_book_details, args=(str(bookId), desc))
        thread.start()
        threads.append(thread)

        # 每隔1.5秒执行一次
        time.sleep(0.5)

    # 等待所有线程完成
    for thread in threads:
        thread.join()

if __name__ == '__main__':
    main()
