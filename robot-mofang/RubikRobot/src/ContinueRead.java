


import java.awt.AWTException;

import java.awt.Robot;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import gnu.io.*;
import java.util.Scanner;


public class ContinueRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    // 监听器,我的理解是独立开辟一个线程监听串口数据
    static CommPortIdentifier portId; // 串口通信管理类
    static Enumeration<?> portList; // 有效连接上的端口的枚举
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    private String rec_u="";
    private String rec_r="";
    private String rec_f="";
    private String rec_d="";
    private String rec_l="";
    private String rec_b="";
    private int rec_count=0;
    private String rec1="";
    private String rec2="";
    private int rec_count1=0;
    static long startTime;
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();

    @Override
    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    public void serialEvent(SerialPortEvent event) {//

        switch (event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
            byte[] readBuffer = new byte[54];
            try {
                int numBytes = -1;
                while (inputStream.available() > 0) {
                	
                    numBytes = inputStream.read(readBuffer);

                    if (numBytes > 0) {
                    	String rec = new String(readBuffer).trim();
                       // msgQueue.add(new Date() + "真实收到的数据为：-----"+ rec);
                        readBuffer = new byte[54];// 重新构造缓冲对象，否则有可能会影响接下来接收的数据
                        if(rec!="") {
                        	rec_count1++;
                        	if(rec.contains("%")) {
                        		long endTime=System.nanoTime(); //获取结束时间  
                        		System.out.println("运行时间： "+(endTime-startTime)/1000000000+"s"); 
                        	}
                        	if(rec_count1==1)
                        		rec1=rec;
                        	if(rec_count1>2)
                        		System.out.println(rec);
                        	if(rec_count1==2) {
                        		rec2=rec;
                        	rec=rec1+rec2;
                        	System.out.println("图像数据采集成功。。。。");
                        	System.out.println("摄像头扫描数据："+rec);
                        	rec_f=rec.substring(0,9);
                        	rec_r=rec.substring(9,18);
                        	rec_b=rec.substring(18,27);
                        	rec_l=rec.substring(27,36);
                        	rec_d=rec.substring(36,45);
                        	rec_u=rec.substring(45,54);
                        	rec=rec_u+rec_r+rec_f+rec_d+rec_l+rec_b;
                        	System.out.println("还原后数据："+rec);
                        	RubikRobot rb = new RubikRobot();
                        	String rev=rb.resolve(rec);
                        	outputStream.write(rev.getBytes("gbk"), 0,rev.getBytes("gbk").length);
                        	System.out.println("步骤发送至单片机。。。");
                        	System.out.println("机械执行步骤：");
                        	}
                        }
                        	
                    } else {
                        msgQueue.add("没有读到数据");
                    }
                }
            } catch (IOException e) {
            }
            break;
        }
    }

    /**
     * 
     * 通过程序打开串口，设置监听器以及相关的参数
     * 
     * @return 返回1 表示端口打开成功，返回 0表示端口打开失败
     */
    public int startComPort() {
        // 通过串口通信管理类获得当前连接上的串口列表
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            // 获取相应串口对象
            portId = (CommPortIdentifier) portList.nextElement();

            System.out.println("设备类型：--->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
            // 判断端口类型是否为串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 判断如果串口存在，就打开该串口
                if (portId.getName().equals("COM13")) {
                    try {
                        // 打开串口名字为COM_1(名字任意),延迟为2毫秒
                        serialPort = (SerialPort) portId.open("COM13", 2000);

                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置当前串口的输入输出流
                    try {
                        inputStream = serialPort.getInputStream();
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 给当前串口添加一个监听器
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置监听器生效，即：当有数据时通知
                    serialPort.notifyOnDataAvailable(true);

                    // 设置串口的一些读写参数
                    try {
                        // 比特率、数据位、停止位、奇偶校验位
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }

                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("--------------任务处理线程运行了--------------");
            while (true) {
                // 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    System.out.println(msgQueue.take());
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws AWTException {
        ContinueRead cRead = new ContinueRead();
        int i = cRead.startComPort();
        if (i == 1) {
            // 启动线程来处理收到的数据
            cRead.start();
            try {
                String st = "";
               // System.out.println("发出字节数：" + st.getBytes("gbk").length);
                Scanner sc = new Scanner(System.in);
                st=sc.nextLine().toString();
                System.out.println("硬件开始工作。。。画面采集。。。");
                Robot r = new Robot();
                r.delay(1000);
                startTime=System.nanoTime();   //获取开始时间  
                outputStream.write(st.getBytes("gbk"), 0,st.getBytes("gbk").length);//告诉单片机开始工作
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            return;
        }
    }
}