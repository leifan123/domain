package test11;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

//import com.github.qcloudsms.SmsSingleSender;
//import com.github.qcloudsms.SmsSingleSenderResult;
//import com.github.qcloudsms.httpclient.HTTPException;
import com.sun.org.apache.bcel.internal.generic.AALOAD;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Test2 {
	
//	@Test
//	public void test1(){
//		 int appid = 1400137623;
//		    
//		    String appkey = "caa23b58361f5b63ae40030f21982a37";
//		    
//		    String[] phoneNumbers = { "18203030990" };
//		    
//		    int templateId = 187841;
//	    	String smsSign = "这是开头";
//		    String str =  "这是内容";
//		    try
//		    {
//		      String[] params = { str };
//		      SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//		      SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0], 
//		        templateId, params, smsSign, "", "");
//		      System.out.println(result);
//		      JSONObject object = JSONObject.fromObject(result.toString());
//		      String msg = object.getString("errmsg");
//		      System.out.println(msg);
//		    }
//		    catch (HTTPException e)
//		    {
//		      e.printStackTrace();
//		    }
//		    catch (JSONException e)
//		    {
//		      e.printStackTrace();
//		    }
//		    catch (IOException e)
//		    {
//		      e.printStackTrace();
//	    }
//	}
	 public static void main(String[] args) throws Exception {
		 List<String> list = new ArrayList<String>();
		 list.add("123.23");
		 System.out.println(Double.parseDouble(list.get(0) )+ (double)32.32);
	    }
	 
	    //链接url下载图片
	    private static void downloadPicture(String urlList,String path) {
	        URL url = null;
	        try {
	            url = new URL(urlList);
	            DataInputStream dataInputStream = new DataInputStream(url.openStream());
	 
	            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	 
	            byte[] buffer = new byte[1024];
	            int length;
	 
	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
