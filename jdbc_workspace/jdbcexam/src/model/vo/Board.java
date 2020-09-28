package model.vo;

import java.sql.Date;

public class Board {
	int b_no;
	String b_title;
	String b_content;
	String b_writer;
	int read_count;	
	Date write_date;
	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Board(int b_no, String b_title, String b_content, String b_writer, int read_count, Date write_date) {
		super();
		this.b_no = b_no;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_writer = b_writer;
		this.read_count = read_count;
		this.write_date = write_date;
	}
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_writer() {
		return b_writer;
	}
	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	
}
