package test11;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.domain.pojo.DomainPrice;
import com.domain.service.DomainPriceService;
import com.domain.util.DomainUtil;
import com.domain.util.HttpReq;
import com.domain.util.TimeUtil;

import net.sf.json.JSONObject;

public class Test1 {
	
	protected static ApplicationContext ctx;

	@BeforeClass
	public static void setup() {
		String[] resources = { "classpath*:applicationContext.xml" };
		ctx = new ClassPathXmlApplicationContext(resources);
	}

	@Test
	public void test() throws Exception{
		
		System.out.println(ctx);
		
		DomainPriceService domainPriceService = ctx.getBean("DomainPriceService", DomainPriceService.class);
		
		System.out.println(1);
		List<String> list = new ArrayList<String>(200);
		list.add(".com".replaceAll(" ", ""));
		list.add(".cn".replaceAll(" ", ""));
		list.add(".top".replaceAll(" ", ""));
		list.add(".net".replaceAll(" ", ""));
		list.add(".com.cn".replaceAll(" ", ""));
		list.add(".wang".replaceAll(" ", ""));
		list.add(".xyz".replaceAll(" ", ""));
		list.add(".online".replaceAll(" ", ""));
		list.add(".shop".replaceAll(" ", ""));
		list.add(".club".replaceAll(" ", ""));
		list.add(".ink".replaceAll(" ", ""));
		list.add(".vip".replaceAll(" ", ""));
		list.add(".info".replaceAll(" ", ""));
		list.add(".org".replaceAll(" ", ""));

		list.add(".pro".replaceAll(" ", ""));
		list.add(".art".replaceAll(" ", ""));
		list.add(".link".replaceAll(" ", ""));
		list.add(".store".replaceAll(" ", ""));
		list.add(".site".replaceAll(" ", ""));
		list.add(".tech".replaceAll(" ", ""));
		list.add(".red".replaceAll(" ", ""));
		list.add(".group".replaceAll(" ", ""));
		list.add(".co".replaceAll(" ", ""));
		list.add(".ltd".replaceAll(" ", ""));
		list.add(".beer".replaceAll(" ", ""));
		list.add(".mobi".replaceAll(" ", ""));
		list.add(".fun".replaceAll(" ", ""));
		list.add(".cc".replaceAll(" ", ""));
		
		list.add(".net.cn".replaceAll(" ", ""));
		list.add(".org.cn".replaceAll(" ", ""));
		list.add(".work".replaceAll(" ", ""));
		list.add(".tv".replaceAll(" ", ""));
		list.add(".ren".replaceAll(" ", ""));
		list.add(".tech".replaceAll(" ", ""));
		list.add(".biz".replaceAll(" ", ""));
		list.add(".wiki".replaceAll(" ", ""));
		list.add(".design".replaceAll(" ", ""));
		list.add(".gov.cn".replaceAll(" ", ""));
		list.add(".ac.cn".replaceAll(" ", ""));
		list.add(".com".replaceAll(" ", ""));
		list.add(".cn".replaceAll(" ", ""));
		list.add(".top".replaceAll(" ", ""));
		
		list.add(".net".replaceAll(" ", ""));
		list.add(".中国".replaceAll(" ", ""));
		list.add(".公司".replaceAll(" ", ""));
		list.add(".网络".replaceAll(" ", ""));
		list.add(".在线".replaceAll(" ", ""));
		list.add(".集团".replaceAll(" ", ""));
		list.add(".网址".replaceAll(" ", ""));
		list.add(".手机".replaceAll(" ", ""));
		list.add(".wang".replaceAll(" ", ""));
		list.add(".online".replaceAll(" ", ""));
		list.add(".shop".replaceAll(" ", ""));
		list.add(".club".replaceAll(" ", ""));
		list.add(".vip".replaceAll(" ", ""));
		list.add(".link".replaceAll(" ", ""));
		
		list.add(".store".replaceAll(" ", ""));
		list.add(".site".replaceAll(" ", ""));
		list.add(".tech".replaceAll(" ", ""));
		list.add(".企业".replaceAll(" ", ""));
		list.add(".商店".replaceAll(" ", ""));
		list.add(".beer".replaceAll(" ", ""));
		list.add(".游戏".replaceAll(" ", ""));
		list.add(".娱乐".replaceAll(" ", ""));
		list.add(".fun".replaceAll(" ", ""));
		list.add(".cc".replaceAll(" ", ""));
		list.add(".work".replaceAll(" ", ""));
		list.add(".biz".replaceAll(" ", ""));
		list.add(".我爱你".replaceAll(" ", ""));
		list.add(".信息".replaceAll(" ", ""));
		
		list.add(".广东".replaceAll(" ", ""));
		list.add(".佛山".replaceAll(" ", ""));
		list.add(".中文网".replaceAll(" ", ""));
		list.add(".bj.cn".replaceAll(" ", ""));
		list.add(".sh.cn".replaceAll(" ", ""));
		list.add(".hk.cn".replaceAll(" ", ""));
		list.add(".tj.cn".replaceAll(" ", ""));
		list.add(".cq.cn".replaceAll(" ", ""));
		list.add(".he.cn".replaceAll(" ", ""));
		list.add(".sx.cn".replaceAll(" ", ""));
		list.add(".work".replaceAll(" ", ""));
		list.add(".nm.cn".replaceAll(" ", ""));
		list.add(".ln.cn".replaceAll(" ", ""));
		list.add(".jl.cn".replaceAll(" ", ""));
		
		list.add(".hl.cn".replaceAll(" ", ""));
		list.add(".js.cn".replaceAll(" ", ""));
		list.add(".zj.cn".replaceAll(" ", ""));
		list.add(".ah.cn".replaceAll(" ", ""));
		list.add(".fj.cn".replaceAll(" ", ""));
		list.add(".jx.cn".replaceAll(" ", ""));
		list.add(".sd.cn".replaceAll(" ", ""));
		list.add(".ha.cn".replaceAll(" ", ""));
		list.add(".he.cn".replaceAll(" ", ""));
		list.add(".hb.cn".replaceAll(" ", ""));
		list.add(".hn.cn".replaceAll(" ", ""));
		list.add(".gd.cn".replaceAll(" ", ""));
		list.add(".gx.cn".replaceAll(" ", ""));
		list.add(".hi.cn".replaceAll(" ", ""));
		
		list.add(".sc.cn".replaceAll(" ", ""));
		list.add(".gz.cn".replaceAll(" ", ""));
		list.add(".yn.cn".replaceAll(" ", ""));
		list.add(".xz.cn".replaceAll(" ", ""));
		list.add(".sn.cn".replaceAll(" ", ""));
		list.add(".gs.cn".replaceAll(" ", ""));
		list.add(".qh.cn".replaceAll(" ", ""));
		list.add(".nx.cn".replaceAll(" ", ""));
		list.add(".xj.cn".replaceAll(" ", ""));
		list.add(".tw.cn".replaceAll(" ", ""));
		list.add(".mo.cn".replaceAll(" ", ""));
		
		System.out.println(list);

		domainPriceService.insertDomain(list);
		
	}
	
	
	@Test
	public void test2(){
		
		DomainPriceService domainPriceService = ctx.getBean("DomainPriceService", DomainPriceService.class);
		
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		List<DomainPrice> select = domainPriceService.select(null);
		
		String sendGet = null;
		
		for(DomainPrice domainPrice : select){
			if(domainPrice.getId() > 75){
				if(domainPrice.getType() == 2){
					sendGet = HttpReq.sendGet("http://api.cndns.com/product/getProductPrice.aspx", 
							authStr + "&type=1&years=1&tag=dmc&suffix=" + domainPrice.getDomainType());
				}else{
					sendGet = HttpReq.sendGet("http://api.cndns.com/product/getProductPrice.aspx", 
							authStr + "&type=1&years=1&tag=dme&suffix=" + domainPrice.getDomainType());
				}
				System.out.println(sendGet);
				JSONObject jsonObject = JSONObject.fromObject(sendGet);
				
				String userId = (String) jsonObject.get("message");
				System.out.println(userId);
				domainPriceService.updateByParam("mcpurchase=" + userId +" where id=" + domainPrice.getId());
			}
		}
		
		
	}
}
