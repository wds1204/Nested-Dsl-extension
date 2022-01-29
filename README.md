# build-logic
组件化开发，对依赖模块进行分类管理,通过配置减少依赖编译

## 插件接入
#### 依赖插件
```groovy
buildscript {
    //...
    dependencies {
        classpath "com.smart.wu.build.logic:nested:1.0"
    }
}
```
#### 应用插件
```groovy
plugins {
    id 'nested.build.dsl'
}
```
#### 插件使用规范
在project下的`build.gradle`可以定如下分层依赖管理

```groovy
nestedDsl {

    business {//第一级节点
        usercenter {//第二级节点 可自定义
            ui {//第三级节点 
                implementation 'com.google.android.material:material:1.4.0'
            }
            biz {
                implementation 'androidx.core:core-ktx:1.7.0'
            }
            service{
                
            }
            
            api {
            }

            description "ui service api dep"
        }
        login {
            ui {}
            biz {}
            api {
                implementation 'androidx.appcompat:appcompat:1.4.0'
            }


        }
    }


    framework{
        core{
            dep{
            }
        }
    }
    base {
        baseSdk {
            dep {
                implementation "androidx.core:core:1.3.2"
            }
        }
    }


}
```
`nestedDsl`为根节点，此节点下分为固定的`framework`、`base`、`business`三个节点。`framework`、`base`、`business`这三个节点下
可自定自己的模块，比如上述例子中在`business`下定义`usercenter`和`login`业务模块。然后又把业务模块分为`ui`、`api`、`biz`、`service`,主要根据
日常组件化开发经验做出的分类。









