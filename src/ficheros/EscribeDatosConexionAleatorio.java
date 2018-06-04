package ficheros;

import java.io.File;
import java.io.RandomAccessFile;

public class EscribeDatosConexionAleatorio {

	public static void main(String[] args) {

		try {
			File fichero = new File("DatosConexionAleatorio.dat");

			// declara el fichero de acceso aleatorio
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");

			// arrays con los datos
			String forname[] = { "oracle.jdbc.driver.OracleDriver", "com.mysql.jdbc.Driver" };// forname

			String servidor[] = { "jdbc:oracle:thin:@localhost:1521:XE", "jdbc:mysql://localhost/" }; // servidor

			String bbdd[] = { "metro", "tiendas" };// bbdd

			String usuario[] = { "metro", "tiendas" };// usuario

			String pass[] = { "metro", "tiendas" };// pass

			StringBuffer buffer = null; // buffer para almacenar apellido
			int n = forname.length; // numero de elementos del array

			for (int i = 0; i < n; i++) { // recorro los arrays

				file.writeInt(i + 1); // uso i+1 para identificar

				buffer = new StringBuffer(forname[i]);

				buffer.setLength(40); // 100 caracteres para el forname

				file.writeChars(buffer.toString());// insertar forname

				buffer = new StringBuffer(servidor[i]);

				buffer.setLength(40); // 100 caracteres para el servidor

				file.writeChars(buffer.toString());// insertar servidor

				buffer = new StringBuffer(usuario[i]);

				buffer.setLength(40); // 100 caracteres para el usuario

				file.writeChars(buffer.toString());// insertar usuario

				buffer = new StringBuffer(pass[i]);

				buffer.setLength(40); // 100 caracteres para el pass

				file.writeChars(buffer.toString());// insertar pass

			}
			file.close(); // cerrar fichero
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
