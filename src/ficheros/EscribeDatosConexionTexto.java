package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EscribeDatosConexionTexto {

	public static void main(String[] args) {

		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("DatosConexionTexto.txt"));

			fichero.write("com.mysql.jdbc.Driver"); // escribe una línea
			fichero.newLine(); // escribe un salto de línea

			fichero.write("jdbc:mysql://localhost/"); // escribe una línea
			fichero.newLine(); // escribe un salto de línea

			fichero.write("tiendas"); // escribe una línea
			fichero.newLine(); // escribe un salto de línea

			fichero.write("tiendas"); // escribe una línea
			fichero.newLine(); // escribe un salto de línea

			fichero.write("tiendas"); // escribe una línea
			fichero.newLine(); // escribe un salto de línea

			fichero.close();
		} catch (FileNotFoundException fn) {
			System.out.println("No se encuentra el fichero");
		} catch (IOException io) {
			System.out.println("Error de E/S ");
		}
	}

}
