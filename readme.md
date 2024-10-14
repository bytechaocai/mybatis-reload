# mybatis热加载

## 使用方式

使用mybatis-reload至少需要引入`core`模块。在javaee容器中启动时（`example/web`），需要创建一个`MyBatisReloadService`类型的bean，然后参考`MyBatisController`来编写加载方法。在springboot中运行时，只需要引入`mybatis-reload-spring-boot-starter`，并添加`mybatis.reload.enable=true`即可，然后就可以使用postman发送请求来加载映射文件了。

在完成上面的步骤后，你可能需要将加载映射的请求路径加到权限验证白名单。

如果你是不能访问互联网的内网用户，你可以将`core`和`example/web`模块的内容抄到项目里，然后使用postman发送请求来加载修改过的映射文件。

## 模块介绍

- core: 核心模块，用来热加载映射文件。
- example: 使用示例：
    - springboot: 在springboot中使用，依赖模块mybatis-reload-spring-boot-starter。
    - standard-app: 标准应用，只引入mybatis和日志。这个模块里包含最简单的mybatis应用，即一个映射接口和对应的映射文件，以及一个不绑定到接口的映射文件，同时还有一个main方法用来执行映射文件中的接口。这个模块也为example中的其他模块提供映射文件和接口。
    - web: 通过创建一个`MyBatisReloadService`类型的bean来使用热加载功能。
- mybatis-reload-spring-boot-starter: springboot starter，目前打包有问题，不能直接执行，需要继续优化。

## 已知问题

1. maven仓库

本项目不会发布到maven仓库，因此你需要拉取项目然后运行`gradlew -Dmaven.repo.local=/local/maven/directory core:publishToMavenLocal`。`maven.repo.local`属性用来定义本地maven仓库。

2. spring-boot-starter

由于我目前对gradle不熟练，因此starter还无法使用，打包时会报错没有主类，希望好心人提pr帮忙解决一下这个问题。

3. 刷新页

将来会在`example/web`模块下新增一个`mybatis-reload.html`提供刷新页面，如果你使用javaee容器启动项目，将该文件复制到`web-content`目录即可。如果使用springboot，复制到`static`目录即可。和之前一样，你可能需要对该文件设置权限校验白名单。
