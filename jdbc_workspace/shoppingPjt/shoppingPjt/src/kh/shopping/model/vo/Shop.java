package kh.shopping.model.vo;

public class Shop {
	int p_no;
	String c_id;
	int price;
	String p_name;
	int p_count;
	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Shop(int p_no, String c_id, int price, String p_name, int p_count) {
		super();
		this.p_no = p_no;
		this.c_id = c_id;
		this.price = price;
		this.p_name = p_name;
		this.p_count = p_count;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getP_count() {
		return p_count;
	}
	public void setP_count(int p_count) {
		this.p_count = p_count;
	}
	
}
