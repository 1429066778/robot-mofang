void seach()
{
    Str = getresult(); //接收到开始指令，开始向openmv读取数据，发送至电脑然后获取电脑计算的结果
    Serial.println(Str);
    while(1)
    {
      if (Str[i] == 'F')//判断当前是何步骤
      {
         switch (F)//判断当前F在那个位置
         {
           case 1:
           Fun_1_3(i);//在一位置执行1位置移动3位置函数
           break;
           case 2:
           Fun_2_3(i);//在2位置执行2位置移动到3位置函数
           break;
           case 3:
           Fun_3_3(i);//在3位置执行3位置移动到3位置函数
           break;
           case 4:
           Fun_4_3(i);//在4位置执行4位置移动到3位置函数
           break;
           case 5:
           Fun_5_6(i);//在5位置执行5位置移动到6位置函数
           break;
           case 6:
           Fun_6_6(i);//在6位置执行6位置移动到6位置函数
           break;
         }
        }
    /*----------以下同理---------------*/
        else if (Str[i] == 'B')
        {
          switch (B)
          {
            case 1:
              Fun_1_3(i);
              break;
            case 2:
              Fun_2_3(i);
              break;
            case 3:
              Fun_3_3(i);
              break;
            case 4:
              Fun_4_3(i);
              break;
            case 5:
              Fun_5_6(i);
              break;
            case 6:
              Fun_6_6(i);
              break;
      
          }
        }
        else if (Str[i] == 'L')
        {
          switch (L)
          {
            case 1:
              Fun_1_3(i);
              break;
            case 2:
              Fun_2_3(i);
              break;
            case 3:
              Fun_3_3(i);
              break;
            case 4:
              Fun_4_3(i);
              break;
            case 5:
              Fun_5_6(i);
              break;
            case 6:
              Fun_6_6(i);
              break;
          }
        }
        else if (Str[i] == 'R')
        {
          switch (R)
          {
            case 1:
              Fun_1_3(i);
              break;
            case 2:
              Fun_2_3(i);
              break;
            case 3:
              Fun_3_3(i);
              break;
            case 4:
              Fun_4_3(i);
              break;
            case 5:
              Fun_5_6(i);
              break;
            case 6:
              Fun_6_6(i);
              break;
      
          }
        }
        else if (Str[i] == 'U')
        {
          switch (U)
          {
            case 1:
              Fun_1_3(i);
              break;
            case 2:
              Fun_2_3(i);
              break;
            case 3:
              Fun_3_3(i);
              break;
            case 4:
              Fun_4_3(i);
              break;
            case 5:
              Fun_5_6(i);
              break;
            case 6:
              Fun_6_6(i);
              break;
      
          }
        }
        else if (Str[i] == 'D')
        {
          switch (D)
          {
            case 1:
              Fun_1_3(i);
              break;
            case 2:
              Fun_2_3(i);
              break;
            case 3:
              Fun_3_3(i);
              break;
            case 4:
              Fun_4_3(i);
              break;
            case 5:
              Fun_5_6(i);
              break;
            case 6:
              Fun_6_6(i);
              break;
          }
        }
        /*Serial.print("F:");Serial.print(F);
        Serial.print(" B:");Serial.print(B);
        Serial.print(" L:");Serial.print(L);
        Serial.print(" R:");Serial.print(R);
        Serial.print(" U:");Serial.print(U);
        Serial.print(" D:");Serial.println(D);*/
        i = i + 2;
        if(i>=Str.length()-1){
          Serial1.write(yihao45,20);
          Serial1.write(erhao45,20);
         Serial.println("end%");
          while(1);
        }
     }
}

