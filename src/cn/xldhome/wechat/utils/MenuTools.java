package cn.xldhome.wechat.utils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
public class MenuTools {
	private Logger log = Logger.getLogger(super.getClass());
	/**
	 * 菜单
	 * 
	 * @return
	 */
	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，我是栋栋，请回复数字选择服务：").append("\n\n");
		buffer.append("1  天气预报").append("\n");
		buffer.append("2  公交查询").append("\n");
		buffer.append("3  周边搜索").append("\n");
		buffer.append("4  歌曲点播").append("\n");
		buffer.append("5  经典游戏").append("\n");
		buffer.append("6  帅哥电台").append("\n");
		buffer.append("7  人脸识别").append("\n");
		buffer.append("8  聊天唠嗑").append("\n\n");
		buffer.append("回复“?”显示此帮助菜单");
		return buffer.toString();
	}
	/**
	 * 判断是否是QQ表情
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;
	 
		// 判断QQ表情的正则表达式
		String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}
	private String getAccessToken(String paramString1, String paramString2)
	  {
	    String str1 = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", new Object[] { paramString1, paramString2 });
	    String str2 = "";
	    if ("".equals(str2))
	      try
	      {
	        str2 = sendRequest(str1);
	      }
	      catch (Exception localException)
	      {
	        this.log.error("获取access_token失败", localException);
	        return null;
	      }
	    if ((str2 != null) && (!(str2.equals(""))))
	    {
	      this.log.debug("请求access_token返回结果:" + str2);
	      JSONObject localJSONObject = JSONObject.fromObject(str2);
	      if (localJSONObject.containsKey("errcode"))
	      {
	        this.log.error("请求access_token失败，失败原因：" + localJSONObject.getString("errmsg"));
	        return null;
	      }
	      return localJSONObject.getString("access_token");
	    }
	    this.log.error("获取access_token失败");
	    return null;
	  }
	private String sendRequest(String paramString)
		    throws Exception
		  {
		    CloseableHttpClient localCloseableHttpClient = createSSLClientDefault();
		    HttpGet localHttpGet = new HttpGet(paramString);
		    try
		    {
		      CloseableHttpResponse localCloseableHttpResponse = null;
		      localCloseableHttpResponse = localCloseableHttpClient.execute(localHttpGet);
		      InputStream localInputStream = localCloseableHttpResponse.getEntity().getContent();
		      byte[] arrayOfByte = new byte[1024];
		      int i = 0;
		      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		      while ((i = localInputStream.read(arrayOfByte)) > -1)
		        localByteArrayOutputStream.write(arrayOfByte, 0, i);
		      localInputStream.close();
		      String str1 = new String(localByteArrayOutputStream.toByteArray(), "utf-8");
		      String str2 = str1;
		      return str2;
		    }
		    finally
		    {
		      localHttpGet.releaseConnection();
		      localCloseableHttpClient.close();
		    }
		  }
	private CloseableHttpClient createSSLClientDefault()
		    throws Exception
		  {
		    SSLContext localSSLContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
		    {
		      public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
		        throws CertificateException
		      {
		        return true;
		      }
		    }).build();
		    SSLConnectionSocketFactory localSSLConnectionSocketFactory = new SSLConnectionSocketFactory(localSSLContext);
		    return HttpClients.custom().setSSLSocketFactory(localSSLConnectionSocketFactory).build();
		  }
}
