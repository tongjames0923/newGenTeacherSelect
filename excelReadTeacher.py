import pandas as pd

import pandas as pd
import json
# 读取Excel文件并指定列名
df = pd.read_excel('/Users/abstergo/Desktop/各专业导师人员名单22.10.10.xlsx')

# 将列名修改为需要的名称
df = df.rename(columns={ '姓名': 'name', '工号': 'workNo', '职称': 'position'})

# 将数据转换为字典格式
data_dict = df.to_dict(orient='records')
data=[]
for item in data_dict:
    data.append({
                'name':item['name'],'workNo':item['workNo'],'phone':item['workNo'],
                'password':"tongyi0923",'pro_title':item['position'],'position':item['position'],
                'department':2
    })

# 将字典格式转换为JSON格式
json_data = json.dumps(data, ensure_ascii=False)

print(json_data)

