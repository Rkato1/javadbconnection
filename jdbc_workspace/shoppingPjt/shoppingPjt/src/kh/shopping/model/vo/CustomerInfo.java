package kh.shopping.model.vo;

public class CustomerInfo {
	private String cId;
	private String cPw;
	private String cName;
	private String cPhone;
	public CustomerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerInfo(String cId, String cPw, String cName, String cPhone) {
		super();
		this.cId = cId;
		this.cPw = cPw;
		this.cName = cName;
		this.cPhone = cPhone;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getcPw() {
		return cPw;
	}
	public void setcPw(String cPw) {
		this.cPw = cPw;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcPhone() {
		return cPhone;
	}
	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}
	
	
}
