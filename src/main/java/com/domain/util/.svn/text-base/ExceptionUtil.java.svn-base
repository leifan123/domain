/**   
 * <p>Title: ExceptionUtil.java  </p>
 * <p>Description: TODO </p>
 * <p>Company: </p>
 * @author yr004   
 * @date 2018年9月20日 下午12:00:16    
 */
package com.domain.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionUtil
 * @Company: YUNRUI
 * @Description:
 * @author YaNan.Guan
 * @date 2018年9月20日 下午12:00:16
 */
public class ExceptionUtil {

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	 public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
}
