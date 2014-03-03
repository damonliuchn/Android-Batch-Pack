
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * 
 * @author liumeng
 * 新建文件、复制文件、文本文件字符集转换、查找指定字符串
 * 
 */
public class FileUtil {
	public static void createNewFile(File f){
		if(!f.exists()){
			try{
				f.createNewFile();
			}catch(Exception e){
				String a=f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("\\"));
				File temp=new File(a);
				temp.mkdirs();
				try {
					f.createNewFile();
				} catch (Exception e1) {
					System.out.println("FileUtil.createNewFile("+f.getAbsolutePath()+")出错");
				}
			}
		}
	}
	//改名
	public static File rename(File sourceFile,String name)  throws IOException{ 
        File tar=new File(sourceFile.getParentFile().getAbsolutePath()+"/"+name);
        moveFile(sourceFile,tar);
        return tar;
    } 
	// 复制文件 
    public static void copyFile(File sourceFile,File targetFile)  throws IOException{ 
        if(!sourceFile.exists()){
        	return;
        }
        if(!targetFile.exists()){
        	FileUtil.createNewFile(targetFile);
        }else{
        	targetFile.delete();
        	FileUtil.createNewFile(targetFile);
        }
		// 新建文件输入流并对它进行缓冲 
        FileInputStream input = new FileInputStream(sourceFile); 
        BufferedInputStream inBuff=new BufferedInputStream(input); 

        // 新建文件输出流并对它进行缓冲 
        FileOutputStream output = new FileOutputStream(targetFile); 
        BufferedOutputStream outBuff=new BufferedOutputStream(output); 
         
        // 缓冲数组 
        byte[] b = new byte[1024 * 5]; 
        int len; 
        while ((len =inBuff.read(b)) != -1) { 
            outBuff.write(b, 0, len); 
        } 
        // 刷新此缓冲的输出流 
        outBuff.flush(); 
         
        //关闭流 
        inBuff.close(); 
        outBuff.close(); 
        output.close(); 
        input.close(); 
    } 
    // 复制文件夹 
    public static void copyDirectiory(String sourceDir, String targetDir) 
            throws IOException { 
    	if(!(new File(sourceDir).exists()))
    		return; 
        // 新建目标目录 
    	if(!(new File(targetDir).exists()))
    		(new File(targetDir)).mkdirs(); 
        // 获取源文件夹当前下的文件或目录 
        File[] file = (new File(sourceDir)).listFiles(); 
        for (int i = 0; i < file.length; i++) { 
            if (file[i].isFile()) { 
                // 源文件 
                File sourceFile=file[i]; 
                // 目标文件 
               File targetFile=new  File(new File(targetDir).getAbsolutePath() +File.separator+file[i].getName()); 
                copyFile(sourceFile,targetFile); 
            } 
            if (file[i].isDirectory()) { 
                // 准备复制的源文件夹 
                String dir1=sourceDir + "/" + file[i].getName(); 
                // 准备复制的目标文件夹 
                String dir2=targetDir + "/"+ file[i].getName(); 
                copyDirectiory(dir1, dir2); 
            } 
        } 
    } 
    //删除文件或文件夹
    public static void delete(File temp){
		if(temp.isDirectory()){
			for(File f:temp.listFiles()){
				delete(f);
			}
			temp.delete();
		 }else{
			temp.delete();
		}
	}
    //移动文件 
    public static void moveFile(File sourceFile,File targetFile)  throws IOException{ 
        if(!sourceFile.exists()){
        	return;
        }
        if(!targetFile.exists()){
        	FileUtil.createNewFile(targetFile);
        }else{
        	targetFile.delete();
        	FileUtil.createNewFile(targetFile);
        }
		// 新建文件输入流并对它进行缓冲 
        FileInputStream input = new FileInputStream(sourceFile); 
        BufferedInputStream inBuff=new BufferedInputStream(input); 

        // 新建文件输出流并对它进行缓冲 
        FileOutputStream output = new FileOutputStream(targetFile); 
        BufferedOutputStream outBuff=new BufferedOutputStream(output); 
         
        // 缓冲数组 
        byte[] b = new byte[1024 * 5]; 
        int len; 
        while ((len =inBuff.read(b)) != -1) { 
            outBuff.write(b, 0, len); 
        } 
        // 刷新此缓冲的输出流 
        outBuff.flush(); 
         
        //关闭流 
        inBuff.close(); 
        outBuff.close(); 
        output.close(); 
        input.close(); 
        FileUtil.delete(sourceFile);
    }
    //字符集转换问题
    public static void convert(File file, final String posfix,String oldEncoding, String newEncoding){
		File[] fileName = file.listFiles(new FileFilter(){
											public boolean accept(File pathname) {
												if(pathname.isDirectory()){
													return true;
												}
												if(pathname.getName().endsWith(posfix)){
													return true;
												}
												return false;
											}
										});
		
		for(File ff : fileName){
			if(ff.isDirectory()){
				convert(ff, posfix, oldEncoding, newEncoding);
			}else{
				//File source = new File(ff.getParentFile().getAbsoluteFile() + "1.txt");
				File temp = new File(ff.getAbsolutePath() + ".temp");
				createNewFile(temp);
				fileIO(ff, getCharset(ff), temp, newEncoding);
				ff.delete();
				fileIO(temp, newEncoding, ff, newEncoding);
				temp.delete();
				
			}
		}
	}
	public static void fileIO(File ff, String oldEncoding, File temp, String newEncoding){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			fis = new FileInputStream(ff);
			isr = new InputStreamReader(fis,oldEncoding);
			br = new BufferedReader(isr);
			
			fos = new FileOutputStream(temp);
			osw = new OutputStreamWriter(fos, newEncoding);
			PrintWriter pw = new PrintWriter(osw);
			
			String str = null;
			while((str = br.readLine()) != null){
				
//				String s=null;
//				if (str != null) {
//					   //用默认字符编码解码字符串。
//					   byte[] bs = str.getBytes(oldEncoding);
//					   //用新的字符编码生成字符串
//					   s= new String(bs, newEncoding);
//				}
				
				pw.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null)try{br.close();}catch(IOException e){}
			if(bw != null)try{bw.close();}catch(IOException e){}
			if(osw != null)try{osw.close();}catch(IOException e){}
			if(isr != null)try{isr.close();}catch(IOException e){}
			if(fos != null)try{fos.close();}catch(IOException e){}
			if(fis != null)try{fis.close();}catch(IOException e){}
		}
	}
	
	public static String getCharset(File file) {
	        String charset = "GBK";
	        byte[] first3Bytes = new byte[3];
	        try {
	            boolean checked = false;
	            BufferedInputStream bis = new BufferedInputStream(
	                  new FileInputStream(file));
	            bis.mark(0);
	            int read = bis.read(first3Bytes, 0, 3);
	            if (read == -1)
	                return charset;
	            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
	                charset = "UTF-16LE";
	                checked = true;
	            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
	                == (byte) 0xFF) {
	                charset = "UTF-16BE";
	                checked = true;
	            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
	                    == (byte) 0xBB
	                    && first3Bytes[2] == (byte) 0xBF) {
	                charset = "UTF-8";
	                checked = true;
	            }
	            bis.reset();
	            if (!checked) {
	                int loc = 0;
	                while ((read = bis.read()) != -1) {
	                    loc++;
	                    if (read >= 0xF0)
	                        break;
	                    //单独出现BF以下的，也算是GBK
	                    if (0x80 <= read && read <= 0xBF)
	                        break;
	                    if (0xC0 <= read && read <= 0xDF) {
	                        read = bis.read();
	                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
	                            // (0x80 -
	                            // 0xBF),也可能在GB编码内
	                            continue;
	                        else
	                            break;
	                     // 也有可能出错，但是几率较小
	                    } else if (0xE0 <= read && read <= 0xEF) {
	                        read = bis.read();
	                        if (0x80 <= read && read <= 0xBF) {
	                            read = bis.read();
	                            if (0x80 <= read && read <= 0xBF) {
	                                charset = "UTF-8";
	                                break;
	                            } else
	                                break;
	                        } else
	                            break;
	                    }
	                }
	            }
	            bis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return charset;
	    }
}
