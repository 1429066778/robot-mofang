
import javax.swing.*;
import java.util.Scanner;
public class RubikRobot{
	public static final long serialVersionUID = 1L;
	public JTextPane jTextPane1;
	public JSpinner spinnerMaxMoves;
	public JSpinner spinnerTimeout;
	public int maxDepth = 17, maxTime = 1;
    boolean useSeparator = true;
    boolean showString = false;
    boolean inverse = false;
    boolean showLength = true;
    public static boolean flag = true;
    String result1="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String result2="";
    Search search = new Search();
    public String resolve(String color) {
    	String resulwwt="";
    	Scanner scan = new Scanner(System.in);
       //String color= scan.nextLine();//54个颜色坐标 ：cubeString
    	RubikRobot inst = new RubikRobot();
                while(flag){
                	//System.out.println("正在计算最短路径。。。。。");
                	resulwwt=inst.solveCube(color);
                }
        return resulwwt;
    }
    public String solveCube(String color) {
        
        String cubeString = color;
       // System.out.println("54个颜色坐标顺序："+cubeString);
        int mask = 0;
        mask |= useSeparator ? Search.USE_SEPARATOR : 0;
        mask |= inverse ? Search.INVERSE_SOLUTION : 0;
        mask |= showLength ? Search.APPEND_LENGTH : 0;
        long t = System.nanoTime();
        String result = search.solution(cubeString, maxDepth, 100, 0, mask);;
        long n_probe = search.numberOfProbes();
        
        while (result.startsWith("Error 8") && ((System.nanoTime() - t) < maxTime * 1.0e9)) {
            result = search.next(100, 0, mask);
            n_probe += search.numberOfProbes();
        }
        t = System.nanoTime() - t;
        
        if (result.contains("Error")) {
        	
            switch (result.charAt(result.length() - 1)) {
            case '1':
                result = "There are not exactly nine facelets of each color!";
                break;
            case '2':
                result = "Not all 12 edges exist exactly once!";
                break;
            case '3':
                result = "Flip error: One edge has to be flipped!";
                break;
            case '4':
                result = "Not all 8 corners exist exactly once!";
                break;
            case '5':
                result = "Twist error: One corner has to be twisted!";
                break;
            case '6':
                result = "Parity error: Two corners or two edges have to be exchanged!";
                break;
            case '7':
                result = "No solution exists for the given maximum move number!";
                break;
            case '8':
                result = "Timeout, no solution found within given maximum time!";
                maxDepth++;
                break;
            }
            //重新接
        } else {
            //System.out.println(String.format(result));// + jTextPane1.getText()
        
            if(String.format(result).length()<result1.length())
            	result1=String.format(result);
            if(String.format(result).length()==result1.length()) {
              System.out.println("最佳步骤:"+result1+"   "+result1.length()/2+"步");
              flag=false;
            }
        	//System.out.println(String.format("%s\n"/*, %s ms, %d probes\n*/, result/*, Double.toString((t / 1000) / 1000.0), n_probe*/));// + jTextPane1.getText()
        }
        return String.format(result);
    }
}
