package bbdd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import clases.Articulo;
import clases.Fabricante;
import clases.Pedido;
import clases.Tienda;
import clases.Venta;

public class Conexion {

	private Connection conexion;
	private String usuario;
	private String pass;
	private String baseDatos;
	private String servidor;
	private String forName;

	private Statement sentencia;

	public Conexion() {

	}

	public Conexion(Connection conexion, String usuario, String pass, String baseDatos, String servidor,
			String forName) {
		this.conexion = conexion;
		this.usuario = usuario;
		this.pass = pass;
		this.baseDatos = baseDatos;
		this.servidor = servidor;
		this.forName = forName;
	}

	public void conectar() {

		this.forName = "com.mysql.jdbc.Driver";
		this.servidor = "jdbc:mysql://localhost/";
		this.baseDatos = "tiendas";
		this.usuario = "tiendas";
		this.pass = "tiendas";

		try {

			// leerXML();

			Class.forName(getForName());

			this.conexion = DriverManager.getConnection(getServidor() + getBaseDatos(), getUsuario(), getPass());

			this.sentencia = conexion.createStatement();

			System.out.println("Conectado");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block

			System.out.println("Error... No Conectado");

			e.printStackTrace();
		}

	}

	public void leerXML() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(new File("configtiendasXML.xml"));
			documento.getDocumentElement().normalize();

			NodeList config = documento.getElementsByTagName("config");

			Node bd = config.item(0);

			Element elemento = (Element) bd;

			this.forName = elemento.getElementsByTagName("forname").item(0).getTextContent();
			this.servidor = elemento.getElementsByTagName("servidor").item(0).getTextContent();
			this.baseDatos = elemento.getElementsByTagName("basedatos").item(0).getTextContent();
			this.usuario = elemento.getElementsByTagName("usuario").item(0).getTextContent();
			this.pass = elemento.getElementsByTagName("clave").item(0).getTextContent();

			/* Comprobamos si esta recogiedo los datos del XML */

			System.out.println("Conector Driver JDBC: " + forName);
			System.out.println("Servidor: " + servidor);
			System.out.println("Nombre de la Base de Datos: " + baseDatos);
			System.out.println("Usuario: " + usuario);
			System.out.println("Clave: " + pass);

		} catch (Exception e) {

		}

	}

	public void cerrarConexion() {

		try {
			conexion.close();

			System.out.println("La conexión se ha cerrado correctamente");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR al cerrar al conexión");
			e.printStackTrace();
		}
	}

	public ArrayList<Tienda> dameTiendas() {

		ArrayList<Tienda> tiendas = new ArrayList<Tienda>();

		String consulta = "SELECT * FROM TIENDAS";

		try {
			ResultSet resultado = sentencia.executeQuery(consulta);

			while (resultado.next()) {

				Tienda tienda1 = new Tienda();

				tienda1.setNombreTienda(resultado.getString(2));
				tienda1.setNif(resultado.getString(1));

				tiendas.add(tienda1);

			}
			resultado.close();
		} catch (Exception e) {

		}

		return tiendas;
	}

	public ArrayList<Articulo> dameArticulos() {

		ArrayList<Articulo> articulos = new ArrayList<Articulo>();

		String consulta = "Select distinct a.articulo, f.nombre from articulos a, fabricantes f "
				+ "where  a.cod_fabricante = f.cod_fabricante;;";

		try {
			ResultSet resultado = sentencia.executeQuery(consulta);

			while (resultado.next()) {

				Articulo articulo1 = new Articulo();
				

				articulos.add(articulo1);
			}

			resultado.close();
		} catch (Exception e) {

		}

		return articulos;
	}

	/**
	 *
	 */
	public ArrayList<Venta> dameVentas(String nif) {

		ArrayList<Venta> ventas = new ArrayList<Venta>();

		PreparedStatement enviaConsultaArticulosVentas;

		String consulta = "select v.nif, v.articulo, f.nombre, v.peso, v.categoria, v.fecha_venta, v.unidades_vendidas, a.precio_venta from ventas v, articulos a, fabricantes f where nif=? and v.articulo = a.articulo and v.categoria = a.categoria and v.cod_fabricante = f.cod_fabricante;";

		try {
			enviaConsultaArticulosVentas = conexion.prepareStatement(consulta);
			enviaConsultaArticulosVentas.setString(1, nif);

			ResultSet resultado = enviaConsultaArticulosVentas.executeQuery();

			while (resultado.next()) {

				Venta venta1 = new Venta();
				Fabricante fabricante1 = new Fabricante();
				Articulo articulo1 = new Articulo();

				venta1.setNif(resultado.getString(1));
				venta1.setNombreArticulo(resultado.getString(2));
				fabricante1.setNombre(resultado.getString(3));
				venta1.setFabricante(fabricante1);
				venta1.setPeso(resultado.getInt(4));
				venta1.setCategoria(resultado.getString(5));
				venta1.setFechaVenta(resultado.getString(6));
				venta1.setUnidadesVendidas(resultado.getInt(7));
				articulo1.setPrecioVenta(resultado.getDouble(8));
				venta1.setArticulo(articulo1);

				ventas.add(venta1);
			}
			resultado.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Bucle for each para ver las ventas
		/*
		 * for (String i : ventas) { System.out.println(i); }
		 */

		return ventas;
	}

	public ArrayList<Pedido> damePedidos(String nif) {
		
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		PreparedStatement enviaConsultaArticulosPedidos;

		String consulta = "select p.nif, p.articulo, f.nombre, p.peso, p.categoria, p.fecha_pedido, p.unidades_pedidas, a.precio_costo "
				+ "from pedidos p, articulos a, fabricantes f " + "where nif=? "
				+ "and p.articulo = a.articulo and p.categoria = a.categoria and p.cod_fabricante = f.cod_fabricante;";

		try {
			enviaConsultaArticulosPedidos = conexion.prepareStatement(consulta);
			enviaConsultaArticulosPedidos.setString(1, nif);

			ResultSet resultado = enviaConsultaArticulosPedidos.executeQuery();

			while (resultado.next()) {

				Pedido pedido1 = new Pedido();
				Fabricante fabricante1 = new Fabricante();
				Articulo articulo1 = new Articulo();

				pedido1.setNif(resultado.getString(1));
				pedido1.setNombreArticulo(resultado.getString(2));
				fabricante1.setNombre(resultado.getString(3));
				pedido1.setFabricante(fabricante1);
				pedido1.setPeso(resultado.getInt(4));
				pedido1.setCategoria(resultado.getString(5));
				pedido1.setFechaPedido(resultado.getString(6));
				pedido1.setUnidadesPedidas(resultado.getInt(7));
				articulo1.setPrecioCosto(resultado.getDouble(8));
				pedido1.setArticulo(articulo1);

				pedidos.add(pedido1);
			}
			resultado.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pedidos;
	}

	public String sumaPrecioVenta(String nif) {

		String totalVentas = null;

		PreparedStatement enviaConsultaSumaVentas;

		ResultSet resultado;

		String consultaPreparadaSumaVentas = "select sum(v.unidades_vendidas * a.precio_venta) as 'Total Uds. Vendidas' from ventas v, articulos a, fabricantes f "
				+ "where nif=? and v.articulo = a.articulo and v.categoria = a.categoria and v.cod_fabricante = f.cod_fabricante;";

		try {
			enviaConsultaSumaVentas = conexion.prepareStatement(consultaPreparadaSumaVentas);

			enviaConsultaSumaVentas.setString(1, nif);

			resultado = enviaConsultaSumaVentas.executeQuery();

			if (resultado.next()) {
				totalVentas = resultado.getString("Total Uds. Vendidas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totalVentas;
	}

	public String sumaPrecioCosto(String nif) {

		String totalPedidos = null;

		PreparedStatement enviaConsultaSumaPedidos;

		ResultSet resultado;

		String consultaPreparadaSumaPedidos = "select sum(p.unidades_pedidas * a.precio_costo) as 'Total Uds. Pedidas' "
				+ "from pedidos p, articulos a, fabricantes f " + "where nif=? "
				+ "and p.articulo = a.articulo and p.categoria = a.categoria and p.cod_fabricante = f.cod_fabricante;";

		try {

			enviaConsultaSumaPedidos = conexion.prepareStatement(consultaPreparadaSumaPedidos);
			enviaConsultaSumaPedidos.setString(1, nif);

			resultado = enviaConsultaSumaPedidos.executeQuery();

			if (resultado.next()) {

				totalPedidos = resultado.getString("Total Uds. Pedidas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(totalPedidos);
		return totalPedidos;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

}
