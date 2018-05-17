void rshun90()
{
  digitalWrite(platformDIR1,1);
  for(u16 i=0;i<200;i++)
  {  
    digitalWrite(platformCLK1,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK1,LOW);    
    delayMicroseconds(400);
  } 
}
void rshun180()
{
  digitalWrite(platformDIR1,1);
  for(u16 i=0;i<400;i++)
  {  
    digitalWrite(platformCLK1,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK1,LOW);    
    delayMicroseconds(400);
  } 
}
void rni90()
{
  digitalWrite(platformDIR1,0);
  for(u16 i=0;i<200;i++)
  {  
    digitalWrite(platformCLK1,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK1,LOW);    
    delayMicroseconds(400);
  } 
}

void dshun90()
{
  digitalWrite(platformDIR,1);
  for(u16 i=0;i<200;i++)
  {  
    digitalWrite(platformCLK,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK,LOW);    
    delayMicroseconds(400);
  }  
}
void dshun180()
{
  digitalWrite(platformDIR,1);
  for(int i=0;i<400;i++)
  {  
    digitalWrite(platformCLK,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK,LOW);    
    delayMicroseconds(400);
  }  
}
void dni90()
{
  digitalWrite(platformDIR,0);
  for(int i=0;i<200;i++) 
  {  
    digitalWrite(platformCLK,HIGH);   
    delayMicroseconds(400);
    digitalWrite(platformCLK,LOW);    
    delayMicroseconds(400);
  }  
}
