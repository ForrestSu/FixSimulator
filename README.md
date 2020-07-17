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
#### 1 How to get the project compiling on Eclipse  
- Clone this repository via GIT
- On a console, go to the git/FixSimulator folder and:
    - if you're on Linux/MacOS, run the command './gradlew build eclipse'
    - if you're on Windows, run 'gradlew.bat build eclipse'
- Import FixSimulator into your Eclipse

#### 2 How to run the app
- Run FixSim.java

###  ChangeLog
- 2020-07-17 11:31:23 upgrade quickfixj to v2.2.0