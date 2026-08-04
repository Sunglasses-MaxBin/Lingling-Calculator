# Lingling‑Calculator


[![Release](https://img.shields.io/github/v/release/Sunglasses-MaxBin/Lingling-Calculator?style=flat-square)](https://github.com/Sunglasses-MaxBin/Lingling-Calculator/releases)
[![Stars](https://img.shields.io/github/stars/Sunglasses-MaxBin/Lingling-Calculator?style=flat-square)](https://github.com/Sunglasses-MaxBin/Lingling-Calculator/stargazers)
[![Forks](https://img.shields.io/github/forks/Sunglasses-MaxBin/Lingling-Calculator?style=flat-square)](https://github.com/Sunglasses-MaxBin/Lingling-Calculator/network/members)
[![Issues](https://img.shields.io/github/issues/Sunglasses-MaxBin/Lingling-Calculator?style=flat-square)](https://github.com/Sunglasses-MaxBin/Lingling-Calculator/issues)


 Lingling‑Calculator 是一款采用 **Material Design 3** 设计的开源安卓计算器，使用 Kotlin + Jetpack Compose 开发，追求简洁现代界面，体积轻巧，无多余权限。
 ---
 ## 技术细节
 本项目基于现代 Android 开发技术栈构建，遵循 MD3 设计规范，无多余第三方重型依赖。
 1. **Material Design 3 UI 界面**
     - 完全遵循 Material3 设计语言，支持深色模式，动态色彩适配。
     - 使用 Jetpack Compose 声明式UI，布局简洁，响应式按钮组件。
 2. **ViewModel 状态管理**
     - 使用 `ViewModel` 保存计算器运算状态，屏幕旋转不会丢失计算输入。
     - 运算逻辑与界面完全分离，便于后续扩展功能（小数、负数、连续运算）。
 3. **轻量特性**
     - 最小SDK版本：Android 14
     - 仅申请基础应用权限，无网络、读取存储等多余权限。
     - R8代码压缩，APK体积精简，仅5mb左右。
 ---
 ## ✨ 功能特性
 - 基础加减乘除四则运算
 - 小数点运算、清零、退格删除
 - MD3深色主题界面
 - 屏幕旋转状态保存
 - 开源 MIT 协议，可自由二次修改分发
 ## 使用方法
 1. 前往release，下载apk文件
 2. 在安卓设备安装 APK
 3. 直接打开即可使用计算器

 ## 编译构建

 - Android Studio Hedgehog+
 - JDK17