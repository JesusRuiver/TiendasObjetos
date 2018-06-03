package ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;
import clases.Pedido;
import clases.Tienda;
import clases.Venta;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EjercicioComboTabla extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tablaVentas;
	private JTable tablaPedidos;

	private JScrollPane scrollPaneTabla;

	private Conexion miConexion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioComboTabla frame = new EjercicioComboTabla(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EjercicioComboTabla(Conexion conex) {
		
		
		miConexion = conex;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 866, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cboxTiendas = new JComboBox();

		cboxTiendas.setBounds(27, 24, 287, 20);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(61, 68, 66, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");

		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(144, 68, 78, 23);
		contentPane.add(rbtnPedidos);

		scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(10, 98, 830, 314);
		contentPane.add(scrollPaneTabla);

		JLabel lbTotal = new JLabel("Total:");
		lbTotal.setFont(new Font("Arial", Font.BOLD, 13));
		lbTotal.setBounds(526, 434, 46, 14);
		contentPane.add(lbTotal);

		JLabel lbResultadoTotal = new JLabel("");
		lbResultadoTotal.setFont(new Font("Arial", Font.PLAIN, 14));
		lbResultadoTotal.setBounds(568, 429, 258, 25);
		contentPane.add(lbResultadoTotal);

		rellenaComboTiendas(miConexion, cboxTiendas);

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNif(cboxTiendas);

				String resultadoTotalVentas = miConexion.sumaPrecioVenta(nif);

				construirTablaVentas(nif);

				lbResultadoTotal.setText(resultadoTotalVentas + " € Ingresos Ventas");

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNif(cboxTiendas);

				String resultadoTotalPedidos = miConexion.sumaPrecioCosto(nif);

				construirTablaPedidos(nif);

				lbResultadoTotal.setText(resultadoTotalPedidos + "€ Coste Pedidos");

			}

		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (rbtnVentas.isSelected() == true) {

					String nif = seleccionaNif(cboxTiendas);

					String resultadoTotalVentas = miConexion.sumaPrecioVenta(nif);

					construirTablaVentas(nif);

					lbResultadoTotal.setText(resultadoTotalVentas + " € Ingresos Ventas");

				} else {

					String nif = seleccionaNif(cboxTiendas);

					String resultadoTotalPedidos = miConexion.sumaPrecioCosto(nif);

					construirTablaPedidos(nif);

					lbResultadoTotal.setText(resultadoTotalPedidos + "€ Coste Pedidos");

				}
			}
		});
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	public void rellenaComboTiendas(Conexion miConexion, JComboBox cboxTiendas) {

		ArrayList<Tienda> tiendas = new ArrayList<Tienda>();

		tiendas = miConexion.dameTiendas();

		for (int i = 0; i < tiendas.size(); i++) {
			cboxTiendas.addItem(tiendas.get(i));
		}
	}

	private String seleccionaNif(JComboBox cboxTiendas) {

		String nif;

		Tienda tienda1 = new Tienda();

		tienda1 = (Tienda) cboxTiendas.getSelectedItem();

		nif = tienda1.getNif();

		System.out.println(nif);

		return nif;
	}

	private void construirTablaVentas(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES VENDIDAS", "PRECIO VENTA" };
		String informacionTablaVentas[][] = obtenerDatosVentas(nif);

		tablaVentas = new JTable(informacionTablaVentas, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaVentas);

	}

	private String[][] obtenerDatosVentas(String nif) {

		ArrayList<Venta> ventas = new ArrayList<Venta>();

		ventas = miConexion.dameVentasParaTabla(nif);

		String matrizInfo[][] = new String[ventas.size()][8];

		for (int i = 0; i < ventas.size(); i++) {

			matrizInfo[i][0] = ventas.get(i).getNif();
			matrizInfo[i][1] = ventas.get(i).getNombreArticulo();
			matrizInfo[i][2] = ventas.get(i).getFabricante().getNombre();
			matrizInfo[i][3] = ventas.get(i).getPeso() + "";
			matrizInfo[i][4] = ventas.get(i).getCategoria();
			matrizInfo[i][5] = ventas.get(i).getFechaVenta();
			matrizInfo[i][6] = ventas.get(i).getUnidadesVendidas() + "";
			matrizInfo[i][7] = ventas.get(i).getArticulo().getPrecioVenta() + "";

		}

		return matrizInfo;
	}

	private void construirTablaPedidos(String nif) {
		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES PEDIDAS", "PRECIO COSTO" };
		String informacionTablaPedidos[][] = obtenerDatosPedidos(nif);

		tablaPedidos = new JTable(informacionTablaPedidos, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaPedidos);

	}

	private String[][] obtenerDatosPedidos(String nif) {

		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		pedidos = miConexion.damePedidosParaTabla(nif);

		String matrizInfo[][] = new String[pedidos.size()][8];

		for (int i = 0; i < pedidos.size(); i++) {

			matrizInfo[i][0] = pedidos.get(i).getNif();
			matrizInfo[i][1] = pedidos.get(i).getNombreArticulo();
			matrizInfo[i][2] = pedidos.get(i).getFabricante().getNombre();
			matrizInfo[i][3] = pedidos.get(i).getPeso() + "";
			matrizInfo[i][4] = pedidos.get(i).getCategoria();
			matrizInfo[i][5] = pedidos.get(i).getFechaPedido();
			matrizInfo[i][6] = pedidos.get(i).getUnidadesPedidas() + "";
			matrizInfo[i][7] = pedidos.get(i).getArticulo().getPrecioCosto() + "";

		}

		return matrizInfo;

	}
}
