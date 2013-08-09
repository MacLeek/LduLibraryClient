package cn.ldu.edu.data;

import java.util.Map;

public class Cookie {

	private Map<String, String> name;
	private Map<String, String> path;
	private Map<String, String> host;
	private Map<String, String> domain;
	private String other;
	public Map<String, String> getName() {
		return name;
	}
	public void setName(Map<String, String> name) {
		this.name = name;
	}
	public Map<String, String> getPath() {
		return path;
	}
	public void setPath(Map<String, String> path) {
		this.path = path;
	}
	public Map<String, String> getHost() {
		return host;
	}
	public void setHost(Map<String, String> host) {
		this.host = host;
	}
	public Map<String, String> getDomain() {
		return domain;
	}
	public void setDomain(Map<String, String> domain) {
		this.domain = domain;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
}
