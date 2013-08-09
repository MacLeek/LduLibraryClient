package cn.ldu.edu.data;

public class BookInfo {
	private String borrowed_time;//借期
	private String back_time;//还期
	private String book_writer;//题名／著者
	private String book_type;//图书类型
	private String login_number;//登陆号
	public String getBorrowed_time() {
		return borrowed_time;
	}
	public void setBorrowed_time(String borrowed_time) {
		this.borrowed_time = borrowed_time;
	}
	public String getBack_time() {
		return back_time;
	}
	public void setBack_time(String back_time) {
		this.back_time = back_time;
	}
	public String getBook_writer() {
		return book_writer;
	}
	public void setBook_writer(String book_writer) {
		this.book_writer = book_writer;
	}
	public String getBook_type() {
		return book_type;
	}
	public void setBook_type(String book_type) {
		this.book_type = book_type;
	}
	public String getLogin_number() {
		return login_number;
	}
	public void setLogin_number(String login_number) {
		this.login_number = login_number;
	}
	

}
