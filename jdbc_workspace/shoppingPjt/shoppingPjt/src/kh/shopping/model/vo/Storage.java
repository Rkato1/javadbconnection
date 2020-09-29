package kh.shopping.model.vo;

public class Storage {
	int s_no;
	int p_no;
	String c_id;
	public Storage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Storage(int s_no, int p_no, String c_id) {
		super();
		this.s_no = s_no;
		this.p_no = p_no;
		this.c_id = c_id;
	}
	public int getS_no() {
		return s_no;
	}
	public void setS_no(int s_no) {
		this.s_no = s_no;
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
	
}
