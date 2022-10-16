- spring aop
  默认强制使用cglib做动态代理，详见配置类ClassProxyingConfiguration，可通过配置文件`aop.proxy-target-class=false`
  来关闭
- gradle add lombok需要添加如下依赖
  ```groovy
   //gradle add lombok must add this two dependency
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
  ```
- 日志打印的报错误问题
  > 尽量使用springboot内部集成的logback版本，省的出现如下问题
  ![](https://cdn.jsdelivr.net/gh/devilgarfield/notes@main/img/202210170341199.png)


- spring redis cache Serializer 使用jackson2JsonRedisSerializer做序列化未配置objetMapper的话
  对象反序列化时会转成LinkedHashMap而导致类型转换失败