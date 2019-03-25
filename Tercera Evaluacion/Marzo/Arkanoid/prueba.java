package Arkanoid;

import java.util.Random;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random rd = new Random();
		
		double x = 0;
		
		for(int i = 0; i < 100; i++) {
			System.out.print(x + " , ");
			x = rd.nextDouble();
		}
	}

}
