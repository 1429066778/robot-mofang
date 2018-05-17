


import java.awt.AWTException;

import java.awt.Robot;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import gnu.io.*;
import java.util.Scanner;


public class ContinueRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    // ������,�ҵ�����Ƕ�������һ���̼߳�����������
    static CommPortIdentifier portId; // ����ͨ�Ź�����
    static Enumeration<?> portList; // ��Ч�����ϵĶ˿ڵ�ö��
    InputStream inputStream; // �Ӵ�������������
    static OutputStream outputStream;// �򴮿��������
    static SerialPort serialPort; // ���ڵ�����
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
     * SerialPort EventListene �ķ���,���������˿����Ƿ���������
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
        case SerialPortEvent.DATA_AVAILABLE:// ���п�������ʱ��ȡ����
            byte[] readBuffer = new byte[54];
            try {
                int numBytes = -1;
                while (inputStream.available() > 0) {
                	
                    numBytes = inputStream.read(readBuffer);

                    if (numBytes > 0) {
                    	String rec = new String(readBuffer).trim();
                       // msgQueue.add(new Date() + "��ʵ�յ�������Ϊ��-----"+ rec);
                        readBuffer = new byte[54];// ���¹��컺����󣬷����п��ܻ�Ӱ����������յ�����
                        if(rec!="") {
                        	rec_count1++;
                        	if(rec.contains("%")) {
                        		long endTime=System.nanoTime(); //��ȡ����ʱ��  
                        		System.out.println("����ʱ�䣺 "+(endTime-startTime)/1000000000+"s"); 
                        	}
                        	if(rec_count1==1)
                        		rec1=rec;
                        	if(rec_count1>2)
                        		System.out.println(rec);
                        	if(rec_count1==2) {
                        		rec2=rec;
                        	rec=rec1+rec2;
                        	System.out.println("ͼ�����ݲɼ��ɹ���������");
                        	System.out.println("����ͷɨ�����ݣ�"+rec);
                        	rec_f=rec.substring(0,9);
                        	rec_r=rec.substring(9,18);
                        	rec_b=rec.substring(18,27);
                        	rec_l=rec.substring(27,36);
                        	rec_d=rec.substring(36,45);
                        	rec_u=rec.substring(45,54);
                        	rec=rec_u+rec_r+rec_f+rec_d+rec_l+rec_b;
                        	System.out.println("��ԭ�����ݣ�"+rec);
                        	RubikRobot rb = new RubikRobot();
                        	String rev=rb.resolve(rec);
                        	outputStream.write(rev.getBytes("gbk"), 0,rev.getBytes("gbk").length);
                        	System.out.println("���跢������Ƭ��������");
                        	System.out.println("��еִ�в��裺");
                        	}
                        }
                        	
                    } else {
                        msgQueue.add("û�ж�������");
                    }
                }
            } catch (IOException e) {
            }
            break;
        }
    }

    /**
     * 
     * ͨ������򿪴��ڣ����ü������Լ���صĲ���
     * 
     * @return ����1 ��ʾ�˿ڴ򿪳ɹ������� 0��ʾ�˿ڴ�ʧ��
     */
    public int startComPort() {
        // ͨ������ͨ�Ź������õ�ǰ�����ϵĴ����б�
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            // ��ȡ��Ӧ���ڶ���
            portId = (CommPortIdentifier) portList.nextElement();

            System.out.println("�豸���ͣ�--->" + portId.getPortType());
            System.out.println("�豸���ƣ�---->" + portId.getName());
            // �ж϶˿������Ƿ�Ϊ����
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // �ж�������ڴ��ڣ��ʹ򿪸ô���
                if (portId.getName().equals("COM13")) {
                    try {
                        // �򿪴�������ΪCOM_1(��������),�ӳ�Ϊ2����
                        serialPort = (SerialPort) portId.open("COM13", 2000);

                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // ���õ�ǰ���ڵ����������
                    try {
                        inputStream = serialPort.getInputStream();
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // ����ǰ�������һ��������
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // ���ü�������Ч��������������ʱ֪ͨ
                    serialPort.notifyOnDataAvailable(true);

                    // ���ô��ڵ�һЩ��д����
                    try {
                        // �����ʡ�����λ��ֹͣλ����żУ��λ
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
            System.out.println("--------------�������߳�������--------------");
            while (true) {
                // ������������д������ݾͽ������
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
            // �����߳��������յ�������
            cRead.start();
            try {
                String st = "";
               // System.out.println("�����ֽ�����" + st.getBytes("gbk").length);
                Scanner sc = new Scanner(System.in);
                st=sc.nextLine().toString();
                System.out.println("Ӳ����ʼ��������������ɼ�������");
                Robot r = new Robot();
                r.delay(1000);
                startTime=System.nanoTime();   //��ȡ��ʼʱ��  
                outputStream.write(st.getBytes("gbk"), 0,st.getBytes("gbk").length);//���ߵ�Ƭ����ʼ����
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            return;
        }
    }
}