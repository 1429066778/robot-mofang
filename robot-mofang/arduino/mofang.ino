#include <Servo.h>
#include"duoji.h"
String recive = "";
int flag = 0;
String color = "";
String Str = "";
int one = 0;
int two = 1;
int L = 1;
int F = 2;
int R = 3;
int B = 4;
int U = 5;
int D = 6;
int platformDIR=12; 
int platformCLK=13;
int platformDIR1=10; 
int platformCLK1=11;
int i=0;//解法步骤的字符串下标
int dtime=450;
void setup() {
  // put your setup code here, to run once:
  Serial3.begin(115200);//连接openmv
  Serial1.begin(9600);
  Serial.begin(9600);//与电脑通信
  pinMode(platformCLK,OUTPUT);  
  pinMode(platformDIR,OUTPUT);
  pinMode(platformCLK1,OUTPUT);  
  pinMode(platformDIR1,OUTPUT);  

  Serial1.write(yihao10,20);
  
  Serial1.write(erhao10,20);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (flag == 0)
    flag = Begin(); //是否接收到了电脑的开始指令
  if (flag == 1) {
    seach();
  }
}
int Begin() {
  while (Serial.available()) {
    recive += char(Serial.read());
    delay(1);
  }
  if (recive.equals("b")) {
    return 1;
  }
  return 0;
}
String getresult() {
  String re = "";
  int lengthl = 0;
  //循环六次向openmv模块索取颜色信息
 if (color == "") {
    Serial1.write(yihao65,20);
    for (int i = 1; i <= 6; i++) {
      if(i==2)
      {
        dni90();
      }else if(i==3)
      {
        dni90();
      }else if(i==4)
      {
        dni90();
      }else if(i==5)
      {
        dni90();
         Serial1.write(yihao10,20);
         delay(dtime);
         Serial1.write(erhao65,20);
         delay(dtime);
        rshun90();
      }else if(i==6)
      {
        rshun180();     
      }
      Serial3.print(i);
      while (1) {
        while (Serial3.available()) {
          color += char(Serial3.read());
        }
        lengthl = color.length();
        if (lengthl == i * 9){
         //Serial.println(color);
          break;
        }
      }  
    }
    rshun90();
    Serial1.write(erhao10,20);
  }
  if (color.length() == 54)
    Serial.print(color);//将索取的颜色信息串口打印给电脑


  
  while (1) {
    while (Serial.available()) {
      re += char(Serial.read()); //串口接受电脑计算的最优路径
      delay(2);
    }
    if(re[re.length()-1]=='*')
      break;
  }
  return re;
}
