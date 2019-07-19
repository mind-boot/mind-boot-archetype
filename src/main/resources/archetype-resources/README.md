## mind-boot 用户文档
#### mind-boot 是基于项目String Boot, 结合在实际开发中遇到的难点、痛点，经过一步步的积累，和总结，为了简化开发，开箱即用，成为“人中吕布，马中赤兔”式的威力强大的项目。我们会把一些好用的通用工具整合到项目中，让搭建项目变得一件有趣，愉快的工作。

## 创建项目
命令模板：
```
mvn archetype:generate -DarchetypeGroupId=io.github.mind-boot -DarchetypeArtifactId=mind-boot-archetype -DarchetypeVersion=1.0.1 -DarchetypeCatalog=local -DgroupId="被创建项目的groupId" -DartifactId="被创建项目的artifactId" -Dversion="被创建项目的版本"

参数说明：
-DgroupId 指定groupId
-DartifactId 指定artifactId
-Dversion 指定项目版本号

命令样例：
mvn archetype:generate -DarchetypeGroupId=io.github.mind-boot -DarchetypeArtifactId=minid-boot-archetype -DarchetypeVersion=1.0.1 -DarchetypeCatalog=local -DgroupId=io.github.mind-boot.demo -DartifactId=demo -Dversion=1.0.1
```