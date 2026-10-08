import requests
import pandas as pd
from sqlalchemy.exc import IntegrityError
from datetime import datetime
import threading
import time

from config import engine


def main():
    url = 'https://www.zongheng.com/api/rank/details'

    # 获取当前的时间的年份
    now = datetime.now()
    year = now.strftime('%Y')

    # 查询参数
    params = {
        'cateFineId': 0,
        'cateType': 0,
        'pageNum': 1,  #页码
        'pageSize': 20,
        'period': 0,
        'rankNo': '',  # 使用当前的时间的年份和月份构造成参数
        'rankType': 1
    }

    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0'
    }

    def fetch_data(rank_no):
        params['rankNo'] = rank_no
        for page in range(1, 21):
            all_data = []
            params['pageNum'] = page
            response = requests.post(url, data=params,headers=headers)
            data = response.json()
            if data['code'] == 0:
                resultList = data['result']['resultList']
                for item in resultList:
                    item['is_python'] = str(params['rankNo']) + str(item['bookId'])
                    item['rankNo'] = str(params['rankNo'])
                    all_data.append(item)
                print(f'Fetched {len(resultList)} data for rank_no {rank_no}')
                df = pd.DataFrame(all_data)
                try:
                    df.to_sql(name='month', con=engine, if_exists='append', index=False)
                    time.sleep(2)
                except IntegrityError:
                    print("Data already exists")
            else:
                print(f'Error on rank_no {rank_no}, page {page}: {data}')

    # 使用多线程进行接口的请求
    threads = []
    for month in range(1, now.month+1):
        rank_no = year + f'{month:02d}'
        thread = threading.Thread(target=fetch_data, args=(rank_no,))
        thread.start()
        threads.append(thread)
        time.sleep(45)

    # 等待所有线程完成
    for thread in threads:
        thread.join()

if __name__ == '__main__':
    main()
