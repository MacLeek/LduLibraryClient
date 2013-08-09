package cn.ldu.edu.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.ldu.edu.data.BookInfo;
import cn.ldu.edu.data.Cookie;
import cn.ldu.edu.data.NETADDRESS;
import cn.ldu.edu.data.SearchBook;
import cn.ldu.edu.data.SearchBookAddress;
import cn.ldu.edu.data.StudentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LibAPI {

	private String username; // 用户名
	private String password; // 密码
	private int state;// 状态
	private String result;//
	private final static String HOST = "202.194.50.132";
	private final static String LOGIN_URL = "http://202.194.50.132/gdlisnet/ReaderLogin.aspx?";
	private final static String LOGIN_SU = "http://210.38.138.1:81/user/userinfo.aspx";
	private String cookie_id;
	private String cookie_id_end;
	private int n = 1;//
	private int max = 1;//
	private String method;
	private String word;

	public LibAPI(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public LibAPI() {

	}

	public String getCookie() {
		return cookie_id + ";" + cookie_id_end;
	}

	public void setCookie(String cookie){
		String[] strs=cookie.split(";");
		cookie_id=strs[0];
		cookie_id_end=strs[1];
	}
	public int getState() {
		return this.state;
	}

    public void sendTreeHole(String msg)
    {
        String LOGIN_URL = "http://liyang.sinaapp.com/upload_status.php";
        DefaultHttpClient client = new DefaultHttpClient();
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("status", msg));
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost post = new HttpPost(LOGIN_URL);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity entity2 = response.getEntity();
            System.out.println(EntityUtils.toString(entity2));
        } catch (Exception e) {
// TODO: handle exception
        }

    }

	public void loginURL() {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("TextBox1", username)); //将用户名密码加入post的参数中，金盘的系统需要在此额外增加
		formparams.add(new BasicNameValuePair("TextBox2", password));
        formparams.add(new BasicNameValuePair("ImageButton1.x", "0"));
        formparams.add(new BasicNameValuePair("ImageButton1.y", "0"));
        formparams.add(new BasicNameValuePair("DropDownList1","读者条码"));
        formparams.add(new BasicNameValuePair("__VIEWSTATE",
                "/wEPDwUJNzQ4NzgzNjUwD2QWAgIDD2QWAgIFD2QWAmYPZBYGAgEPZBYCAgEPDxYCHgRUZXh0BesGPHRkI"+
                        "HN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J2RlZmF1bHQuYXNweCc+PHNwYW4+6aaW6aG1PC9zcGF"+
                        "uPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdkZWZhdWx0LmFzcHgnPjxzcGFu"+
                        "PuS5puebruafpeivojwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nTWFn"+
                        "YXppbmVDYW50b1NjYXJjaC5hc3B4Jz48c3Bhbj7mnJ/liIrnr4flkI08L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPS"+
                 "JoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1Jlc2VydmVkTGlzdC5hc3B4Jz48c3Bhbj7pooTnuqbliLDppoY8"+
                        "L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J0V4cGlyZWRMaXN0LmFzcHg"+
                        "nPjxzcGFuPui2heacn+WFrOWRijwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJ"+
                    "lZj0nTmV3Qm9vS1NjYXJjaC5hc3B4Jz48c3Bhbj7mlrDkuabpgJrmiqU8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eW"+
                        "xlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J0FkdmljZXNTY2FyY2guYXNweCc+PHNwYW4+5oOF5oql5qOA57SiPC9z"+
                        "cGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdDb21tZW5kTmV3Qm9va1NjYXJjaC5hc"+
                        "3B4Jz48c3Bhbj7mlrDkuablvoHorqI8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J"+
                    "1JlYWRlckxvZ2luLmFzcHgnPjxzcGFuPuivu+iAheeZu+W9lTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+"+
                        "PEEgaHJlZj0nT25saW5lU3R1ZHkuYXNweCc+PHNwYW4+5Zyo57q/5ZKo6K+iL+WfueiurTwvc3Bhbj48L0E+PC90ZD5kZAID"+
                     "D2QWBAICDw8WAh8ABTI8c3Bhbj7mrKLov47mgqg6R3Vlc3Qg6K+36YCJ5oup5L2g55qE5pON5L2cPC9zcGFuPmRkA"+
                        "gMPDxYCHgdWaXNpYmxlaGRkAgUPZBYCAgEPZBYCAgMPZBYCZg8QZBAVAwzor7vogIXmnaHnoIEM5YCf5Lmm6K+B5Y+3BuW"+
                        "nk+WQjRUDDOivu+iAheadoeeggQzlgJ/kuabor4Hlj7cG5aeT5ZCNFCsDA2dnZ2RkGAEFHl9fQ29udHJvbHNSZXF1aX"+
                        "JlUG9zdEJhY2tLZXlfXxYBBQxJbWFnZUJ1dHRvbjE5DFoEmomZ8hEP5NYzsmBsDxdCfw=="));
        formparams.add(new BasicNameValuePair("__EVENTVALIDATION",
                "/wEWCAKvrdaRCwLgnZ70BALntNySDgLrr+CHBALwuLirBQLs0bLrBgLs0fbZDALSwpnTCPjT+fZOv0Nvay0umrL1/i9C95b0"));
        try {
			UrlEncodedFormEntity entity;
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			HttpPost post = new HttpPost(LOGIN_URL);
			post.addHeader("Referer",
					"http://202.194.50.132/gdlisnet/ReaderLogin.aspx");    //这里可以不用设吧，不过还是按照抓的包填上吧
			post.addHeader("Accept",
					"text/javascript, text/html, application/xml, text/xml");
			post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Cache-Control", "no-cache");
			post.addHeader("Cookie", cookie_id);
			post.addHeader("Host", "202.194.50.132");
			post.setEntity(entity);

			response = client.execute(post);    //开始post表项
			HttpEntity entity2 = response.getEntity();
			// System.out.println(EntityUtils.toString(entity2));
			result = EntityUtils.toString(entity2);
			state = response.getStatusLine().getStatusCode();
			if (result.length() < 2) {
				System.out.println("成功");
				Header h = response.getFirstHeader("Set-Cookie");
			    System.out.println(h);
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator("Set-Cookie"));
				while (it.hasNext()) {
					HeaderElement elem = it.nextElement();
					cookie_id_end = elem.getName() + "=" + elem.getValue();
					// System.out.println("----------");
					// System.out.println(cookie_id_end);
				}
                System.out.println("未知");

			} else {
				System.out.println(result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void Get() {
		HttpClient c = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://202.194.50.132/gdlisnet/ReaderLogin.aspx");
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("Host", "202.194.50.132");
		get.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		get.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");


		try {
            System.out.println("get前");

            HttpResponse re = c.execute(get);
            System.out.println("get后");

			/*
			 * System.out.println(get.getAllHeaders().length);
			 * 
			 * Header[] hs=re.getAllHeaders(); for(Header h:hs){
			 * System.out.println(h); }
			 */
			// System.out.println(re.getFirstHeader("Set-Cookie"));

			HeaderElementIterator it = new BasicHeaderElementIterator(
					re.headerIterator("Set-Cookie"));
			Cookie cookie = new Cookie();
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
                System.out.println("获得cookie");

                System.out.println(he.getName()+"="+he.getValue());
				cookie_id = he.getName() + "=" + he.getValue();
				Map<String, String> tmp = new HashMap<String, String>();
				tmp.put(he.getName(), he.getValue());
				cookie.setName(tmp);
				NameValuePair[] params = he.getParameters();
				for (int i = 0; i < params.length; i++) {
					 System.out.println(" " + params[i]);
				}
			}
			// System.out.println(cookie.getName().toString());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StudentInfo Get_User() {
        System.out.println("getuser前");

        HttpClient c = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://202.194.50.132/gdlisnet/ReaderTable.aspx");
		addHttpGetHeader(get);
		StudentInfo s = new StudentInfo();
		try {
			HttpResponse response = c.execute(get);
			HttpEntity entity = response.getEntity();
			System.out.println("----------html-------------");
		   // System.out.println(EntityUtils.toString(entity));
			s = JsoupUtils.getUserInfo(EntityUtils.toString(entity));
            System.out.println("NUmber");
            System.out.println(s.getNumber());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(s);
		return s;
	}

	// 修改密码
	public int changPass(String oldpass,String newpass,String renewpass) {
		HttpClient c = new DefaultHttpClient();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("ScriptManager1","UpdatePanel1|BtnPass"));
		formparams.add(new BasicNameValuePair("__EVENTTARGET", ""));
		formparams.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		formparams
				.add(new BasicNameValuePair(
                        "__VIEWSTATE",
                        "/wEPDwUKMjEwMDkyODE5NA9kFgICAw9kFgICAw9kFgJmD2QWNgIBD2QWAgIBDw8WAh4EVGV4dAXABzx0ZCBzdHlsZ" +
                                "T0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdkZWZhdWx0LmFzcHgnPjxzcGFuPummlumhtTwvc3Bhbj48L" +
                                "0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nZGVmYXVsdC5hc3B4Jz48c3Bhbj7" +
                                "kuabnm67mn6Xor6I8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjx" +
                                "BIGhyZWY9J01hZ2F6aW5lQ2FudG9TY2FyY2guYXNweCc+PHNwYW4+5pyf5YiK56+H5ZCNPC9zcGFuPjw" +
                                "vQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdSZXNlcnZlZExpc3QuYXNweCc+PHNwYW4+6aKE57q" +
                                "m5Yiw6aaGPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48Q" +
                                "SBocmVmPSdFeHBpcmVkTGlzdC5hc3B4Jz48c3Bhbj7otoXmnJ/lhazlkYo8L3NwYW4+PC9BPjwvd" +
                                "GQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J05ld0Jvb0tTY2FyY2guYXNweCc+PHNwYW4+5" +
                                "paw5Lmm6YCa5oqlPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSB" +
                                "ocmVmPSdBZHZpY2VzU2NhcmNoLmFzcHgnPjxzcGFuPuaDheaKpeajgOe0ojwvc3Bhbj48L0E+PC90ZD48dG" +
                                "Qgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nQ29tbWVuZE5ld0Jvb2tTY2FyY2guYXNweCc+PHNw" +
                                "YW4+5paw5Lmm5b6B6K6iPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBo" +
                                "cmVmPSdSZWFkZXJMb2dpbi5hc3B4Jz48c3Bhbj7or7vogIXnmbvlvZU8L3NwYW4+PC9BPjwvdGQ+PHRkIH" +
                                "N0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J09ubGluZVN0dWR5LmFzcHgnPjxzcGFuPuWcqOe6v+WSqO" +
                                "ivoi/ln7norq08L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1" +
                                "JlYWRlclRhYmxlLmFzcHgnPjxzcGFuPuivu+iAheeuoeeQhjwvc3BhbjwvYT48L3RkPmRkAgUPZBYCAgIPDx" +
                                "YCHwAFNDxzcGFuPiDmrKLov47mgqg65p2O5rSLIOivt+mAieaLqeS9oOeahOaTjeS9nDwvc3Bhbj5kZ" +
                                "AIJDw8WAh8ABQ0yMDEwMjIxMjIzMSAgZGQCCw8PFgIfAAUNMjAxMDIyMTIyMzEgIGRkAg0PDxYCHwAFCzIwM" +
                                "TAyMjEyMjMxZGQCDw8PFgIeCEltYWdlVXJsBRRJbWFnZXMvcmVhZGVycGhvLmdpZmRkAhEPDxYCHwAFBuadj" +
                                "ua0i2RkAhMPDxYCHwAFA+eUt2RkAhUPDxYCHwAFG+S/oeaBr+enkeWtpuS4juW3peeoi+WtpumZomRkAhcPD" +
                                "xYCHwAFBuacrOenkWRkAhkPDxYCHwAFBiZuYnNwO2RkAhsPDxYCHwAFBiZuYnNwO2RkAh0PDxYCHwAFBiZuY" +
                                "nNwO2RkAh8PDxYCHwBlZGQCIQ8PFgIfAGVkZAIjDw8WAh8AZWRkAiUPDxYCHwBlZGQCJw8PFgIfAGVkZAIpDw" +
                                "8WAh8AZWRkAisPDxYCHwAFBjEuMjAwMGRkAi0PDxYCHwAFBjAuMDAwMGRkAi8PDxYCHwAFATNkZAIzDw9kFgIeB2" +
                                "9uY2xpY2sFK3JldHVybiBjb25maXJtKCfnoa7orqTkvaDnmoTkv67mlLnlkJfvvJ8nKTtkAjkPPCsACwIADxYIHghEY" +
                                "XRhS2V5cxYAHgtfIUl0ZW1Db3VudAIDHglQYWdlQ291bnQCAR4VXyFEYXRhU291cmNlSXRlbUNvdW50AgNkATwrAAcBBDwrAA" +
                                "QBABYCHgdWaXNpYmxlZxYCZg9kFgYCAQ9kFg5mDw8WAh8ABQ4yMDEyMDAwMTY1NTQ2MWRkAgEPDxYCHwAFTUxJTlVY5YaF5" +
                                "qC46K6+6K6h55qE6Im65pyvOuWbvuino0xJTlVY5pON5L2c57O757uf5p625p6E6K6+6K6h5LiO5a6e546w5Y6f55CGZGQCA" +
                                "g8PFgIfAAUKMjAxMy0wNS0xMWRkAgMPDxYCHwAFCjIwMTMtMDYtMTJkZAIED2QWAmYPFQMBMQY0OTMzMzkOMjAxMjAwMDE2NTU0" +
                                "NjFkAgUPDxYCHwAFDVRQMzE2Ljg1LzAyMzNkZAIGDw8WAh8ABRTmgLvlh7rnurPlpIQo5Lmm5bqTKWRkAgIPZBYOZg8PFgIfAAUO" +
                                "MjAwNzAwMDA4NzEwNjZkZAIBDw8WAh8ABRNDKysgUFJJTUVS5Lit5paH54mIZGQCAg8PFgIfAAUKMjAxMy0wNS0xNmRkAg" +
                                "MPDxYCHwAFCjIwMTMtMDYtMTdkZAIED2QWAmYPFQMBMAYyOTYxNjkOMjAwNzAwMDA4NzEwNjZkAgUPDxYCHwAFC1RQMz" +
                                "EyQy83NDkxZGQCBg8PFgIfAAUU5oC75Ye657qz5aSEKOS5puW6kylkZAIDD2QWDmYPDxYCHwAFDjIwMTIwMDAxNjg0NzQ" +
                                "3ZGQCAQ8PFgIfAAUnQysr5Y+N5rGH57yW5LiO6YCG5ZCR5YiG5p6Q5oqA5pyv5o+t56eYZGQCAg8PFgIfAAUKMjAxMy0wNS0y" +
                                "MmRkAgMPDxYCHwAFCjIwMTMtMDYtMjNkZAIED2QWAmYPFQMBMQY1MTE3MDQOMjAxMjAwMDE2ODQ3NDdkAgUPDx" +
                                "YCHwAFC1RQMzEyQy84MzQ0ZGQCBg8PFgIfAAUU5oC75Ye657qz5aSEKOS5puW6kylkZAJBDw8WAh8ABRjmmoLml6Dpo" +
                                "oTnuqblm77kuaYuLi4uLi5kZAJDDzwrAAsAZAJFD2QWAmYPZBYEZg9kFgICAQ9kFgICAQ9kFgJmD2QWAgIFDw9kFgIfAgU0cm" +
                                "V0dXJuIGNvbmZpcm0oJ+ehruiupOimgeS/neWtmOS9oOeahOS/ruaUueWQl++8nycpO2QCAQ9kFgICAQ9kFgICAQ9kFgJmD2QWAg" +
                                "IDDw9kFgIfAgUucmV0dXJuIGNvbmZpcm0oJ+ehruiupOimgeaMguWkseivgeS7tuegge+8nycpO2RkLPjV7m05ftNPM0vd+NY+T/yklms="));
		formparams
				.add(new BasicNameValuePair("__EVENTVALIDATION",
						"/wEWDgL2mo/zBAKrhfywDgL7hryJDwLgnZ70BAKW6avNDQLdmpmqCgKQ9M/rBQLXqL7fDwLwmLemD"+
                                "QLs0fbZDALs0Yq1BQL2/4n8CALs0e58Aovpt48Jba29xNDNAL4jICm6dkobVFulLfs="));
		formparams.add(new BasicNameValuePair("TextBox2", newpass));
		formparams.add(new BasicNameValuePair("TextBox3", renewpass));
        formparams.add(new BasicNameValuePair("TextBox4", ""));
        //formparams.add(new BasicNameValuePair("ctl00$cpRight$txtRepNewPass",renewpass));
		formparams.add(new BasicNameValuePair("BtnPass", "确认修改密码"));

		HttpPost post = new HttpPost(NETADDRESS.CHANGEPASS);
		UrlEncodedFormEntity entity;

		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			// post.addHeader("Referer",
			// "http://210.38.138.1:81/user/changepas.aspx");
			post.addHeader("Accept",
					"text/javascript, text/html, application/xml, text/xml");
			post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Cache-Control", "no-cache");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.addHeader("Cookie", cookie_id + ";" + cookie_id_end);
			post.addHeader("Host", "202.194.50.132");
			post.addHeader("Origin", "http://202.194.50.132");
			post.setEntity(entity);
			HttpResponse response = c.execute(post);
//			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getFirstHeader("Location"));
			return response.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;

	}

	public HttpGet addHttpGetHeader(HttpGet get) {
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("Cache-Control", "max-age=0");
		get.addHeader("Host", "202.194.50.132");
		get.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		get.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.addHeader("Cookie", cookie_id + ";" + cookie_id_end);
		return get;
	}

	// 查看正在借的图书
	public List<BookInfo> getBorrowedContent() {
		HttpClient c = new DefaultHttpClient();
		HttpGet get = new HttpGet(NETADDRESS.BOOKBORROWED);
		addHttpGetHeader(get);
		try {
			HttpResponse response = c.execute(get);
			HttpEntity entity = response.getEntity();
			return JsoupUtils.getBorrowedContent(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 查以前借过的书
	public void borrowedHistory() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(NETADDRESS.BOOKBORROWEDHISTORY + "?PageNo=" + n);
		addHttpGetHeader(get);
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
            System.out.println("---------history");
            System.out.println(EntityUtils.toString(entity));

			JsoupUtils.getBorrowedHistory(EntityUtils.toString(entity));
			max = JsoupUtils.getBorrowedmax();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextBorrowedHistory() {
		if (n < max) {
			n++;
			borrowedHistory();
		}
	}

	// 查书
	/*
	 * anywords:java dt:ALL cl:ALL dp:20 sf:M_PUB_YEAR ob:DESC sm:table dept:ALL
	 */
	public String searchBook( String word) {
        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("name", word));
        String LOGIN_URL = "http://liyang.sinaapp.com/welcome.php";
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(qparams, "UTF-8");
            HttpPost post = new HttpPost(LOGIN_URL);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity entity2 = response.getEntity();
            Document doc = Jsoup.parse(EntityUtils.toString(entity2));
            Elements es = doc.getElementsByTag("h1");
            System.out.println(es.text().toString());

            return es.text().toString();
        } catch (Exception e) {
// TODO: handle exception
        }
        return null;
	}


/*
	public List<SearchBook> searchBook(String method, String word, int page) {
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("ScriptManager1","UpdatePanel1|Button1"));
        formparams.add(new BasicNameValuePair("__EVENTTARGET",""));
        formparams.add(new BasicNameValuePair("__EVENTARGUMENT",""));
        formparams.add(new BasicNameValuePair("__LASTFOCUS",""));
        formparams.add(new BasicNameValuePair("__VIEWSTATE",
           "/wEPDwUKLTk1NjkxNzk2NQ9kFgICAw9kFgICBQ9kFgJmD2QWEAIBD2QWAgIBDw8WAh4EVGV4dAXABzx0ZCBzdH"+
                   "lsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdkZWZhdWx0LmFzcHgnPjxzcGFuPummlumhtTwvc3Bhbj48L0E"+
                   "+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nZGVmYXVsdC5hc3B4Jz48c3Bhbj7kuabnm67mn6Xor"+
    "6I8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J01hZ2F6aW5lQ2FudG9TY2FyY2guYXNweCc+PHN"+
                   "wYW4+5pyf5YiK56+H5ZCNPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdSZXNlcnZlZExpc3Q"+
                   "uYXNweCc+PHNwYW4+6aKE57qm5Yiw6aaGPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdFeHBpcmVkTGlzd"+
                   "C5hc3B4Jz48c3Bhbj7otoXmnJ/lhazlkYo8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J05ld0Jvb0tTY2F"+
                   "yY2guYXNweCc+PHNwYW4+5paw5Lmm6YCa5oqlPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdBZHZpY2V"+
                   "zU2NhcmNoLmFzcHgnPjxzcGFuPuaDheaKpeajgOe0ojwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nQ29tbW"+
                   "VuZE5ld0Jvb2tTY2FyY2guYXNweCc+PHNwYW4+5paw5Lmm5b6B6K6iPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48Q"+
                   "SBocmVmPSdSZWFkZXJMb2dpbi5hc3B4Jz48c3Bhbj7or7vogIXnmbvlvZU8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxB"+
                   "IGhyZWY9J09ubGluZVN0dWR5LmFzcHgnPjxzcGFuPuWcqOe6v+WSqOivoi/ln7norq08L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6ID"+
                   "IxcHgiPjxBIGhyZWY9J1JlYWRlclRhYmxlLmFzcHgnPjxzcGFuPuivu+iAheeuoeeQhjwvc3BhbjwvYT48L3RkPmRkAgUPZBYCAgIPDxYCHwAFNDxzc"+
                   "GFuPiDmrKLov47mgqg65p2O5rSLIOivt+mAieaLqeS9oOeahOaTjeS9nDwvc3Bhbj5kZAIJD2QWAmYPDxYCHwAFrA3ng63pl6jmo4DntKI6PF"+
                   "NQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ"+
                   "7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDpuqbnlLDph4znmoTlrojmnJvogIU8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bG"+
                   "U9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1S"+
                   "SUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDlubPlh6HnmoTkuJbnlYw8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT0"+
                   "06ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxc"+
                   "Hggc29saWQiPiDkuqzljY7ng5/kupE8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNv"+
                   "bGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDkuK3l"+
                   "m73ljobku6PnmoflkI7kvKDlpYc8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyB"+
                   "CQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDlnKPnu488L1NQ"+
                   "QU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY"+
                   "1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiBQSE9UT1NIT1A8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3"+
                   "R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJP"+
                   "UkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDpq5jnrYnmlbDlraY8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RU"+
                   "T006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTF"+
                   "CRiAxcHggc29saWQiPiDlm7Tln448L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlk"+
                   "OyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiBBQ0NFU1"+
                   "M8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SO"+
                   "iAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiDnprvmrYw8L1NQQU4+Jm5ic3A7Jm5ic3A7PF"+
                   "NQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ"+
                   "7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiPiA8YSBocmVmPUhvdFNjYXJjaEtheS5hc3B4PuabtOWkmi4uLjwvYT48L1NQ"+
                   "QU4+ZGQCCw8QDxYGHg1EYXRhVGV4dEZpZWxkBQzkuabnm67lupPlkI0eDkRhdGFWYWx1ZUZpZWxkBQnlupPplK7noIEeC18hRGF0YUJ"+
                   "vdW5kZ2QQFQoM5Lit5paH5Zu+5LmmDOWkluaWh+WbvuS5pgzkuK3mlofmnJ/liIoM5aSW5paH5pyf5YiKEuS4reaWh+inhuWQrOi1hOa"+
                   "WmRLopb/mlofop4blkKzotYTmlpkS54m55q6K5Y+k57GN5Zu+5LmmDOaXpeaWh+WbvuS5phDkuabnm67mlbDmja7lupM5DOilv+aWh+WbvuS5p"+
                   "hUKATEBMgEzATQBNQE2ATcBOAE5AjEwFCsDCmdnZ2dnZ2dnZ2cWAWZkAg8PEA8WBh8BBQzlrZfmrrXlkI3np7AfAgUJ5omA5bGe6KGoHw"+
                   "NnFgIeCG9uY2hhbmdlBQtHZXRWYWx1ZSgpOxAVCgbpopjlkI0J6LSj5Lu76ICFDOmmhuiXj+WcsOWdgAzmoIflh4bnvJbnoIEM6aKY"+
                   "5ZCN57yp5YaZCeS4u+mimOivjQnlh7rniYjogIUJ5paH54yu5ZCNCee0ouS5puWPtwRpc2NyFQoP6aaG6JeP5Lmm55uu5bqTEuajgOe0ou"+
                   "i0o+S7u+iAheW6kw/ppobol4/lhbjol4/lupMP5qOA57Si57yW56CB5bqTD+mmhuiXj+S5puebruW6kxLmo4DntKLkuLvpopjor43lup"+
                   "MP6aaG6JeP5Lmm55uu5bqTEuajgOe0ouS4gOWvueWkmuW6kw/ppobol4/kuabnm67lupMS5qOA57Si5LiA5a+55aSa5bqTFCsDCmdnZ2dnZ2"+
                   "dnZ2cWAWZkAhcPEA8WBh8BBQblkI3np7AfAgUG5Luj56CBHwNnZBAVNQoJCQkJ5Lit5paHCgkJCQnoi7HmlocKCQkJCeS/hOaWhwoJCQkJ5pel"+
                   "5paHCgkJCQnmnJ3mlocKCQkJCeW+t+aWhwoJCQkJ5rOV5paHFgkJCQnpmL/lsJTlt7TlsLzkuprmlocQCQkJCemYv+aLieS8r+aWhxMJCQkJ55"+
                   "m95L+E572X5pav5paHEwkJCQnkv53liqDliKnkuprmlocNCQkJCee8heeUuOaWhwoJCQkJ5o235paHDQkJCQnovr7ph4zmlocNCQkJCeS4uem6p"+
                   "uaWhxAJCQkJ6KW/54+t54mZ5paHDQkJCQnoiqzlhbDmlocTCQkJCeagvOmygeWQieS6muaWhw0JCQkJ5biM6IWK5paHDQkJCQnojbflhb"+
                   "DmlocQCQkJCeWMiOeJmeWIqeaWhw0JCQkJ5Y2w5Zyw6K+tDQkJCQnljbDlsLzmlocQCQkJCeW4jOS8r+iOseaWhxAJCQkJ5LmM5bC"+
                   "U5aSa5paHDQkJCQnms6Lmlq/mlocNCQkJCeWGsOWym+aWhxAJCQkJ5oSP5aSn5Yip5paHEAkJCQnmn6zln5Tlr6jmlocTCQkJCeWQieWw"+
                   "lOWQieaWr+aWhw0JCQkJ6ICB5oyd5paHCgkJCQnokpnmlocNCQkJCemprOadpeaWhw0JCQkJ5oyq5aiB5paHEAkJCQnlsLzms4rlsJTm"+
                   "locNCQkJCeazouWFsOaWhxAJCQkJ6JGh6JCE54mZ5paHEAkJCQnmma7ku4Dlm77mlocTCQkJCee9l+mprOWwvOS6muaWhw0JCQkJ55"+
                   "Ge5YW45paHEwkJCQnmlq/mtJvkvJDlhYvmlocQCQkJCeWhlOWQieWFi+aWhwoJCQkJ6JeP6K+tCgkJCQnms7DmlocQCQkJCeWcn+iAs+WFt"+
                   "uaWhxAJCQkJ5Zyf5bqT5pu85paHEAkJCQnnu7TlkL7lsJTor60QCQkJCeS5jOWFi+WFsOaWhw0JCQkJ6LaK5Y2X5paHEAkJCQnlk4jokKjlhY"+
                   "vmlocTCQkJCeWNl+aWr+aLieWkq+aWhxMJCQkJ5LmM5YW55Yir5YWL5paHBuS4jemZkBU1AkNOAkdCAlJVAkpQAktSAkRFAkZSA"+
                   "kFCAkFFAkJFAkJHAkJVAkNaAkRBAkRLAkVTAkZJAkdFAkdLAkhMAkhVAklDAklEAklMAklOAklSAklTAklUAktIAktZAkxBAk1OAk1ZAk"+
                   "5PAk5QAlBMAlBUAlBVAlJPAlNFAlNMAlRBAlRCAlRIAlRVAlRZAlVHAlVLAlZOAlhBAllVAllaBuS4jemZkBQrAzVnZ2dnZ2dnZ2dnZ2dnZ2dn"+
                   "Z2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2RkAiMPEA8WBh8BBQnljZXkvY3lkI0fAgUJ6aaG6ZSu56CBHwNnZBA"+
                   "VAQnlm77kuabppoYVAQExFCsDAWcWAWZkAiUPDxYCHwAFSSZuYnNwJm5ic3AmbmJzcCZuYnNwJm5ic3A8aW1nIHNyYz1JbWFnZXMvaWNvb"+
                   "i5naWYgPuiuv+mXrumHj+WFsTc5NDY0NjDmrKFkZGT/roESDpXpkbK4iTrugynLwRHxIQ=="));
        formparams.add(new BasicNameValuePair("TxtIndex","linux"));
        formparams.add(new BasicNameValuePair("DropDownList1","1"));
        formparams.add(new BasicNameValuePair("DropDownList2","馆藏书目库"));
        formparams.add(new BasicNameValuePair("DropDownList4","前方一致"));
        formparams.add(new BasicNameValuePair("DropLanguage","不限"));
        formparams.add(new BasicNameValuePair("DropViewType","列表方式显示"));
        formparams.add(new BasicNameValuePair("HiddenValue",""));
        formparams.add(new BasicNameValuePair("hidtext","题名"));
        formparams.add(new BasicNameValuePair(" hidValue","馆藏书目库"));
        formparams.add(new BasicNameValuePair("DrpHouse","1"));
        formparams.add(new BasicNameValuePair("__EVENTVALIDATION",
                                        "/wEWXwKxlL26AgL7hryJDwLgnZ70BAKjjKTsAgKd5I/lCgKSi6WLBgKTi6WLBgKQi6WLBgKRi6WLBgK"+
                                                "Wi6WLBgKXi6WLBgKUi6WLBgKFi6WLBgKKi6WLBgKSi+WIBgLn/a+ACwLH4J/fCALR+/qwBQK2q9jKAg"+
                                                "Ln/a+ACwKHkLivDgLn/a+ACwKj9tD3DwLn/a+ACwKj9tD3DwLt37y/CQLNhoi5AgKsy7y/CQL95rypDwLTrd"+
                                                "SNCQL4rPjBBwKnururBQLxyoK0AQLY4tPQAgKOpaXzCQKKpbXzCQK9pcHzCQK1pf3zCQK2pfXzCQKPpYHzC"+
                                                "QKJpfXzCQKMpbXzCQKMpYHzCQKNpYHzCQKNpZnzCQKNpcHzCQKOpZXyCQKPpbHzCQKPpanzCQKIpcnzCQKJpdHzCQK"+
                                                "KpYHzCQKKpanzCQK7pa3zCQK7pcHzCQK0pYnzCQK0pY3zCQK0pa3zCQK0paXzCQK0pfXzCQK0pcnzCQK0pc3zCQ"+
                                                "K2pd3zCQK2pZHyCQK3pbHzCQKwpaXzCQKwpZHyCQKxpbnzCQKxpf3zCQKjpa3zCQKjpc3zCQKjpcHzCQK9pbnzC"+
                                                "QK+pYHzCQK+pa3zCQK/pbHzCQK/pbXzCQK/pd3zCQK/pcHzCQK/pZHyCQK4pZnzCQK4panzCQK5paXzCQKrpbHzCQKkpc"+
                                                "HzCQKkpZXyCQLwsfzjBgLsmdDoBgLZtd++DAKM54rGBgLe64HXAgLB36CtCwLgyKrzCALsh7ajDQLj6JzNAZRHERNpi"+
                                                "ESIaR6H6/ZCaGpGM6t2"));
        formparams.add(new BasicNameValuePair("Button1","开始检索"));

        try {
            UrlEncodedFormEntity entity;
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost post = new HttpPost("http://202.194.50.132/gdlisnet/default.aspx");
            post.addHeader("Referer",
                    "http://202.194.50.132/gdlisnet/default.aspx");
            post.addHeader("Accept",
                    "text/javascript, text/html, application/xml, text/xml");
            post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
            post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            post.addHeader("Connection", "Keep-Alive");
            post.addHeader("Cache-Control", "no-cache");
            post.addHeader("Cookie", cookie_id);
            post.addHeader("Host", "202.194.50.132");
            post.setEntity(entity);

            response = client.execute(post);    //开始post表项


            HttpEntity entity2 = response.getEntity();
            // System.out.println(EntityUtils.toString(entity2));
            String end = EntityUtils.toString(entity2);
               // System.out.println("end");

            HttpClient c = new DefaultHttpClient();

            HttpGet get = new HttpGet("http://202.194.50.132/gdlisnet/ScarchList.aspx");
            addHttpGetHeader(get);
            get.addHeader("Referer",
                    "http://202.194.50.132/gdlisnet/default.aspx");
            try {
                HttpResponse res = c.execute(get);
                HttpEntity entity3 = res.getEntity();
                System.out.println("----------search--test-------------");
                 System.out.println(EntityUtils.toString(entity3));
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
		URI uri;

		try {
			uri = URIUtils.createURI("http", "http://202.194.50.132", -1,
					"/gdlisnet/default.aspx",
					URLEncodedUtils.format(qparams, "GBK"), null);
			HttpGet httpget = new HttpGet(uri);
			System.out.println(httpget.getURI());
			addHttpGetHeader(httpget);
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			// System.out.println(EntityUtils.toString(entity));
			return JsoupUtils.getSearchBook(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
*//*
	public List<SearchBook> nextSearchBook() {
		double pg = JsoupUtils.getSearchNumber();
		if (n <= (int) Math.ceil(pg / 20)) {
			n++;
			System.out.println("-------------");
			return searchBook(method, word);
		} else {
			return null;
		}

	}*/
	
	public List<SearchBookAddress> getBookInfo(String number){
		HttpClient client=new DefaultHttpClient();
		HttpGet get=new HttpGet("http://210.38.138.1/bookinfo.aspx?ctrlno="+number);
		addHttpGetHeader(get);
		try {
			HttpResponse response=client.execute(get);
			HttpEntity entity=response.getEntity();
			return JsoupUtils.getSearch_book_info(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
