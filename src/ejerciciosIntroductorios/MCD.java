package ejerciciosIntroductorios;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class MCD {

	public static void main(String[] args) {
		double [] result;
		BigInteger n1, n2;
		String s1, s2;
		try {
			FileWriter myWriter = new FileWriter("results.csv");
			myWriter.write("Entrada;Euclides;Descomposicion\n");
			/*FileWriter myWriter2 = new FileWriter("resultsPrimos.csv");
			myWriter2.write("Entrada;Primo1;Primo2\n");*/
			for (int i = 1; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					s1 = "";
					s2 = "";
					for (int k = 0; k < i; k++) {
						s1 += (int)(Math.random()*10);
						s2 += (int)(Math.random()*10);
					}
					n1 = new BigInteger(s1);
					n2 = new BigInteger(s2);
					if(n1.equals(new BigInteger("0"))){
						n1 = new BigInteger("1");
					}			
					if(n2.equals(new BigInteger("0"))){
						n2 = new BigInteger("1");
					}
					System.out.println("n1: "+n1);
					System.out.println("n2 "+n2);
					result = test(n1, n2);
					myWriter.write(("n1: "+n1+" n2: "+n2+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
					/*System.out.println("Euclides: "+result[0]+" Descomposition: "+result[1]);
					result = testPrimos(n1);
					myWriter2.write((""+n1+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
					System.out.println("Primo1: "+result[0]+" Primo2: "+result[1]);
					result = testPrimos(n2);
					myWriter2.write((""+n2+";"+result[0]+";"+result[1]+"\n").replace('.', ','));
					System.out.println("Primo1: "+result[0]+" Primo2: "+result[1]);*/
				}
			}
			myWriter.close();
			//myWriter2.close();
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }

		 /* try {
			FileWriter myWriter = new FileWriter("resultsProgresive.csv");
			myWriter.write("Entrada;Euclides;Descomposicion\n");
			FileWriter myWriter2 = new FileWriter("resultsProgresivePrimos.csv");
			myWriter2.write("Entrada;Primo1;Primo2\n");
			for (BigInteger i = new BigInteger("0"); i.compareTo(new BigInteger("20000000000000")) < 0; i = i.add(new BigInteger("1"))) {
				n1 = new BigInteger(""+i);
				n2 = new BigInteger(""+i);
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
		/*	}
			myWriter.close();
			//myWriter2.close();
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }*/

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

	// T(n) = 6+4+2+3+5+7n+5n+15n^2+(7/4)n^3-5-15n-(7/4)n^2+10+1+1+5+3+3+3+1+1+1 = 44-3n+(53/4)n^2+(7/4)n^3
	public static BigInteger descomposicion(BigInteger n1, BigInteger n2) {
		BigInteger divisor = new BigInteger("2"), zero = new BigInteger("0"), one = new BigInteger("1"), lastPrimo = one; //6
		ArrayList<BigInteger> divisores1 = new ArrayList<BigInteger>(), divisores2 = new ArrayList<BigInteger>(); //4
		BigInteger mcd = new BigInteger("1"); //2
		if(n1.equals(zero ) || n2.equals(zero)) return zero; //3
		// Bucle donde entra mientras todavia puedan dividirse
		while (!n1.equals(one) && !n2.equals(one) && divisor.compareTo(n1.divide(lastPrimo)) <= 0 && divisor.compareTo(n2.divide(lastPrimo)) <= 0) { //5+7n+(n-1)(1+1+3+15n+(7/4)n^2)+2+2+1+2+2+1 (peor caso, ambos son el mismo numero primo)
			// Si ninguno de los dos es divisible por el divisor, buscamos el siguiente
			// numero primo
			if (!zero.equals(n1.mod(divisor)) && !zero.equals(n2.mod(divisor))) {
				lastPrimo = divisor;
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
		if(!n1.equals(one) && !n2.equals(one)) {
			if (zero.equals(n1.mod(n2))) {
				divisores1.add(n2);
				divisores2.add(n2);
				//System.out.println("n1    " + divisor);
			}
			// Si el segundo numero es divisible entre el divisor
			else if (zero.equals(n2.mod(n1))) {
				divisores1.add(n1);
				divisores2.add(n1);
				//System.out.println("n2    " + divisor);
			}else {
				divisores1.add(n1);
				divisores2.add(n2);
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
	
	// Metodo que determina cual es el siguiente numero primo al proporcionado |T(n) = 1+2+2+n(2+12+7n/4+1)+1 = 6+15n+(7/4)n^2
	public static BigInteger siguientePrimo(BigInteger n){
		if (new BigInteger("0").equals(n.mod(new BigInteger("2")))) {//1
			n = n.subtract(new BigInteger("1")); //2
		}
		// Vamos sumando 2 al numero proporcionado hasta encontrar un numero primo
		do{
			n = n.add(new BigInteger("2")); //2
		}while(!isPrimo2(n)); // n(2+12+7n/4+1)
		return n; //1
	}

	// Metodo que determina si un numero es primo con pasos de 1 | T(n) = 6+2+3+(n/2)(2+3+2)+1 = 12+7n/2
	public static boolean isPrimo1(BigInteger n) {
		BigInteger zero = new BigInteger("0"), one = new BigInteger("1"), two = new BigInteger("2"); //6
		// Comprobamos si es divisible por algun numero desde 2 hasta n
		for (BigInteger i = new BigInteger("2"); n.divide(two).compareTo(i) > 0; i = i.add(one)) { //2+3+(n/2)(2+3+2)
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
		if(n.mod(two).equals(zero)) return false; //2
		// Comprobamos si es divisible por algun numero impar desde 3 hasta n/2
		for (BigInteger i = new BigInteger("3"); n.divide(two).compareTo(i) > 0; i = i.add(two)) { //2+3+(n/4)(2+3+2)
			if (n.mod(i).equals(zero)) {
				return false;
			}
		}
		return true; //1
	}
}
