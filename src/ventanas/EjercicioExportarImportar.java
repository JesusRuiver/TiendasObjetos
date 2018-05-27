package ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;
import clases.Articulo;
import clases.Fabricante;
import clases.Pedido;
import clases.Tienda;
import clases.Venta;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class EjercicioExportarImportar extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private ArrayList<Venta> ventas;
	private ArrayList<Pedido> pedidos;

	private Conexion miConexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioExportarImportar frame = new EjercicioExportarImportar();
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
	public EjercicioExportarImportar() {

		miConexion.conectar();

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(42, 28, 272, 23);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setBounds(109, 62, 109, 23);
		rbtnVentas.setSelected(true);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(109, 95, 109, 23);
		contentPane.add(rbtnPedidos);

		JButton btnExportarBinarioSecuencial = new JButton("Exportar Fichero Binario");
		btnExportarBinarioSecuencial.setBounds(133, 148, 181, 23);
		contentPane.add(btnExportarBinarioSecuencial);

		JButton btnExportarXML = new JButton("Exportar Fichero XML");
		btnExportarXML.setBounds(133, 193, 181, 23);
		contentPane.add(btnExportarXML);

		JButton btnImportarBinarioSecuencial = new JButton("Importar Fichero Binario");
		btnImportarBinarioSecuencial.setBounds(133, 240, 181, 23);
		contentPane.add(btnImportarBinarioSecuencial);

		JButton btnImportarFicheroXML = new JButton("Importar Fichero XML");
		btnImportarFicheroXML.setBounds(133, 292, 181, 23);
		contentPane.add(btnImportarFicheroXML);

		// Primero rellenamos el comboBox de Tiendas con NIF

		rellenaComboTiendas(miConexion, cboxTiendas);

		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNif(cboxTiendas);

				ventas = miConexion.dameVentasParaImportar(nif);

				for (Venta i : ventas) {
					System.out.println(i);
				}

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nif = seleccionaNif(cboxTiendas);

				pedidos = miConexion.damePedidosParaImportar(nif);

				for (Pedido i : pedidos) {
					System.out.println(i);
				}

			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					String nif = seleccionaNif(cboxTiendas);

					ventas = miConexion.dameVentasParaImportar(nif);

					for (Venta i : ventas) {
						System.out.println(i);
					}
				} else {

					String nif = seleccionaNif(cboxTiendas);

					pedidos = miConexion.damePedidosParaImportar(nif);

					for (Pedido i : pedidos) {
						System.out.println(i);
					}

				}

			}
		});

		btnExportarBinarioSecuencial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

						for (int i = 0; i < ventas.size(); i++) {

							Venta venta1 = new Venta();

							venta1.setNif(ventas.get(i).getNif());
							venta1.setNombreArticulo(ventas.get(i).getNombreArticulo());
							venta1.setCodFabricante(ventas.get(i).getCodFabricante());
							venta1.setPeso(ventas.get(i).getPeso());
							venta1.setCategoria(ventas.get(i).getCategoria());
							venta1.setFechaVenta(ventas.get(i).getFechaVenta());
							venta1.setUnidadesVendidas(ventas.get(i).getUnidadesVendidas());

							dataOS.writeObject(venta1);

						}
						dataOS.close(); // cerrar stream

						JOptionPane.showMessageDialog(null, "Exportado Fichero Ventas");

						System.out.println("Exportado Fichero Ventas");

					} catch (Exception ex) {
						// TODO: handle exception
					}

				} else {

					try {
						File fichero = new File("FicheroDatosPedidos.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

						for (int i = 0; i < pedidos.size(); i++) {

							Pedido pedido1 = new Pedido();

							Fabricante fabricante1 = new Fabricante();

							Articulo articulo1 = new Articulo();

							/*pedido1.setNif(pedidos.get(i).getNif());
							pedido1.setNombreArticulo(pedidos.get(i).getNombreArticulo());

							fabricante1.setNombre(pedidos.get(i).getFabricante().getNombre());
							pedido1.setFabricante(fabricante1);

							pedido1.setPeso(pedidos.get(i).getPeso());
							pedido1.setCategoria(pedidos.get(i).getCategoria());
							pedido1.setFechaPedido(pedidos.get(i).getFechaPedido());
							pedido1.setUnidadesPedidas(pedidos.get(i).getUnidadesPedidas());

							articulo1.setPrecioVenta(pedidos.get(i).getArticulo().getPrecioVenta());
							pedido1.setArticulo(articulo1);*/
							
							pedido1.setNif(pedidos.get(i).getNif());
							pedido1.setNombreArticulo(pedidos.get(i).getNombreArticulo());
							pedido1.setCodFabricante(pedidos.get(i).getCodFabricante());
							pedido1.setPeso(pedidos.get(i).getPeso());
							pedido1.setCategoria(pedidos.get(i).getCategoria());
							pedido1.setFechaPedido (pedidos.get(i).getFechaPedido());
							pedido1.setUnidadesPedidas(pedidos.get(i).getUnidadesPedidas());

							dataOS.writeObject(pedido1); // inserta nombre
						}

						dataOS.close(); // cerrar strem

						JOptionPane.showMessageDialog(null, "Exportado Fichero Pedidos");

						System.out.println("Exportado Fichero Pedidos");
					} catch (Exception ex) {
						// TODO: handle exception
					}

				}

			}
		});

		btnImportarBinarioSecuencial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {
					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileInputStream filein = new FileInputStream(fichero);

						ObjectInputStream dataIS = new ObjectInputStream(filein);

						while (true) {

							Venta venta1;

							venta1 = (Venta) dataIS.readObject();
							
							miConexion.insertaVenta(venta1.getNif(), venta1.getNombreArticulo(), venta1.getCodFabricante(),
									venta1.getPeso(), venta1.getCategoria(), venta1.getFechaVenta(),venta1.getUnidadesVendidas());

							System.out.println(venta1.getNif() + " " + venta1.getNombreArticulo());
						}
						// Funciona pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						System.out.println("Los registros ya existen en la tabla ventas");
					}

				} else {

					try {

						File fichero = new File("FicheroDatosPedidos.dat");

						FileInputStream filein = new FileInputStream(fichero);

						ObjectInputStream dataIS = new ObjectInputStream(filein);

						while (true) {

							Pedido pedido1;
							
							pedido1 = (Pedido) dataIS.readObject();
							
							miConexion.insertaPedido(pedido1.getNif(), pedido1.getNombreArticulo(), pedido1.getCodFabricante(),
									pedido1.getPeso(), pedido1.getCategoria(), pedido1.getFechaPedido(),pedido1.getUnidadesPedidas());

							System.out.println(pedido1.getNif() + " " + pedido1.getNombreArticulo());
						}
						// Funcion pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						System.out.println("Los registros ya existen en la tabla pedidos");
					}

				}
			}
		});

		btnExportarXML.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});
	}

	/*-------------------------------------METODOS-----------------------------------*/

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
}
