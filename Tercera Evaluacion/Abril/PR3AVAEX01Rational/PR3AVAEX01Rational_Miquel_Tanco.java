package PR3AVAEX01Rational;

public class PR3AVAEX01Rational_Miquel_Tanco {

	private int numerador;
	private int denominador;

	/* CONSTRUCTORES */

	public PR3AVAEX01Rational_Miquel_Tanco(int numerador, int denominador) {

		/* He usado IllegalArgumentException en vez de ArithmeticException por lo que pone en la documentacion:
		 * 
		 *   - Thrown to indicate that a method has been passed an illegal or inappropriate argument.
		 * 
		 *   https://docs.oracle.com/javase/7/docs/api/java/lang/IllegalArgumentException.html
		 */ 

		if (denominador == 0) {
			throw new IllegalArgumentException("ERROR ----- Division por cero");
		}

		int[] lista = simplificarConstructor(numerador, denominador);

		this.numerador = lista[0];
		this.denominador = lista[1];
	}

	public PR3AVAEX01Rational_Miquel_Tanco() {
		this.numerador = 1;
		this.denominador = 2;
	}

	/* GETTERS AND SETTERS */

	public int getNumerador() {
		return numerador;
	}

	public void setNumerador(int numerador) {
		this.numerador = numerador;
	}

	public int getDenominador() {
		return denominador;
	}

	public void setDenominador(int denominador) {
		this.denominador = denominador;
	}

	/* METODO A */

	public static PR3AVAEX01Rational_Miquel_Tanco add(PR3AVAEX01Rational_Miquel_Tanco fraccion1, PR3AVAEX01Rational_Miquel_Tanco fraccion2) {

		/*
		 * 				a	c	a*d + b*c
		 * 				- + - = ----------
		 * 				b	d	   b*d
		 *
		 *	https://www.youtube.com/watch?v=c-dfIl8TIus
		 */

		/*

				COMO LO HE HECHO AL PRINCIPIO, DESPUES LO HE SIMPLIFICADO

		int x;
		int z;

		x = a.numerador * b.denominador; // a*d
		z = a.denominador * b.numerador; //b*c

		x = x+z; // resultado de ad + bc

		z = a.denominador * b.denominador; // b*d

		PR3AVAEX01Rational_Miquel_Tanco resultado = new PR3AVAEX01Rational_Miquel_Tanco(x,z);

		 */

		PR3AVAEX01Rational_Miquel_Tanco resultado = new PR3AVAEX01Rational_Miquel_Tanco();

		resultado.numerador = fraccion1.numerador*fraccion2.denominador + fraccion1.denominador*fraccion2.numerador; //a*d + b*c
		resultado.denominador = fraccion1.denominador*fraccion2.denominador; //b*d

		return resultado.simplificar(resultado);
	}

	/* METODO B */

	public static PR3AVAEX01Rational_Miquel_Tanco subtract(PR3AVAEX01Rational_Miquel_Tanco fraccion1, PR3AVAEX01Rational_Miquel_Tanco fraccion2) {

		/*
		 * 				a	c	a*d - b*c
		 * 				- - - = ----------
		 * 				b	d	   b*d
		 *
		 *	https://www.youtube.com/watch?v=QKmgfB1Rt44
		 */

		PR3AVAEX01Rational_Miquel_Tanco resultado = new PR3AVAEX01Rational_Miquel_Tanco();

		resultado.numerador = fraccion1.numerador*fraccion2.denominador - fraccion1.denominador*fraccion2.numerador; //a*d - b*c
		resultado.denominador = fraccion1.denominador*fraccion2.denominador; //b*d

		return resultado.simplificar(resultado);
	}

	/* METODO C */

	public static PR3AVAEX01Rational_Miquel_Tanco multiply(PR3AVAEX01Rational_Miquel_Tanco fraccion1, PR3AVAEX01Rational_Miquel_Tanco fraccion2) {

		/*
		 * 				a	c	   a*c
		 * 				- * - = ----------
		 * 				b	d	   b*d
		 *
		 *	https://www.youtube.com/watch?v=AoTe3QJ479A
		 */

		PR3AVAEX01Rational_Miquel_Tanco resultado = new PR3AVAEX01Rational_Miquel_Tanco();

		resultado.numerador = fraccion1.numerador*fraccion2.numerador; //a*c
		resultado.denominador =  fraccion1.denominador*fraccion2.denominador; //b*d

		return resultado.simplificar(resultado);	
	}

	/* METODO D */

	public static PR3AVAEX01Rational_Miquel_Tanco divide(PR3AVAEX01Rational_Miquel_Tanco fraccion1, PR3AVAEX01Rational_Miquel_Tanco fraccion2) {

		/*
		 * 				a	c	   a*d
		 * 				- / - = ----------
		 * 				b	d	   b*c
		 *
		 *	https://www.youtube.com/watch?v=wNaHtBhPVhU
		 */

		PR3AVAEX01Rational_Miquel_Tanco resultado = new PR3AVAEX01Rational_Miquel_Tanco();

		resultado.numerador = fraccion1.numerador*fraccion2.denominador; //a*d
		resultado.denominador =  fraccion1.denominador*fraccion2.numerador; //b*c

		return resultado.simplificar(resultado);
	}

	/* METODO E */

	public String toString() {
		return "(" + numerador + "/" + denominador + ")";
	}

	/* METODOS PRIVADOS */

	private PR3AVAEX01Rational_Miquel_Tanco simplificar(PR3AVAEX01Rational_Miquel_Tanco a) {
		int MaxComDiv; // MAXIMO COMUN DIVISOR
		MaxComDiv = MCD(a.numerador,a.denominador); //BUSCAMOS EL MCD
		a.numerador /= MaxComDiv; //SIMPLIFICAMOS
		a.denominador /= MaxComDiv; //SIMPLIFICAMOS
		return a;
	}

	private int[] simplificarConstructor(int numerador,int denominador) {
		int MaxComDiv; // MAXIMO COMUN DIVISOR
		MaxComDiv = MCD(numerador,denominador); //BUSCAMOS EL MCD
		numerador /= MaxComDiv; //SIMPLIFICAMOS
		denominador /= MaxComDiv; //SIMPLIFICAMOS
		return new int[] {numerador, denominador};
	}

	private int MCD(int numerador,int denominador) {

		//UTILIZAMOS EUCLIDES

		if(denominador==0) //Si el resto es 0
			return numerador;
		else
			return MCD(denominador, numerador % denominador);
	}
}
