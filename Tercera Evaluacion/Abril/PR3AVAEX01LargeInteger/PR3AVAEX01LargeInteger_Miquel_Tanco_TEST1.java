package PR3AVAEX01LargeInteger;


public class PR3AVAEX01LargeInteger_Miquel_Tanco_TEST1{
    public static void main(String[] args){
    	
    	PR3AVAEX01LargeInteger_Miquel_Tanco li1 = new PR3AVAEX01LargeInteger_Miquel_Tanco("998");
    	PR3AVAEX01LargeInteger_Miquel_Tanco li2 = new PR3AVAEX01LargeInteger_Miquel_Tanco("999");
    	PR3AVAEX01LargeInteger_Miquel_Tanco liAdd = PR3AVAEX01LargeInteger_Miquel_Tanco.add(li1, li2);
    	PR3AVAEX01LargeInteger_Miquel_Tanco liSub = PR3AVAEX01LargeInteger_Miquel_Tanco.subtract(li1, li2);


    	System.out.println("li1 " + li1.toString());
    	System.out.println("li2 " + li2.toString());

    	System.out.println("add " + liAdd.toString()); 
    	System.out.println("sub " + liSub.toString()); 
    	System.out.println("isEqualTo " + PR3AVAEX01LargeInteger_Miquel_Tanco.isEqualTo(li1, li2)); 
    	System.out.println("isNotEqualTo " + PR3AVAEX01LargeInteger_Miquel_Tanco.isNotEqualTo(li1, li2)); 
    	System.out.println("isGreaterThan " + PR3AVAEX01LargeInteger_Miquel_Tanco.isGreaterThan(li1, li2)); 
    	System.out.println("isLessThan " + PR3AVAEX01LargeInteger_Miquel_Tanco.isLessThan(li1, li2));
    	System.out.println("isGreaterThanOrEqualTo " + PR3AVAEX01LargeInteger_Miquel_Tanco.isGreaterThanOrEqualTo(li1, li2));
    	System.out.println("isLessThanOrEqualTo " + PR3AVAEX01LargeInteger_Miquel_Tanco.isLessThanOrEqualTo(li1, li2)); 
    	System.out.println("li1 isZero " + li1.isZero()); 
    	System.out.println("li1 isZero " + li2.isZero());
		 
    }
}