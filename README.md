# bigdata-一班李鸿飞
# 5.21
1、今天完成了哪些任务？<br/>
今天实现了作业的第一部分要求，即实现了本地目录与S3桶的同步：本地目录增加文件、删除文件和修改文件时，桶里的内容也发生相应的变化<br>
2、今天遇到了哪些问题? <br/>
在一开始启动时，发现本地目录的文件有肯能不是S3桶里的文件内容一致 <br/>
3、您又是如何解决这些问题的? <br/>
本来打算找出相较于本地目录，S3桶里缺少的文件，然后上传缺少的文件即可，但是有肯能的情况是：本地与桶里均含有此文件，但是修改时间不同，即本地的刚刚更新过，而桶里的文件还没更新，这时候用上述方法显然处理不了此问题，故决定初始化时，都先删除桶里所有文件，再上传本地所有的文件上去。
# 5.22
1、今天完成了哪些任务？<br/>
今天实现了作业的第二部分的要求，即实现了当文件大小大于20M时的分片上传与下载<br/>
2、今天遇到了哪些问题? <br/>
今天一开始在MAC电脑上调试，觉得代码没有问题，但是死活报错，一直出现文件找不到的问题，一开始以为是路径写错了或者别的小错误，说实话有点搞心态了，最终我是在解决不了，晚上换了window机器，一调试果然是mac电脑自身的问题，算是有惊无险吧。<br/>
3、您又是如何解决这些问题的? <br/>
换了台电脑，我决定放弃MAC了，.DS_store文件是真的奇奇怪怪。
# 5.25
1、今天完成了哪些任务？<br/>
今天完成了项目ppt的内容<br/>
2、今天遇到了哪些问题? <br/>
小组的分工明确，大家都积极参与，小问题就是肯能有一些事宜或者细节有些组员未必清楚<br/>
3、您又是如何解决这些问题的? <br/>
积极询问组员相关事宜，完成文档、ppt的编写
# 5.26
1、今天完成了哪些任务？<br/>
今天完成了云储存项目的所有内容和第二周布置的扩展题<br/>
2、今天遇到了哪些问题? <br/>
第一道拓展题，一直想要RDDfilter.values进行相加，但是数据类型不支持<br/>
3、您又是如何解决这些问题的? <br/>
最后我改用.max()._2转为为int进行加法运算；其他扩展题没遇到太多难题<br/>
# 5.27
1、今天完成了哪些任务？<br/>
今天完成了第二周布置的扩展题和预习了下一周的内容<br/>
2、今天遇到了哪些问题? <br/>
第二题刚开始建外表时老是报错<br/>
3、您又是如何解决这些问题的? <br/>
由于家里WiFi坏了，导致上网有困难，暂时没做出来；其他扩展题没遇到太多难题<br/>
# 5.28
1、今天完成了哪些任务？<br/>
今天把扩展题都写完了和完成了下一周实操五的内容<br/>
2、今天遇到了哪些问题? <br/>
昨天建表出的有出小错误<br/>
3、您又是如何解决这些问题的? <br/>
过滤数据文件中的“，”即可；其他没遇到多大问题<br/>
# 5.29
1、今天完成了哪些任务？<br/>
今天把扩展题检查了一遍,顺便自学了spark的相关知识<br/>
2、今天遇到了哪些问题? <br/>
没有问题<br/>
3、您又是如何解决这些问题的? <br/>
没有问题<br/>
# 6.1
1、今天完成了哪些任务？<br/>
今天做了第三周扩展题的一部分,顺便和组里讨论了大作业的情况<br/>
2、今天遇到了哪些问题? <br/>
今天实操八的内容一直连不上mysql<br/>
3、您又是如何解决这些问题的? <br/>
将数据导到MySQL时，先要退出MySQL；然后代码中passwaord后面不用双引号<br/>
# 6.2
1、今天完成了哪些任务？<br/>
今天做了实训二（5-8）扩展题，实操三的部分扩展题和部分大作业<br/>
2、今天遇到了哪些问题? <br/>
没遇到什么问题，但是别的同学与我说他实操二最后一个扩展题一直报错，后来和他看了一会，发现我写的和我也一样，但是报了一个不知道什么的错误，最后问了老师才得知是内网解析不了的问题<br/>
3、您又是如何解决这些问题的? <br/>
遇到不会的先自己找资料，然后还不会的话会选择求助同学<br/>
# 6.3
1、今天完成了哪些任务？<br/>
今天做了实训三的扩展题和学习了大作业要用到的SpringBoot框架<br/>
2、今天遇到了哪些问题? <br/>
遇到了不少问题，一开始SpringBoot登上sparksql、查询数据库所有的表时都出现了问题<br/>
3、您又是如何解决这些问题的? <br/>
在同学的帮助下解决了问题<br/>
# 6.4
1、今天完成了哪些任务？<br/>
今天做了实训三的扩展题和大作业的内容<br/>
2、今天遇到了哪些问题? <br/>
对Scala语言了解不够，窗口函数等方法未能熟练掌握<br/>
3、您又是如何解决这些问题的? <br/>
通过查阅资料，知道了scala的语法并解决了问题<br/>
# 6.5
1、今天完成了哪些任务？<br/>
今天做了实训三的扩展题和大作业的内容<br/>
2、今天遇到了哪些问题? <br/>
Flink的接口不太熟悉，为了完成扩展二去查阅了官网文档<br/>
3、您又是如何解决这些问题的? <br/>
通过查阅资料，了解了Flink的用法<br/>
# 6.8
1、今天完成了哪些任务？<br/>
今天完成了实训三后面的课程，对流式计算有了更深的理解；同时开始着手做大作业，做了一部分了<br/>
2、今天遇到了哪些问题? <br/>
今天去网上查了关于kafka的文章，对kafka更加了解；因为电脑是MAC系统，上课听到老师说到明天要用到s3挂载，因此我去试着让s3 bucket挂载到我硬盘上，但是有两个问题：1️⃣brew的速度实在是太慢了，根本用不了 2️⃣下载完s3fs后依然不太明天该怎么挂载<br/>
3、您又是如何解决这些问题的? <br/>
brew太慢的问题查了百度，找了很多方法都不行，最后找到一个号文章终于解决，把我的homebrew换成了华中科技大学的镜像，重新brew install 一下子就下完了；s3挂载的问题，我看群上说好像又不用我们自己挂载了，因此后面我也没继续尝试了<br/>
# 6.9
1、今天完成了哪些任务？<br/>
今天学习了机器学习相关的知识<br/>
2、今天遇到了哪些问题? <br/>
大作业的归类想用JSON的形式提取出不同字段内容，但是系统报match的错误<br/>
3、您又是如何解决这些问题的? <br/>
上网查了资料，好像是因为空值的原因，但我看数据好像没有空值，暂时没解决这个问题，要是不行就换另一种方法吧<br/>
# 6.10
1、今天完成了哪些任务？<br/>
已基本完成大作业情况<br/>
2、今天遇到了哪些问题? <br/>
昨天遇到的问题依然存在<br/>
3、您又是如何解决这些问题的? <br/>
好像是因为kafka队列里消息为空会报错，在这之前的都可以正确按消息关键字段归类<br/>
# 6.11
1、今天完成了哪些任务？<br/>
已基本完成大作业情况<br/>
2、今天遇到了哪些问题? <br/>
题目的理解是好像出现了偏差<br/>
3、您又是如何解决这些问题的? <br/>
重新审题并询问同学对题目的理解<br/>
# 6.12
1、今天完成了哪些任务？<br/>
提交了大作业<br/>
2、今天遇到了哪些问题? <br/>
consumer消费的时候出问题，好像是跟数据有关<br/>
3、您又是如何解决这些问题的? <br/>
重启程序，检查了代码，一下没什么问题了<br/>
# 6.15
1、今天完成了哪些任务？<br/>
今天学了机器学习中的模型评估、pipeline技术和结出了深度学习，同时课后我也着手开始做起了大作业<br/>
2、今天遇到了哪些问题? <br/>
回归模型的评价指标不确定用哪一个<br/>
3、您又是如何解决这些问题的? <br/>
通过查阅资料，了解了不同指标的定义和使用场景<br/>
# 6.16
1、今天完成了哪些任务？<br/>
今天了解了数据治理相关的知识，同时也做了部分大作业<br/>
2、今天遇到了哪些问题? <br/>
数据治理实操手册上有一些操作报错<br/>
3、您又是如何解决这些问题的? <br/>
看了群里的同学有相关的经历 解决了<br/>
# 6.17
1、今天完成了哪些任务？<br/>
今天完成了大作业的流程图和代码调试<br/>
2、今天遇到了哪些问题? <br/>
在用不同模型进行预测时，出现了过拟合的现象<br/>
3、您又是如何解决这些问题的? <br/>
加入正则化参数，降低模型复杂度<br/>
# 6.18
1、今天完成了哪些任务？<br/>
今天完成了大作业的全部内容<br/>
2、今天遇到了哪些问题? <br/>
保存结果时没有索引<br/>
3、您又是如何解决这些问题的? <br/>
在拆分测试集的时候取出索引<br/>
# 6.19
1、今天完成了哪些任务？<br/>
今天把实训五中的步骤完整的过了一遍，因为之前上课好像有些地方操作失败了<br/>
2、今天遇到了哪些问题? <br/>
没有，之前页面老是加载不出来，今天换了网速好的地方操作<br/>
3、您又是如何解决这些问题的? <br/>
顺利的解决的问题了，为下一次实操打下基础<br/>
# 6.20
1、今天完成了哪些任务？<br/>
今天继续学习了数据治理后续的课程<br/>
2、今天遇到了哪些问题? <br/>
物化模型老是失败<br/>
3、您又是如何解决这些问题的? <br/>
肯能是家里网速的问题，上一次的实操也是因为家里网速不行，物化模型总是失败，明天去图书馆试试<br/>
