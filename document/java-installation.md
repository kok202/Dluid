---
title: Java installation
has_children: false
nav_order: 4
---

## Java installation
This project is developed by `Java`. 
Although here is simple guide, java installation process may be different depending on your computer environment. 
Please google `install openjdk 11`. There are many detailed articles about the Java installation process.
This project does not receive Java installation inquiries beyond the prepared Guide document. 

### Window
1. Install JDK  
First of all you need to install jdk. 
You might be wondering that you heard you need to install java, but why you should install jdk instead.
Jdk is a abbreviation of `java development kit` which is necessary program for developing java application. 
It's okay to understand `jdk = java`. 
If you just want to using `Dluid` only, you can install jre (`java runtime environment`) instead of jdk.  
    - You can download jdk 11 at here. [Download](https://adoptopenjdk.net/)   

### Mac
1. Install [Brew](https://brew.sh/)  
Brew is package manager for mac or linux. 
Brew helps installation of application much easier. 
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"

Paste above in a macOS terminal or linux shell prompt.
```
2. Add OpenJDK repository to brew.
```
brew tap AdoptOpenJDK/openjdk

Paste above in a macOS terminal or linux shell prompt.
```
3. Install OpenJDK 11
```
brew cask install adoptopenjdk11

Paste above in a macOS terminal or linux shell prompt.
```
4. Check open jdk is installed.
```
java -version

Paste above in a macOS terminal or linux shell prompt. 
If terminal print like `openjdk version` , java is installed successfully.
```