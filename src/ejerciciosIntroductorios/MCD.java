package ejerciciosIntroductorios;

import java.math.BigInteger;
import java.util.ArrayList;

public class MCD {

	public static void main(String[] args) {
		BigInteger n1 = new BigInteger("11");
		BigInteger n2 = new BigInteger("11");
		double time1 = System.nanoTime();
		System.out.println(euclides(n1, n2));
		double time2 = System.nanoTime();
		System.out.println("Execution time for the euclides algorithm(miliseconds): "+((time2-time1)/1000000));
		time1 = System.nanoTime();
		System.out.println(descomposicion(n1, n2));
		time2 = System.nanoTime();
		System.out.println("Execution time for the descomposition algorithm (miliseconds): "+((time2-time1)/1000000));
	}

	// T(n) = 6+4+2+3+5+7n+5n+15n^2+(7/4)n^3-5-15n-(7/4)n^2+10+1+1+5+3+3+3+1+1+1 = 44-3n+(53/2)n^2+(7/4)n^3
	public static BigInteger descomposicion(BigInteger n1, BigInteger n2) {
		BigInteger divisor = new BigInteger("2"), zero = new BigInteger("0"), one = new BigInteger("1"); //6
		ArrayList<BigInteger> divisores1 = new ArrayList<BigInteger>(), divisores2 = new ArrayList<BigInteger>(); //4
		BigInteger mcd = new BigInteger("1"); //2
		if(n1.equals(zero ) || n2.equals(zero)) return zero; //3
		// Bucle donde entra mientras todavia puedan dividirse
		while (!n1.equals(one) || !n2.equals(one)) { //5+7n+(n-1)(1+1+3+15n+(7/4)n^2)+2+2+1+2+2+1 (peor caso, ambos son el mismo numero primo)
			// Si ninguno de los dos es divisible por el divisor, buscamos el siguiente
			// numero primo
			if (!zero.equals(n1.mod(divisor)) && !zero.equals(n2.mod(divisor))) {
				divisor = siguientePrimo(divisor);
				continue;
			}
			// Si el primer numero es divisible entre el divisor
			if (zero.equals(n1.mod(divisor))) {
				n1 = n1.divide(divisor);
				divisores1.add(divisor);
				//System.out.println("n1    " + divisor);
			}
			// Si el segundo numero es divisible entre el divisor
			if (zero.equals(n2.mod(divisor))) {
				n2 = n2.divide(divisor);
				divisores2.add(divisor);
				//System.out.println("n2    " + divisor);
			}
		}
		// Recorremos las dos listas de divisores
		for (int i = 0, j = 0; i < divisores1.size() && j < divisores2.size();) { //1+1+5+3
			// Si los dos divisores son iguales, multiplicamos el mcd por el divisor y
			// pasamos al siguiente elemento de las listas
			if (divisores1.get(i).equals(divisores2.get(j))) { //3
				mcd = mcd.multiply(divisores1.get(i)); //3
				i++; //1
				j++; //1
				// Avanzamos en la lista que tenga el menor divisor
			} else if (divisores1.get(i).compareTo(divisores2.get(j)) < 0) {
				i++;
			} else
				j++;
		}
		return mcd; //1
	}

	// Metodo de euclides para calcular el mcd | T(n) = 5+2+2+1+1+3+n(3+2+1+1)+1 = 15+7n
	public static BigInteger euclides(BigInteger n1, BigInteger n2) {
		BigInteger zero = new BigInteger("0"), divisor, dividendo, resto; // 5
		// Seleccionamos el mayor de los dos numeros como dividendo y el otro como divisor
		if(n1.compareTo(n2) < 0) { //2
			divisor = n1;
			dividendo = n2;
		}else if(n1.compareTo(n2) > 0) { //2
			divisor = n2; //1
			dividendo = n1; //1
		}else return n1; // Si son iguales devolvemos ese numero
		// Mientras el resto sea distinto de cero no habremos encontrado el mcd
		while(!zero.equals(dividendo.mod(divisor))) { //3+n(3+2+1+1)
			resto = dividendo.mod(divisor);
			dividendo = divisor;
			divisor = resto;
		}
		return divisor; //1
	}
	
	// Metodo que determina cual es el siguiente numero primo al proporcionado |T(n) = 2+n(2+12+7n/4+1)+1 = 3+15n+(7/4)n^2
	public static BigInteger siguientePrimo(BigInteger n){
		// Vamos sumando 1 al numero proporcionado hasta encontrar un numero primo
		do{
			n = n.add(new BigInteger("1")); //2
		}while(!isPrimo2(n)); // n(2+12+7n/4+1)
		return n; //1
	}

	// Metodo que determina si un numero es primo con pasos de 1 | T(n) = 6+2+3+n(2+3+2)+1 = 12+7n
	public static boolean isPrimo1(BigInteger n) {
		BigInteger zero = new BigInteger("0"), one = new BigInteger("1"), two = new BigInteger("2"); //6
		// Comprobamos si es divisible por algun numero desde 2 hasta n
		for (BigInteger i = new BigInteger("2"); n.divide(two).compareTo(i) > 0; i = i.add(one)) { //2+3+n/2(2+3+2)
			if (n.mod(i).equals(zero)) {
				return false;
			}
		}
		return true; //1
	}

	// Metodo que determina si un numero es primo con pasos de 2 | T(n) = 4+2+2+3+(n/4)(2+3+2)+1 = 12+7n/4
	public static boolean isPrimo2(BigInteger n) {
		BigInteger zero = new BigInteger("0"), two = new BigInteger("2"); //4
		// Si es divisible por dos no es primo
		if(n.divide(two).equals(zero)) return false; //2
		// Comprobamos si es divisible por algun numero impar desde 3 hasta n/2
		for (BigInteger i = new BigInteger("3"); n.divide(two).compareTo(i) > 0; i = i.add(two)) { //2+3+(n/4)(2+3+2)
			if (n.mod(i).equals(zero)) {
				return false;
			}
		}
		return true; //1
	}
}
