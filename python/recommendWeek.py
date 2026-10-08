#点击榜数据获取，周
import pandas as pd
import requests
from sqlalchemy.exc import IntegrityError

from config import engine
# 导入datetime模块，用于获取当前时间
import datetime


def main():
    url = 'https://www.zongheng.com/api/rank/details'

    # 查询参数
    params = {
        'cateFineId': 0,
        'cateType': 0,
        'pageNum': 1,  #页码
        'pageSize': 20,
        'period': 0,
        'rankNo': '',
        'rankType': 6
    }

    all_data = []

    now = datetime.datetime.now()
    # 获取当前时间所在的周数
    year = now.year
    month = now.month
    week = now.isocalendar()[1]

    for page in range(1, 21):
        print(f'Processing page {page}')
        params['pageNum'] = page
        response = requests.post(url, data=params)
        data = response.json()
        if data['code'] == 0:
            resultList = data['result']['resultList']
            for item in resultList:
                item['isPython'] = str(year)+str(month)+str(week) + str(item['bookId'])
                # 代表week
                item['type'] = 1
                all_data.append(item)
        else:
            print(f'Error on page {page}: {data}')
    df = pd.DataFrame(all_data)
    try:
        df.to_sql(name='recommend', con=engine, if_exists='append', index=False)
    except IntegrityError:
        print("数据存在")



if __name__ == '__main__':
    main()