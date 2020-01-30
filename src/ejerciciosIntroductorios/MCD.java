package ejerciciosIntroductorios;

import java.math.BigInteger;
import java.util.ArrayList;

public class MCD {

	public static void main(String[] args) {
		BigInteger n1 = new BigInteger("30");
		BigInteger n2 = new BigInteger("12");
		System.out.println(descomposicion(n1, n2));
		
	}
	
	public static BigInteger descomposicion (BigInteger n1, BigInteger n2) {
		BigInteger divisor = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		ArrayList<BigInteger> divisores1 = new ArrayList<BigInteger>(), divisores2 = new ArrayList<BigInteger>();
		BigInteger mcd = new BigInteger("1");		
		while(!n1.equals(divisor) || !n2.equals(divisor)) {
			if(!zero.equals(n1.mod(divisor)) && !zero.equals(n2.mod(divisor))) {
				divisor = divisor.nextProbablePrime();
				continue;
			}
			if(zero.equals(n1.mod(divisor))) {
				n1 = n1.divide(divisor);
				divisores1.add(divisor);
				System.out.println("n1    "+divisor);
			}
			if(zero.equals(n2.mod(divisor))) {
				n2 = n2.divide(divisor);
				divisores2.add(divisor);
				System.out.println("n2    "+divisor);
			}
		}
		divisores1.add(divisor);
		divisores2.add(divisor);
		for(int i = 0, j  = 0; i < divisores1.size() && j < divisores2.size();) {
			System.out.println(i+"       "+j);
			if(divisores1.get(i).equals(divisores2.get(j))) {
				mcd.multiply(divisores1.get(i));
				i++;
				j++;
			}else if(divisores1.get(i).compareTo(divisores2.get(j)) < 0) {
				i++;
			}else j++;
		}
		return mcd;
	}
	
}
