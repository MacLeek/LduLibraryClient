package cn.ldu.edu.data;

public class SearchBook {

	private String name;
	private String systemnumber;//ÏµÍ³±àºÅ
	private String writer;
	private String publish;
	private String date;
	private String searchnumber;
	private String max;
	private String min;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSystemnumber() {
		return systemnumber;
	}
	public void setSystemnumber(String systemnumber) {
		this.systemnumber = systemnumber;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSearchnumber() {
		return searchnumber;
	}
	public void setSearchnumber(String searchnumber) {
		this.searchnumber = searchnumber;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	@Override
	public String toString() {
		return name+"\n"+writer+"\n"+publish+"\n"+date+"\n"+searchnumber+"\n"+max+"\n"+min;
	}
	
}
