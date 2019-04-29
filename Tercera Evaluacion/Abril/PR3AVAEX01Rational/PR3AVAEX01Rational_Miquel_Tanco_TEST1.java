package PR3AVAEX01Rational;

public class PR3AVAEX01Rational_Miquel_Tanco_TEST1{
    public static void main(String[] args){
    	PR3AVAEX01Rational_Miquel_Tanco r1 = new PR3AVAEX01Rational_Miquel_Tanco(1, 1);
    	PR3AVAEX01Rational_Miquel_Tanco r2 = new PR3AVAEX01Rational_Miquel_Tanco(1, 6);
        PR3AVAEX01Rational_Miquel_Tanco rAdd = PR3AVAEX01Rational_Miquel_Tanco.add(r1, r2);
        PR3AVAEX01Rational_Miquel_Tanco rSub = PR3AVAEX01Rational_Miquel_Tanco.subtract(r1, r2);
        PR3AVAEX01Rational_Miquel_Tanco rMul = PR3AVAEX01Rational_Miquel_Tanco.multiply(r1, r2);
        PR3AVAEX01Rational_Miquel_Tanco rDiv = PR3AVAEX01Rational_Miquel_Tanco.divide(r1, r2);

        System.out.println("r1 " + r1.toString());
        System.out.println("r2 " + r2.toString());

        System.out.println("add " + rAdd.toString());
        System.out.println("sub " + rSub.toString());
        System.out.println("mul " + rMul.toString());
        System.out.println("div " + rDiv.toString());
        
    }
}