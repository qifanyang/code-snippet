### 控制台
具备的能力
- 机器发现
- 健康情况管理
- 监控(单机和集群)
- 规则管理和推送

部署(官方单机,集群需改造)
- 下载: https://github.com/alibaba/Sentinel/releases
- 启动:java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar

客户端接入
- 客户端使用Transport 模块来与 Sentinel 控制台进行通信,以及本地启动httpSever用于控制台查询
- classpath下新建sentinel.properties,指定dashbord地址 csp.sentinel.dashboard.server=ip:port 

监控信息
- 簇点链路(单机调用链路):控制台实时拉取客户端资源运行情况,客户端存在内存中的数据
- 实时监控(集群聚合):同一个服务下所有机器的簇点信息会被汇总,秒级展示

规则管理及推送  
**原始模式**
- 规则管理:控制台通过接入端暴露的HTTP API查询接入端存在内存中的规则,控制台不用存储全部接入端数据
- 规则推送:控制台通过接入端暴露的HTTP API更新规则,存在内存中(不安全)  
**pull模式**  
**push模式**

鉴权
- 通过sentinel.properties配置账号密码,默认都是sentinel
- sentinel.dashboard.auth.username=sentinel
- sentinel.dashboard.auth.password=sentinel

控制台配置项
- auth.enable=true, 默认true
- 


框架集成保护资源(springMVC)

显示使用API保护资源


