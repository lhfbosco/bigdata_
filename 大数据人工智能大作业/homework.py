import numpy as np
import pandas as pd
import os

#                    理解数据
#读取数据集
dataset_path = os.path.join('~/s3data/homework', 'kaggle_bike_competition_train.csv')
df_train = pd.read_csv(dataset_path)
df_train.head(10)
#查看列名及其类型

df_train.dtypes
#查看数据集的大小

df_train.shape
#查看缺失值情况

df_train.info()
#发现竟然没有缺失值...

#查看数值型数据的统计情况
df_train.describe()

#                       数据清洗
#一、对日期数据进行拆分
df_train['datetime'].head()
#年份数据无价值，因为都是20111年数据，需要拆分成月份、天数、小时
df_train['month'] = pd.DatetimeIndex(df_train.datetime).month
df_train['day'] = pd.DatetimeIndex(df_train.datetime).day
df_train['hour'] = pd.DatetimeIndex(df_train.datetime).hour
# 将'datetime'设为索引
df_train.set_index('datetime', inplace=True)
df_train.head(10)
# 二、拆分特征和标签
y = df_train.get('count')
X = df_train.drop('count', axis=1)

#                        构建模型
from sklearn.model_selection import train_test_split
from sklearn import linear_model
from sklearn import svm
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score
from sklearn.metrics import mean_squared_error as MSE
import matplotlib.pyplot as plt
# %matplotlib inline
#导入sklearn包，本次大作业主要使用三种模型来拟合数据：随机森林、SVM和线性模型
#模型评价指标选择r2_score和MSE

#划分数据集
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=23)
# 将测试集的索引取出来，方便后面与预测结果进行合并，写入文件
y_index = y_test.index
#运用r2_score和MSE对模型的的预测结果进行评估
def evl(y_test, y_pred):
    r2 = r2_score(y_test, y_pred)
    mse = MSE(y_test, y_pred)
    print("evl:{0}   MES:{1}".format(r2, mse))
#随机森林模型
RFR = RandomForestRegressor()
model = RFR.fit(X_train, y_train)
y_pred = model.predict(X_test)
print("RandomForest: ")
evl(y_test, y_pred)
#SVM模型
svc = svm.SVR().fit(X_train, y_train)
y_pred = svc.predict(X_test)
print("SVM: ")
evl(y_test, y_pred)
#线性模型
model_lm = linear_model.Ridge().fit(X_train, y_train)
y_pred = model_lm.predict(X_test)
print("Linear_Model: ")
evl(y_test, y_pred)
# 上述结果说明线性模型和随机森林模型肯能过拟合了，而SVM则欠拟合
# 绘制图像，对比预测结果与真实结果

def plot_(pred, truth):
    plt.figure(figsize=(16, 12))
    plt.title("result")

    plt.xlabel("examples")
    plt.ylabel("count")

    plt.grid()

    plt.plot(np.arange(0, 20, 1), pred[0:20], '-', color="g", linewidth=1.2,
             label="predict_count")

    plt.plot(np.arange(0, 20, 1), truth[0:20], '-', color="r", linewidth=1,
             label="ground_truth")
    plt.legend(loc="best")
    plt.show()

# 结果可视化
plot_(y_pred, y_test)

#                               保存结果
y_pred[0:10]
# 将ndarray转为dateframe, 方便与索引列合并
y_pred_df = pd.DataFrame(y_pred, columns=['count'])
y_pred_df.set_index(y_index, inplace=True)
y_pred_df.head()
# 将结果保存成csv文件
load_path = os.path.join('~/s3data/homework', 'result.csv')
y_pred_df.to_csv(load_path, index=True)


