package PR3AVAEX01LargeInteger;

public class PR3AVAEX01LargeInteger_Miquel_Tanco { 

	private int numeroArray[];

	/* CONSTRUCTORES */

	public PR3AVAEX01LargeInteger_Miquel_Tanco(String numero) {
		parse(numero);
	}

	public PR3AVAEX01LargeInteger_Miquel_Tanco(int tamany) {
		numeroArray = new int[tamany];
	}

	/* METODOS UTILES */

	private int tamany() {
		return numeroArray.length;
	}

	/* METODO ELIMINAR 0 A LA IZQUIERDA, ME ENCONTRABA MUY PERDIDO EN ESTE TEMA Y CHRISTIAN GIL ME AYUDÓ */

	private String extraerNumero(int[] array) {

		boolean entrar = false;
		String resultado = "";

		for (int i=0; i<tamany() ; i++) {                        

			if ((array[i] != 0) || entrar == true) {        
				resultado += array[i];                        
				entrar = true;                                
			}
		}

		if (resultado.equals("")) {                           
			resultado = "0";                                
		}

		return resultado;                             
	}

	/* NO TENIA NI IDEA DE COMO DETERMINAR EL TAMAÑO DE LA ARRAY
	 * DE LOS RESULTADOS PARA QUE CUANDO HICIERA UNA OPERACION
	 * TUVIERA EL TAMAÑO ADECUADO Y LO HE TENIDO QUE BUSCAR POR
	 * INTERNET, EL METODO DE AQUI ABAJO, LO SIENTO 
	 */

	private void checkLength(int length) {
		length += 1;
		// test size
		if(tamany() >= length)
			return;
		// make copy
		int[] x = new int[length];
		int k = length;
		for(int i = tamany() -1; i >= 0; i--)
			x[--k] = numeroArray[i];
		// new array
		numeroArray = x;
	}

	/* METODOS QUE SE PIDEN */

	private void parse(String numero) {
		numeroArray = new int[numero.length()];
		for(int i = 0; i < numero.length(); i++)
			numeroArray[i] = Character.getNumericValue(numero.charAt(i)); // https://docs.oracle.com/javase/7/docs/api/java/lang/Character.html
	}

	/* METODO SUMA */

	public static PR3AVAEX01LargeInteger_Miquel_Tanco add(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {

		int length = y.tamany();
		int suma;
		int llevamos = 0; //cuando una suma es mayor o igual a 10, se sumará uno en el siguiente numero

		/* ESTO ES LO QUE HE COPIADO DE INTERNET */

		if(x.tamany() > length)
			length = x.tamany();
		y.checkLength(length);
		x.checkLength(length);

		PR3AVAEX01LargeInteger_Miquel_Tanco resultado = new PR3AVAEX01LargeInteger_Miquel_Tanco(length + 1);

		for(int i = length; i >= 0; i--) {

			suma = y.numeroArray[i] + x.numeroArray[i] + llevamos;

			if(suma > 10) {
				llevamos = 1;
				suma -= 10;
			}
			else 
				llevamos = 0;

			resultado.numeroArray[i] = suma;
		}

		return resultado;
	}

	/* METODO RESTA, FRACASO TOTAL */

	public static PR3AVAEX01LargeInteger_Miquel_Tanco subtract(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {

		int length = y.tamany();
		int resta;

		/* ESTO ES LO QUE HE COPIADO DE INTERNET */

		if(x.tamany() > length)
			length = x.tamany();
		y.checkLength(length);
		x.checkLength(length);

		PR3AVAEX01LargeInteger_Miquel_Tanco resultado = new PR3AVAEX01LargeInteger_Miquel_Tanco(length + 1);

		for(int i = length; i >= 0; i--) {

			resta = y.numeroArray[i] - x.numeroArray[i];

			resultado.numeroArray[i] = resta;

		}
		return resultado;
	}

	public static boolean isEqualTo(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {

		for(int i = 0; i <y.tamany(); i++)
		{
			if(y.numeroArray[i] != x.numeroArray[i])
			{
				/* SI ALGUN NUMERO NO COINCIDE SALE DIRECTAMENTE DEL METODO, ASI NO ES POSIBLE UN IndexOutOfBoundsException, no te imaginas cuantos de estos me han salido este ejercicio, horrible */
				return false;
			}
		}

		// SI TODOS LOS NUMEROS COINCIDEN RETORNA TRUE
		return true;

	}

	public static boolean isNotEqualTo(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {
		//REUTILIZAMOS METODOS, SI ES TRUE SERÁ FALSE YA QUE USAMOS EL !
		return !(isEqualTo(x,y));
	}

	public static boolean isGreaterThan(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {

		int i = 0;

		while( (y.numeroArray[i] == x.numeroArray[i]) && (i < y.tamany()-1) ) {
			i++;
		}
			/* SI EL NUMERO ES EL MISMO */
		if(i == y.tamany()) 
			return false;
		else
			/* RETORNA TRUE SI EL NUMERO A LA IZQUIERDA ES MAS GRANDE */
			return (y.numeroArray[i] > x.numeroArray[i]);

	}

	public static boolean isLessThan(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {
		int i = 0;
		while( (y.numeroArray[i] == x.numeroArray[i]) && (i < y.tamany()-1) ) {
			i++;
		}
			/* SI EL NUMERO ES EL MISMO */
		if(i ==  y.tamany()) {
			return false;
		}

		else {
			/* RETORNA TRUE SI EL NUMERO A LA IZQUIERDA ES MAS PEQUEÑO */
			return (y.numeroArray[i] < x.numeroArray[i]);
		}
	}

	public static boolean isGreaterThanOrEqualTo(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {
		//REUTILIZAMOS METODOS
		return (isGreaterThan(x, y) || isEqualTo(x, y));
	}

	public static boolean isLessThanOrEqualTo(PR3AVAEX01LargeInteger_Miquel_Tanco x, PR3AVAEX01LargeInteger_Miquel_Tanco y) {
		//REUTILIZAMOS METODOS
		return (isLessThan(x, y) || isEqualTo(x, y));
	}

	public boolean isZero() {

		for(int i = 0; i < tamany(); i++) {
			/* SI ALGUN NUMERO NO COINCIDE SALE DIRECTAMENTE DEL METODO */
			if(numeroArray[i] != 0) {
				return false;
			}
		}
		// SI TODOS LOS NUMEROS COINCIDEN RETORNA TRUE
		return true;
	}

	@Override
	public String toString() {

		String numero = "";

		numero = this.extraerNumero(numeroArray);

		return numero;
	}
} 	    