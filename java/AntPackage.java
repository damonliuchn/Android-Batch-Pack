import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AntPackage {

	/**
	 * @param args
	 */
	public static  String channels[];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String projectPath="C:\\mentor_adbtool\\ant-package\\project\\MDApp_Android";

		channels=args[1].split("_");

		String projectPath=args[0];
		for (int i = 0; i < channels.length; i++) {
			modifyChannel(projectPath,channels[i]);//修改渠道名
			//打包
			packageing(projectPath);
			//把apk复制出来
			copyApk(projectPath,channels[i]);
		}
		
	}
	public static void modifyChannel(String projectPath,String channel) {
		// TODO Auto-generated method stub
		String manifestPath=projectPath+"\\AndroidManifest.xml";
		File manifes = new File(manifestPath);
		String a=StringUtil.file2String(manifes, "UTF-8");
		String b=StringUtil.getPatternStr(a, "ant-package-tag-start-umeng.*?ant-package-tag-end-umeng", 0, 0);
		String c="ant-package-tag-start-umeng--><meta-data android:value=\""+channel+"\" android:name=\"UMENG_CHANNEL\"/><!--ant-package-tag-end-umeng";
		a=a.replace(b, c);
		manifes.delete();
		System.out.println(a) ;
		StringUtil.string2File(a, manifes.getAbsolutePath());
	}
	public static void copyApk(String projectPath,String channel) {
		// TODO Auto-generated method stub
		String projectNames[]=projectPath.split("\\\\");
		String apkPath=projectPath+"\\bin\\"+projectNames[projectNames.length-1]+"-release.apk";
		
		String outputPath=projectPath.replace("\\"+projectNames[projectNames.length-2]+"\\"+projectNames[projectNames.length-1], "\\output");
		String outputApk=outputPath+"\\"+channel+"-"+projectNames[projectNames.length-1]+"-release.apk";
		
		if(new File(outputApk).exists()){
			new File(outputApk).delete();
		}
		try {
			FileUtil.copyFile(new File(apkPath), new File(outputApk));
		} catch (IOException e) {e.printStackTrace();}
	}
	public static void packageing(String projectPath){
		String projectNames[]=projectPath.split("\\\\");
		String batPath=projectPath.replace("\\"+projectNames[projectNames.length-2]+"\\"+projectNames[projectNames.length-1], "\\package.bat");
		System.out.println(batPath);  
		String command = batPath;  
        try {  
            Process process = Runtime.getRuntime().exec(command+" "+projectNames[projectNames.length-1]);  
            CmdProcess cmdProcess = new AntPackage().new CmdProcess(process.getInputStream());  
            cmdProcess.start();  
            process.waitFor();  
        }catch(Exception e){  
            e.printStackTrace();  
        }   
	}
	/** 
	 * 该类启动doc界面 
	 * @author Administrator 
	 * 
	 */  
	public class CmdProcess extends Thread{  
	      
	    InputStream inputStream;  
	  
	    public InputStream getInputStream() {  
	        return inputStream;  
	    }  
	  
	    public void setInputStream(InputStream inputStream) {  
	        this.inputStream = inputStream;  
	    }  
	  
	    public CmdProcess() {  
	        super();  
	        // TODO Auto-generated constructor stub  
	    }  
	      
	    public CmdProcess(InputStream inputStream) {  
	        super();  
	        this.inputStream = inputStream;  
	    }  
	  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        try {  
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	            String line = null;  
	            while ((line = bufferedReader.readLine()) != null){  
	            //while ((bufferedReader.readLine()) != null) {//此处是关键,原因我也不知道，望牛人解答  
	                System.out.println(line);  
	            }  
	            try{                  
	            }finally{  
	                if(bufferedReader!=null) bufferedReader.close();  
	                if(inputStreamReader!=null) inputStreamReader.close();  
	                if(inputStream!=null) inputStream.close();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }}
}
