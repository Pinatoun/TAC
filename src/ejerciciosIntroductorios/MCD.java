package ejerciciosIntroductorios;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class MCD {

	public static void main(String[] args) {
		double [] result;
		try {
			FileWriter myWriter = new FileWriter("results.csv");
			myWriter.write("Entrada;Euclides;Descomposicion\n");
			FileWriter myWriter2 = new FileWriter("resultsPrimos.csv");
			myWriter2.write("Entrada;Primo1;Primo2\n");
			for (int i = 1; i < 8; i++) {
				BigInteger n1 = new BigInteger(""+(int)((int)(Math.random()*(Math.pow(10, i)))));
				BigInteger n2 = new BigInteger(""+(int)((int)(Math.random()*(Math.pow(10, i)))));
				System.out.println("n1: "+n1);
				System.out.println("n2 "+n2);
				result = test(n1, n2);
				myWriter.write(("n1: "+n1+" n2: "+n2+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
				System.out.println("Euclides: "+result[0]+" Descomposition: "+result[1]);
				result = testPrimos(n1);
				myWriter2.write((""+n1+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
				System.out.println("Primo1: "+result[0]+" Primo2: "+result[1]);
				result = testPrimos(n2);
				myWriter2.write((""+n2+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
				System.out.println("Primo1: "+result[0]+" Primo2: "+result[1]);
			}
			myWriter.close();
			myWriter2.close();
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }

	}

	public static double[] test(BigInteger n1, BigInteger n2){
		double time1 = System.nanoTime();
		System.out.println(euclides(n1, n2));
		double time2 = System.nanoTime();
		double euclides = ((time2-time1)/1000000);
		System.out.println("Execution time for the euclides algorithm(miliseconds): "+euclides);
		time1 = System.nanoTime();
		System.out.println(descomposicion(n1, n2));
		time2 = System.nanoTime();
		double descomposition = ((time2-time1)/1000000);
		System.out.println("Execution time for the descomposition algorithm (miliseconds): "+descomposition);
		return new double[] {euclides, descomposition};
	}

	public static double[] testPrimos(BigInteger n1){
		double time1 = System.nanoTime();
		System.out.println(isPrimo1(n1));
		double time2 = System.nanoTime();
		double primo1 = ((time2-time1)/1000000);
		System.out.println("Execution time for the primo1 algorithm(miliseconds): "+primo1);
		time1 = System.nanoTime();
		System.out.println(isPrimo2(n1));
		time2 = System.nanoTime();
		double primo2 = ((time2-time1)/1000000);
		System.out.println("Execution time for the primo2 algorithm (miliseconds): "+primo2);
		return new double[] {primo1, primo2};
	}

	public static BigInteger descomposicion(BigInteger n1, BigInteger n2) {
		BigInteger divisor = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		ArrayList<BigInteger> divisores1 = new ArrayList<BigInteger>(), divisores2 = new ArrayList<BigInteger>();
		BigInteger mcd = new BigInteger("1");
		// Bucle donde entra mientras todavia puedan dividirse
		while (!n1.equals(one) || !n2.equals(one)) {
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
		for (int i = 0, j = 0; i < divisores1.size() && j < divisores2.size();) {
			// Si los dos divisores son iguales, multiplicamos el mcd por el divisor y
			// pasamos al siguiente elemento de las listas
			if (divisores1.get(i).equals(divisores2.get(j))) {
				mcd = mcd.multiply(divisores1.get(i));
				i++;
				j++;
				// Avanzamos en la lista que tenga el menor divisor
			} else if (divisores1.get(i).compareTo(divisores2.get(j)) < 0) {
				i++;
			} else
				j++;
		}
		return mcd;
	}

	// Metodo de euclides para calcular el mcd
	public static BigInteger euclides(BigInteger n1, BigInteger n2) {
		BigInteger zero = new BigInteger("0"), divisor, dividendo, resto; // 4
		// Seleccionamos el mayor de los dos numeros como dividendo y el otro como divisor
		if(n1.compareTo(n2) < 0) { //2
			divisor = n1;
			dividendo = n2;
		}else if(n1.compareTo(n2) > 0) { //2
			divisor = n2; //1
			dividendo = n1; //1
		}else return n1; // Si son iguales devolvemos ese numero
		// Mientras el resto sea distinto de cero no habremos encontrado el mcd
		while(!zero.equals(dividendo.mod(divisor))) { //2+n(2+2+1+1)????
			resto = dividendo.mod(divisor);
			dividendo = divisor;
			divisor = resto;
		}
		return divisor;
	}
	
	// Metodo que determina cual es el siguiente numero primo al proporcionado
	public static BigInteger siguientePrimo(BigInteger n){
		// Vamos sumando 1 al numero proporcionado hasta encontrar un numero primo
		do{
			n = n.add(new BigInteger("1")); //2
		}while(!isPrimo2(n)); // n(1+1) o n(1+p)??
		return n; //1
	}

	// Metodo que determina si un numero es primo con pasos de 1 | T(n) = 3+1+3+n(2+3+2)+1 = 8+7n
	public static boolean isPrimo1(BigInteger n) {
		BigInteger zero = new BigInteger("0"), one = new BigInteger("1"), two = new BigInteger("2"); //3
		// Comprobamos si es divisible por algun numero desde 2 hasta n/2
		for (BigInteger i = new BigInteger("2"); n.divide(two).compareTo(i) > 0; i = i.add(one)) { //1+3+n(2+3+2)
			if (n.mod(i).equals(zero)) {
				return false;
			}
		}
		return true; //1
	}

	// Metodo que determina si un numero es primo con pasos de 2 T(n) = 2+2+1+3+(n/2)(2+3+2)+1 = 9+7n/2
	public static boolean isPrimo2(BigInteger n) {
		BigInteger zero = new BigInteger("0"), two = new BigInteger("2"); //2
		// Si es divisible por dos no es primo
		if(n.divide(two).equals(zero)) return false; //2
		// Comprobamos si es divisible por algun numero impar desde 3 hasta n/2
		for (BigInteger i = new BigInteger("3"); n.divide(two).compareTo(i) > 0; i = i.add(two)) { //1+3+(n/2)(2+3+2)
			if (n.mod(i).equals(zero)) {
				return false;
			}
		}
		return true; //1
	}
}
