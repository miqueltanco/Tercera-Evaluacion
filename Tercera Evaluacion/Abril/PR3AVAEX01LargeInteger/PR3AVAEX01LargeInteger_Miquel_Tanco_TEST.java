package PR3AVAEX01LargeInteger;

public class PR3AVAEX01LargeInteger_Miquel_Tanco_TEST {

	public static void main(String[] args) {
		
		/* NUMEROS ESCOGIDOS */
		
		PR3AVAEX01LargeInteger_Miquel_Tanco x = new PR3AVAEX01LargeInteger_Miquel_Tanco("369823649230023487843698236492300");
		PR3AVAEX01LargeInteger_Miquel_Tanco y = new PR3AVAEX01LargeInteger_Miquel_Tanco("743343421677221");
		PR3AVAEX01LargeInteger_Miquel_Tanco z = new PR3AVAEX01LargeInteger_Miquel_Tanco("0");
		
		/* PRUEBAS SUMA Y RESTA, LA RESTA HORRIBLE */
		
		//System.out.println(x + " + " + y + " = " + x.add(y));
		//System.out.println(x + " - " + y + " = " + x.subtract(y));
		
		/* PRUEBAS COMPARACIONES */
		
		//System.out.println("Es "+ x + " igual a " + y + ": " + x.isEqualTo(y));
		//System.out.println("Es "+ x + " diferente a " + y + ": " + x.isNotEqualTo(y));
		//System.out.println("Es "+ x + " mayor a " + y + ": " + x.isGreaterThan(y));
		//System.out.println("Es "+ x + " menor a " + y + ": " + x.isLessThan(y));
		//System.out.println("Es "+ x + " más grande o igual a " + y + ": " + x.isGreaterThanOrEqualTo(y));
		//System.out.println("Es "+ x + " más pequeño o igual a " + y + ": " + x.isLessThanOrEqualTo(y));
		
		/* IGUAL A CERO */
		
		System.out.println("Es "+ x + " igual a cero: " + x.isZero());
		System.out.println("Es "+ y + " igual a cero: " + y.isZero());
		System.out.println("Es "+ z + " igual a cero: " + z.isZero());		
	}
}
