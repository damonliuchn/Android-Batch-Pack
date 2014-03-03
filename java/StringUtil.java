

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class StringUtil {
	/** 
     * 追加文件：使用FileWriter 
     *   
     * @param fileName 
     * @param content 
     */  
    public static void string2fileAdd(String fileName, String content) {   
        try {   
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content+"\n");   
            writer.close();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    } 
	/**
	 * 是否有中文
	 * @param c
	 * @return
	 */
	public static boolean hasChinese(String c) {
		for (int i = 0; i < c.length(); i++) {
			int b = c.charAt(i);
			if (b >= 0x4E00 && b <= 0x9FA5) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		if(StringUtil.isBlank(str))
			return str;
		Pattern pattern = Pattern.compile("<([^>]*)>");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);

		return sb.toString().replaceAll(" \\n *", "").replace("&ldquo;", " ").replace("&rdquo;", " ");
	}
	/**
	 * 去除字符串首尾的空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.trim();
	}
	/**
	 * 去除文字中的空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeSpace(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.trim().replaceAll("\\s", "");
	}
	/**
	 * 对象不为空且长度不为空
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).length() == 0;
		}
		if (obj instanceof Collection<?>) {
			return ((Collection<?>) obj).size() == 0;
		}
		if (obj instanceof byte[]) {
			return ((byte[]) obj).length == 0;
		}
		if (obj instanceof int[]) {
			return ((int[]) obj).length == 0;
		}
		return false;
		//throw new IllegalArgumentException("undefined type: "
		//		+ obj.getClass().getName());
	}

	public static String MD5(String str) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return toHex(messageDigest.digest()).substring(8, 24);
			// 下边是替换移位处理
//			if(s!=null){
//				char[] a=s.toCharArray();
//				char[] b=new char[a.length];
//				a[4]='s';
//				a[9]='t';
//				a[24]='y';
//				for(int i=0;i<a.length;i++)
//				{
//					b[i]=a[(i+8)%s.length()];
//				}
//				return new String (b);
//			}else{
//				return null;
//			}
		} catch (Exception e) {
		}

		return "";
	}
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}
		return sb.toString();
	}
	/**
	 * web连接正则
	 * 
	 * @return
	 */
	public static Pattern getWebPattern() {
		Pattern webpattern = Pattern
					.compile("http[s]*://[[[^/:]&&[a-zA-Z_0-9]]\\.]+(:\\d+)?(/[a-zA-Z_0-9]+)*(/[a-zA-Z_0-9]*([a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+)*)?(\\?(&?[a-zA-Z_0-9]+=[%[a-zA-Z_0-9]-]*)*)*(#[[a-zA-Z_0-9]|-]+)?");
		return webpattern;
	}
	public static String getPatternStr(String ori,String pat,int a,int b){
		if(StringUtil.isBlank(ori))return null;
		
		Pattern atPeoplePtn = Pattern.compile(pat,Pattern.DOTALL);
		Matcher matcher = atPeoplePtn.matcher(ori);
		while (matcher.find()) {
			int i = matcher.start();
			int j = matcher.end();
			String id = ori.substring(i + a, j - b);
			return id;
			
		}
		return null;
	}
	/** 
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！) 
     * 
     * @param res            原字符串 
     * @param filePath 文件路径 
     * @return 成功标记 
     */ 
    public static boolean string2File(String res, String filePath) { 
            boolean flag = true; 
            BufferedReader bufferedReader = null; 
            BufferedWriter bufferedWriter = null; 
            try { 
                    File distFile = new File(filePath); 
                    if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs(); 
                    bufferedReader = new BufferedReader(new StringReader(res)); 
//                    bufferedWriter = new BufferedWriter(new FileWriter(distFile)); 
                    java.io.FileOutputStream writerStream = new java.io.FileOutputStream(filePath);    
                    bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(writerStream, "UTF-8")); 
                    char buf[] = new char[1024];         //字符缓冲区 
                    int len; 
                    while ((len = bufferedReader.read(buf)) != -1) { 
                            bufferedWriter.write(buf, 0, len); 
                    } 
                    bufferedWriter.flush(); 
                    bufferedReader.close(); 
                    bufferedWriter.close(); 
            } catch (IOException e) { 
                    e.printStackTrace(); 
                    flag = false; 
                    return flag; 
            } finally { 
                    if (bufferedReader != null) { 
                            try { 
                                    bufferedReader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
                    } 
            } 
            return flag; 
    }
    /** 
     * 文本文件转换为指定编码的字符串 
     * 
     * @param file         文本文件 
     * @param encoding 编码类型 
     * @return 转换后的字符串 
     * @throws IOException 
     */ 
    public static String file2String(File file, String encoding) { 
            InputStreamReader reader = null; 
            StringWriter writer = new StringWriter(); 
            try { 
                    if (encoding == null || "".equals(encoding.trim())) { 
                            reader = new InputStreamReader(new FileInputStream(file)); 
                    } else { 
                            reader = new InputStreamReader(new FileInputStream(file), encoding); 
                    } 
                    //将输入流写入输出流 
                    char[] buffer = new char[2048]; 
                    int n = 0; 
                    while (-1 != (n = reader.read(buffer))) { 
                            writer.write(buffer, 0, n); 
                    } 
            } catch (Exception e) { 
                    e.printStackTrace(); 
                    return null; 
            } finally { 
                    if (reader != null) 
                            try { 
                                    reader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
            } 
            //返回转换结果 
            if (writer != null) 
                    return writer.toString(); 
            else return null; 
    }
}
