/*-------移动函数---------------*/
void Fun_1_3(int i)/*1 位置移动到 3 位置*/
{
    char ch=Str[i+1];//获取魔方转向
    D_S_2();//Down 顺时针 转 2 圈
    if(ch=='+')//正方向
    {
        R_Shun_N();//正顺时针拧
    }
    else if(ch=='-')
    {
        R_Ni_N();//逆时针拧
    }
    else if(ch=='2')
    {
        R_S_2_N();//顺时针拧2圈
    }
}
void Fun_2_3(int i)//执行 2 位置移动到 3 位置最短路径
{
    char ch=Str[i+1];//获取转向
    D_Shun();//下边shun时针转
    if(ch=='+')//正向
    {
        R_Shun_N();//下手顺时针拧
    }
    else if(ch=='-')
    {
        R_Ni_N();//下手逆时针拧
    }
    else if(ch=='2')
    {
        R_S_2_N();//下手顺时针转180度
    }
}
void Fun_3_3(int i)
{
    char ch=Str[i+1];
    if(ch=='+')
    {
        R_Shun_N();
    }
    else if(ch=='-')
    {
        R_Ni_N();//右手逆时针拧
    }
    else if(ch=='2')
    {
        R_S_2_N();//
    }
}
void Fun_4_3(int i)
{
    char ch=Str[i+1];
    D_Ni();
    if(ch=='+')
    {
        R_Shun_N();
    }
    else if(ch=='-')
    {
        R_Ni_N();
    }
    else if(ch=='2')
    {
        R_S_2_N();
    }
}
void Fun_5_6(int i)
{
    char ch=Str[i+1];
    R_S_2();
    if(ch=='+')
    {
        D_Shun_N();
    }
    else if(ch=='-')
    {
        D_Ni_N();
    }
    else if(ch=='2')
    {
        D_S_2_N();
    }
}
void Fun_6_6(int i)
{
    char ch=Str[i+1];
    if(ch=='+')
    {
        D_Shun_N();
    }
    else if(ch=='-')
    {
        D_Ni_N();
    }
    else if(ch=='2')
    {
        D_S_2_N();
    }
}

/*---------------------步进电机转------------------*/

void R_Shun()//右手顺时针转
{
    Serial.println("right shun 90");
    Serial1.write(erhao65,20);
    delay(dtime);
    rshun90();
    Serial1.write(erhao10,20);
    delay(dtime);
    Serial1.write(yihao65,20);
    delay(dtime);
    rni90();
    Serial1.write(yihao10,20);
    delay(dtime);
    R_Z();
}
void R_Ni()//右-逆时针转
{
    Serial.println("right-ni 90");
    Serial1.write(erhao65,20);
    delay(dtime);
    rni90();
    Serial1.write(erhao10,20);
    delay(dtime);
    Serial1.write(yihao65,20);
    delay(dtime);
    rshun90();
    Serial1.write(yihao10,20);
    delay(dtime);
    R_N();
}
void R_S_2()//右-顺时针转180度
{
    Serial.println("right-shun 180");
    Serial1.write(erhao65,20);
    delay(dtime);
    rshun180();
    Serial1.write(erhao10,20);
    delay(dtime);
    R_2();
}
void D_Shun()//下---顺时针转
{
    Serial.println("down-shun 90");
    Serial1.write(yihao65,20);
    delay(dtime);
    dshun90();
    Serial1.write(yihao10,20);
    delay(dtime);
    Serial1.write(erhao65,20);
    delay(dtime);
    dni90();
    Serial1.write(erhao10,20);
    delay(dtime);
    D_Z();
}
void D_Ni()//下---逆时针转
{
    Serial.println("down-ni 90");
    Serial1.write(yihao65,20);
    delay(dtime);
    dni90();
    Serial1.write(yihao10,20);
    delay(dtime);
    Serial1.write(erhao65,20);
    delay(dtime);
    dshun90();
    Serial1.write(erhao10,20);
    delay(dtime);
    D_N();
}
void D_S_2()//下---顺时针转180度
{
    Serial.println("down-shun 180");
    Serial1.write(yihao65,20);
    delay(dtime);
    dshun180();
    Serial1.write(yihao10,20);
    delay(dtime);
    D_2();
}
/*-------------------机械爪拧---------------*/

void R_Shun_N()//右手顺时针拧
{

    Serial.println("right-shun_n 90");
    rshun90();
    Serial1.write(yihao65,20);
    delay(dtime);
    rni90();
    Serial1.write(yihao10,20);
    delay(dtime);
}
void R_Ni_N()//右-逆时针拧
{
    Serial.println("right-ni_n 90");
    rni90();
    Serial1.write(yihao65,20);
    delay(dtime);
    rshun90();
    Serial1.write(yihao10,20);
    delay(dtime);
}
void R_S_2_N()//右-顺时针拧180度
{
    Serial.println("right-shun_n 180");
    rshun180();
}
void D_Shun_N()//下---顺时针拧
{
    Serial.println("down-shun_n 90");
    dshun90();
    Serial1.write(erhao65,20);
    delay(dtime);
    dni90();
    Serial1.write(erhao10,20);
    delay(dtime);
}
void D_Ni_N()//下---逆时针拧
{
    Serial.println("down-ni_n 90");
    dni90();
    Serial1.write(erhao65,20);
    delay(dtime);
    dshun90();
    Serial1.write(erhao10,20);
    delay(dtime);
}
void D_S_2_N()//下---顺时针拧180度
{
    Serial.println("down-shun_n 180");
    dshun180();
}
