package cn.ldu.edu.net;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.ldu.edu.data.BookInfo;
import cn.ldu.edu.data.SearchBook;
import cn.ldu.edu.data.SearchBookAddress;
import cn.ldu.edu.data.StudentInfo;

public class JsoupUtils {
	public static String get__VIEWSTATE() {
		return __VIEWSTATE;
	}

	public static String get__EVENTVALIDATION() {
		return __EVENTVALIDATION;
	}
	private static String __VIEWSTATE;
	private static String __EVENTVALIDATION;
	private static int borrowedmax;//借书历史页数
//	private static StudentInfo student;
//	private static BookInfo book;
	private static int searchNumber;
	
	public static int getSearchNumber() {
		return searchNumber;
	}
	public static int getBorrowedmax() {
		return borrowedmax;
	}

	public static void setBorrowedmax(int borrowedmax) {
		JsoupUtils.borrowedmax = borrowedmax;
	}

	// 解释用户信息
	public static StudentInfo getUserInfo(String html) {
        Document doc = Jsoup.parse(html);
        //Elements es = doc.getElementById("userInfoContent").getElementsByClass(
		//		"infoline");

        Elements es = doc.getElementsByClass("TableContent1");
        System.out.println("--1--");
        System.out.println(es);
        StudentInfo student = new StudentInfo();
		int count = 0;
		for (Element e : es) {
		 //System.out.println(e.getElementById("LblNo").text());
			System.out.println("---------");

			switch (count) {
			case 0:
				student.setNumber(e.getElementById("LblNo").text());
                System.out.println(e.getElementById("LblNo").text());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
   				student.setName(e.getElementById("LblreaderName").text());
                System.out.println(e.getElementById("LblreaderName").text());

                break;
			case 5:
				student.setType(e.getElementById("LblReaderSex").text());
                System.out.println(e.getElementById("LblReaderSex").text());

                break;
			case 6:
				student.setUnits(e.getElementById("LblReaderUnit").text());
                System.out.println(e.getElementById("LblReaderUnit").text());

				break;
			case 7:
				student.setState(e.getElementById("LblReaderType").text());
                System.out.println(e.getElementById("LblReaderType").text());

				break;
			case 8:
                break;
            case 9:

                break;

                case 10:
                    student.setAddress(e.getElementById("lblAdess").text());
                    System.out.println(e.getElementById("lblAdess").text());

                    break;

                case 11:
                    student.setOther(e.getElementById("LblPostCode").text());
                    System.out.println(e.getElementById("LblPostCode").text());

                    break;

                case 12:

                    student.setTele(e.getElementById("LblPhoneNo").text());
                    System.out.println(e.getElementById("LblPhoneNo").text());
                    student.setMobile(e.getElementById("LblPhoneNo").text());
                    System.out.println(e.getElementById("LblPhoneNo").text());
                    break;

                case 13:

                    student.setEmail(e.getElementById("LblEmail").text());
                    System.out.println(e.getElementById("LblEmail").text());

                    break;

                case 14:
                    student.setMoney(e.getElementById("LblQfk").text());
                    System.out.println(e.getElementById("LblQfk").text());

                    break;

                case 15:

                    break;
                case 16:

                    break;
                case 17:

                    break;
			default:
				break;
			}
			count++;
		}
		System.out.println(student);
		return student;
	}

	public static void get__Viewstate(String html){
		Document doc = Jsoup.parse(html);		
		System.out.println("-------view--------");
		__VIEWSTATE=doc.getElementById("__VIEWSTATE").attr("value").toString();
		__EVENTVALIDATION=doc.getElementById("__EVENTVALIDATION").attr("value").toString();
	}
	// 解释借阅情况
	public static List<BookInfo> getBorrowedContent(String html) {
		List<BookInfo> list=new ArrayList<BookInfo>();
		Document doc = Jsoup.parse(html);
		Elements es = doc.getElementsByAttributeValue("color","Black");//doc.getElementsByTag("td");
		System.out.println("-------es--------");
        System.out.println(es);

        __VIEWSTATE=doc.getElementById("__VIEWSTATE").attr("value").toString();
		__EVENTVALIDATION=doc.getElementById("__EVENTVALIDATION").attr("value").toString();
       // System.out.println(__VIEWSTATE);

        int n = 1;
		for (Element e : es) {
		//	if (n % 7 == 2) {
				System.out.println("--------borrowed-----");
                //System.out.println(e.text());
               // System.out.println(tdes.attr("color","Black"));
//				int count = 0;
				BookInfo book=new BookInfo();
                    //System.out.println(tdes.get(i).text());
                    n=n%7;
					switch (n) {
					case 0:
                        System.out.println("类型  "+e.text());
                        book.setBook_type(e.text());
						break;
					case 1:
                        System.out.println("登录号  " + e.text());
                        book.setLogin_number(e.text());
                        break;
					case 2:
						System.out.println("题名／著者   " + e.text());
						book.setBook_writer(e.text());
						break;
					case 3:

                        System.out.println("借期  " + e.text());
                        book.setBorrowed_time(e.text());
                        break;
					case 4:
                        book.setBack_time(e.text());
                        System.out.println("最迟应还期 "+e.text());
                        break;
                    case 5:
                         book.setBook_type(e.text());
                         System.out.println("已续借 "+e.text());
                         break;
					default:
						break;

				}
				list.add(book);

			n++;
        }
		//}
		return list;
	}
	//解释借书历史记录
	public static void getBorrowedHistory(String html){
		Document doc=Jsoup.parse(html);
		borrowedmax=doc.getElementById("ctl00_cpRight_Pagination2_gotoddl2").getElementsByTag("option").size();
//		System.out.println(doc.getElementsByClass("tb"));
		Elements ees=doc.getElementsByClass("tb");
		for(Element e:ees){
			Elements es=e.getElementsByTag("tr");
			for(int j=1;j<es.size();j++){
				
				System.out.println("------------------");
//				System.out.println(tmp.text());
				Elements des=es.get(j).getElementsByTag("td");
//				System.out.println(es.get(j).getElementsByTag("td").text());
				BookInfo book=new BookInfo();
				for(int i=0;i<des.size();i++){
					System.out.println(des.get(i).text());
					switch (i) {
					case 0:
						book.setBorrowed_time(des.get(i).text());
						break;
					case 1:
						book.setBack_time(des.get(i).text());
						break;
					case 2:
						book.setBook_writer(des.get(i).text());
						break;
					case 3:
						book.setBook_type(des.get(i).text());
						break;
					case 4:
						book.setLogin_number(des.get(i).text());
						break;
					
					}
				}
			}
		}
		
	}
	
	//解释搜书
		public static List<SearchBook> getSearchBook(String html){
			List<SearchBook> list=new ArrayList<SearchBook>();
			Document doc=Jsoup.parse(html);
			searchNumber=Integer.parseInt(doc.getElementById("ctl00_ContentPlaceHolder1_countlbl").text());
			if(searchNumber==0){
				return null;
			}
			Elements es=doc.getElementsByClass("tb").get(0).getElementsByTag("tr");
			for(int i=1;i<es.size();i++){
				SearchBook book=new SearchBook();
				Elements tdes= es.get(i).getElementsByTag("td");
				for(int j=0;j<tdes.size();j++){
					switch (j) {
					case 0:
						book.setSystemnumber(tdes.get(j).getElementsByTag("input").attr("value"));
						break;
					case 1:
						book.setName(tdes.get(j).text().trim());
						break;
					case 2:
						book.setWriter(tdes.get(j).text().trim());
						break;
					case 3:
						book.setPublish(tdes.get(j).text().trim());
						break;
					case 4:
						book.setDate(tdes.get(j).text().trim());
						break;
					case 5:
						book.setSearchnumber(tdes.get(j).text().trim());
						break;
					case 6:
						book.setMax(tdes.get(j).text().trim());
						break;
					case 7:
						book.setMin(tdes.get(j).text().trim());
						break;
					default:
						break;
					}
				}
				list.add(book);
				System.out.println(book);
			}
			return list;
		}
		//解释图书放在的位置
		public static List<SearchBookAddress> getSearch_book_info(String html){
			List<SearchBookAddress> list=new ArrayList<SearchBookAddress>();
			Document doc=Jsoup.parse(html);
			Elements es=doc.getElementById("bardiv").getElementsByTag("tr");
			int len=es.size()-1;
			for(int i=1;i<es.size();i++){
				Elements tdes=es.get(i).getElementsByTag("td");
				System.out.println("----------");
				SearchBookAddress sba=new SearchBookAddress();
				for(int j=0;j<tdes.size();j++){
					if(j==0){
						sba.setAddress(tdes.get(j).text().trim());
						System.out.println(tdes.get(j).text().trim());
					}
					if(j==5){
						sba.setIslent(tdes.get(j).text().trim());
						System.out.println(tdes.get(j).text().trim());
					}
				}
				list.add(sba);
			}
			return list;
		}

}
