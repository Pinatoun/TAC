package ejerciciosIntroductorios;

import java.math.BigInteger;
import java.util.ArrayList;

public class MCD {

	public static void main(String[] args) {
		BigInteger n1 = new BigInteger("30");
		BigInteger n2 = new BigInteger("60");
		System.out.println(descomposicion(n1, n2));
		
	}
	
	public static BigInteger descomposicion (BigInteger n1, BigInteger n2) {
		BigInteger divisor = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		ArrayList<BigInteger> divisores1 = new ArrayList<BigInteger>(), divisores2 = new ArrayList<BigInteger>();
		BigInteger mcd = new BigInteger("1");		
		//Bucle donde entra mientras todavia puedan dividirse
		while(!n1.equals(one) || !n2.equals(one)) {
			//Si ninguno de los dos es divisible por el divisor, buscamos el siguiente numero primo
			if(!zero.equals(n1.mod(divisor)) && !zero.equals(n2.mod(divisor))) {
				divisor = divisor.nextProbablePrime();
				continue;
			}
			//Si el primer numero es divisible entre el divisor
			if(zero.equals(n1.mod(divisor))) {
				n1 = n1.divide(divisor);
				divisores1.add(divisor);
				System.out.println("n1    "+divisor);
			}
			//Si el segundo numero es divisible entre el divisor
			if(zero.equals(n2.mod(divisor))) {
				n2 = n2.divide(divisor);
				divisores2.add(divisor);
				System.out.println("n2    "+divisor);
			}
		}
		//Recorremos las dos listas de divisores
		for(int i = 0, j  = 0; i < divisores1.size() && j < divisores2.size();) {
			//Si los dos divisores son iguales, multiplicamos el mcd por el divisor y pasamos al siguiente elemento de las listas
			if(divisores1.get(i).equals(divisores2.get(j))) {
				mcd = mcd.multiply(divisores1.get(i));
				i++;
				j++;
			//Avanzamos en la lista que tenga el menor divisor
			}else if(divisores1.get(i).compareTo(divisores2.get(j)) < 0) {
				i++;
			}else j++;
		}
		return mcd;
	}
	
}
