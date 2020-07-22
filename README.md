# FixSimulator
This a FIX simulator,Support Order actions like :**Cancel ,Reject, Fill, CancelAck, CancelReject, etc!**<br/>
Of course, there are some minor problems, If you want to improve it,Welcome add issue or PR!   
Enjoy it.
Please Attention Just for **Fix4.2**

### Unsupported
There are five common Order actions do not support now ! `(2017-02-09)`
>Modify Fill / Cancel Fill  
>Fill-Allocation / Ack Fill-Allocation / Reject Fill-Allocation 

### Requirements

- JRE 8
- quickfixj v2.2.0
- Gradle 4.8.1

### Author
 SunQuan

### Screenshot
![Effection](https://github.com/ForrestSu/FixSimulator/raw/master/images/screenshot.png)


### For Developer
#### 1 How to build? 

- Clone this repository via GIT

```sh
$ cd FixSimulator
# if on Linux/MacOS
$ ./gradlew build  
# if on Windows
> gradlew.bat build
```

#### 2 How to run the app
```sh
$ cd build/libs 
$ java -jar FixSimulator-0.1.jar
```

###  ChangeLog
- 2020-07-17 11:31:23 upgrade quickfixj to v2.2.0