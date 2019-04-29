package PR3AVAEX01Rational;

public class PR3AVAEX01Rational_Miquel_Tanco_TEST {

	public static void main(String[]args) {
		
		PR3AVAEX01Rational_Miquel_Tanco fraccion1 = new PR3AVAEX01Rational_Miquel_Tanco();
		PR3AVAEX01Rational_Miquel_Tanco fraccion2 = new PR3AVAEX01Rational_Miquel_Tanco(1,4);
		PR3AVAEX01Rational_Miquel_Tanco resultado;
		
		/* PROBAMOS EL TOSTRING */
		
		System.out.println(fraccion1.toString());
		System.out.println(fraccion2.toString());
		
		/* SUMAMOS */
		
		resultado = PR3AVAEX01Rational_Miquel_Tanco.add(fraccion1, fraccion2);
		
		System.out.println("Suma: " + resultado);
		
		/* RESTAMOS */
		
		resultado = PR3AVAEX01Rational_Miquel_Tanco.subtract(fraccion1, fraccion2);
		
		System.out.println("Resta: " + resultado);
		
		/* MULTIPLICAMOS */
		
		resultado = PR3AVAEX01Rational_Miquel_Tanco.multiply(fraccion1, fraccion2);
		
		System.out.println("Multiplicacion: " + resultado);
		
		/* DIVIDIMOS */
		
		resultado = PR3AVAEX01Rational_Miquel_Tanco.divide(fraccion1, fraccion2);
		
		System.out.println("Division: " + resultado);
		
	}	
}
