import pandas as pd

import pandas as pd
import json
# 读取Excel文件并指定列名
df = pd.read_excel('/Users/abstergo/Desktop/ddd.xls')

# 将列名修改为需要的名称
df = df.rename(columns={'学号': 'number', '姓名': 'name', '班级': 'clas', '成绩': 'score'})

# 将数据转换为字典格式
data_dict = df.to_dict(orient='records')
data=[]
for item in data_dict:
    data.append({
                'name': item['name'],
        'number': item['number'],
        'phone': item['number'],
        'password': "tongyi0923",
        'clas':item['clas'],
        'grade': 2,
        'department': 2,
        'score':item['score']
    })

# 将字典格式转换为JSON格式
json_data = json.dumps(data, ensure_ascii=False)

print(json_data)

