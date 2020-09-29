package testPjt;

public class Test {
	public void main() {
		System.out.println("1번째출력");
		//1초
		delay(1000);
		System.out.println("2번째출력");
	}
	
	void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
