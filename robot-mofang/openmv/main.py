# Blob Detection Example
#
# This example shows off how to use the find_blobs function to find color
# blobs in the image. This example in particular looks for dark green objects.

import sensor, image, time
import pyb, time
led = pyb.LED(4)
usb = pyb.USB_VCP()
from pyb import UART
uart= UART(3,115200)
uart.init(115200, bits=8, parity=None, stop=1)
thresholds = [(14, 56, 23, 90, 24, 73) , # һ������µĺ�ɫ��ֵ
             (42, 100, -92, -64, 30, 81), # һ������µ���ɫ��ֵ
              (10, 100, -24, 52, -90, -23),# һ������µ���ɫ��ֵ
            (86, 100, -43, -5, 38, 127),
              (36, 100, 26, 87, -3, 70),#��ɫ
         (42, 100, -25, 28, -25, 11)]#��ɫ


# ��ɫ1: ��ɫ����ɫ����
red_color_code = 1 # code = 2^0 = 1
# ��ɫ2: ��ɫ����ɫ����
green_color_code = 2 # code = 2^1 = 2
blue_color_code =4
yellow_color_code=8
orange_color_code=16
white_color_code=32

XH=0
xh=1
def fun(blobs):
    for blob in blobs:
    #�����ҵ���Ŀ����ɫ����
        x = blob[0]
        y = blob[1] #
        width = blob[2] # ɫ����εĿ��
        height = blob[3] # ɫ����εĸ߶�
        ares=blob[4]
        center_x = blob[5] # ɫ�����ĵ�xֵ
        center_y = blob[6] # ɫ�����ĵ�yֵ
        color_code = blob[8] # ��ɫ����

        # �����ɫ˵��
        if color_code == red_color_code :
            img.draw_string(x, y - 10, "R", color = (0xFF, 0x00, 0x00))
            return 'R'
        elif color_code == green_color_code:
            img.draw_string(x, y - 10, "G", color = (0x00, 0xFF, 0x00))
            return 'F'
        elif color_code == blue_color_code:
            img.draw_string(x, y - 10, "B", color = (0xFF, 0x00, 0xFF))
            return 'B'
        elif color_code ==yellow_color_code:
            img.draw_string(x, y - 10, "Y", color = (0xFF, 0x00, 0x0F))
            return 'D'
        elif color_code == orange_color_code:
            img.draw_string(x, y - 10, "O", color = (0xFF, 0xF0, 0xFF))
            return 'L'
        elif color_code == white_color_code:
            img.draw_string(x, y - 15, "W", color = (0x00, 0x00, 0xFF))
            return 'U'

sensor.reset() # ��ʼ������ͷ
sensor.set_contrast(+3)
sensor.set_brightness(-2)
sensor.set_saturation(+2)
sensor.set_auto_exposure(True, value=-1000)#�򿪣�True����رգ�False���Զ��ع⡣
sensor.set_pixformat(sensor.RGB565) # ѡ������ģʽ RGB565.
sensor.set_framesize(sensor.QVGA) # use QQVGA for speed.
sensor.skip_frames(10) # Let new settings take affect.
sensor.set_auto_whitebal(False) #�رհ�ƽ�⡣��ƽ����Ĭ�Ͽ����ģ�����ɫʶ���У���Ҫ�رհ�ƽ�⡣
clock = time.clock() # Tracks FPS.
send_s=""
while(True):
    clock.tick() # Track elapsed milliseconds between snapshots().
    if uart.any():
       XH = int(uart.read())
       print(XH)
    img = sensor.snapshot() # ���գ�����ͼ�񣬲ɼ�ħ������ɫ
    #led.on()
    # ��ͼ����Ѱ��������ɫ��ֵԼ��(color_thr
    # ��ͼ����Ѱ��������ɫ��ֵԼ��(color_threshold, �����ʽ), ������ֵpixel_threshold�� ɫ�������С��ֵ(area_threshold)��ɫ��
    #img.draw_rectangle([19,15,80,80])
    blob1 = img.find_blobs(thresholds, pixels_threshold=200, area_threshold=200, merge=True, roi=(234,58,38,29))
    blob2 = img.find_blobs(thresholds, pixels_threshold=200, area_threshold=200, merge=True, roi=(231,123, 52, 31))
    blob3 = img.find_blobs(thresholds, pixels_threshold=200, area_threshold=200, merge=True, roi=(225,181,35, 35))
    blob4 = img.find_blobs(thresholds, pixels_threshold=200,area_threshold=200,  merge=True, roi=(176,51, 28, 38))
    blob5 = img.find_blobs(thresholds, pixels_threshold=220, area_threshold=220, merge=True, roi=(181,125, 21, 21))
    blob6 = img.find_blobs(thresholds, pixels_threshold=200,area_threshold=200,  merge=True, roi=(172,172, 35, 27))
    blob7 = img.find_blobs(thresholds, pixels_threshold=200, area_threshold=200, merge=True, roi=(120,50,34 ,39))
    blob8 = img.find_blobs(thresholds, pixels_threshold=100, merge=True, roi=(132,124, 27, 30))
    blob9 = img.find_blobs(thresholds, pixels_threshold=200, area_threshold=200, merge=True, roi=(124,172, 31, 31))
    if blob1 and blob2  and blob3 and blob4 and blob5 and blob6 and blob7 and blob8 and blob9:
       A=fun(blob1)
       B=fun(blob2)
       C=fun(blob3)
       D=fun(blob4)
       E=fun(blob5)
       F=fun(blob6)
       G=fun(blob7)
       H=fun(blob8)
       I=fun(blob9)
       if(A and B and C and D and E and F and G and H and I):
            send_s=A+B+C+D+E+F+G+H+I
            if(XH==xh):
               xh=xh+1#���������ݺ���xh��һ��ʾ�ɼ���ϲ���������һ��Ĳɼ�
               print(XH)
               print(send_s)
               uart.write(send_s)
