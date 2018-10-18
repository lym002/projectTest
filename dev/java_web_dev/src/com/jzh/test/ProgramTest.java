package com.jzh.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Notice:
 * <BR> 1. 仅限使用以下package:
 *		java.lang.*, java.io.*, java.math.*, java.text.*, java.util.*
 * <BR> 2. 请勿变更 package名，类名，test()定义。 
 *
 */
public class ProgramTest {

	/**
	 * 请在此方法内完成代码，但可以增加自己的私有方法。
	 * 数据文件inputFile的内容格式为一行一条数据，每条数据有2个字段用逗号分隔，第1个字段为排序用的Key，第二个字段为value。
	 * 换行符为'\n'。
	 * 数据内容举例如下:
	 * abe,xmflsflmfmlsmfs
	 * abc,xmlmxlkmffhf
	 * 8fj3l,xxjfluu313ooo11
	 * 
	 * 注意点: 1.本次的测试数据内容都是ASCII字符，无中文汉字.所以不必考虑encoding。
	 *        2.本次的测试数据中,key的最大长度8，value的最大长度32。
	 * 
	 * 排序以key的升序，使用String.compareTo()来比较key的大小。最后排序完成的数据写入outputFile。
	 * @param inputFile 输入文件
	 * @param outputFile 输出文件
	 * @param tempFile 临时文件。处理的数据量大的时候，可能会需要用到临时文件。
	 * @throws Exception
	 */
	public static void test(File inputFile, File outputFile, File tempFile) throws Exception {
		//TODO ====================== YOUR CODE HERE ======================
		   Map<String, String> map = new TreeMap<String, String>(
	                new Comparator<String>() {
	                    public int compare(String obj1, String obj2) {
	                        return obj1.compareTo(obj2);
	                    }
	                });
		   BufferedInputStream fis = new BufferedInputStream(new FileInputStream(inputFile));    
		   BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),10*1024*1024);  
		   BufferedWriter out=null;

		    
           out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile,true)));  //追加的方法
		   String s=new String();       
		  while((s=reader.readLine())!=null)
	      {
			
			 String[] ss=s.split(",");
	           map.put(ss[0],ss[1]);
	                      
		          out.write(ss[0]+ss[1]);
		          out.write("\n");
		          ss=null; 
		           }
		  reader.close(); 
		
		//=================================================================
	}
	
	
	//TODO ====================== YOUR CODE HERE (You can add private method if need) ======================
	
	public static void main(String[] args) throws Exception {
		Long start = System.currentTimeMillis();
		File file = new File("D://input.data");
		File file1 = new File("D://input1.data");
		File file2 = new File("");
		test(file, file1, file2);
		Long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	//=================================================================
	
}
