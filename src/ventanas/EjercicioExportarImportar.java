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

				ventas = miConexion.dameVentas(nif);

				for (Venta i : ventas) {
					System.out.println(i);
				}

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nif = seleccionaNif(cboxTiendas);

				pedidos = miConexion.damePedidos(nif);

				for (Pedido i : pedidos) {
					System.out.println(i);
				}

			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					String nif = seleccionaNif(cboxTiendas);

					ventas = miConexion.dameVentas(nif);

					for (Venta i : ventas) {
						System.out.println(i);
					}
				} else {

					String nif = seleccionaNif(cboxTiendas);

					pedidos = miConexion.damePedidos(nif);

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
							Fabricante fabricante1 = new Fabricante();
							Articulo articulo1 = new Articulo();
							
							venta1.setNif(ventas.get(i).getNif());
							venta1.setNombreArticulo(ventas.get(i).getNombreArticulo());
							fabricante1.setNombre(ventas.get(i).getFabricante().getNombre());
							venta1.setFabricante(fabricante1);
							venta1.setPeso(ventas.get(i).getPeso());
							venta1.setCategoria(ventas.get(i).getCategoria());
							venta1.setFechaVenta(ventas.get(i).getFechaVenta());
							venta1.setUnidadesVendidas(ventas.get(i).getUnidadesVendidas());
							articulo1.setPrecioVenta(ventas.get(i).getArticulo().getPrecioVenta());
							venta1.setArticulo(articulo1);
							
							
							/*dataOS.writeObject(ventas.get(i).getNif());
							dataOS.writeObject(ventas.get(i).getNombreArticulo()); 
							dataOS.writeObject(ventas.get(i).getFabricante().getNombre()); 
							dataOS.writeObject(ventas.get(i).getPeso()); 
							dataOS.writeObject(ventas.get(i).getCategoria()); 
							dataOS.writeObject(ventas.get(i).getFechaVenta()); 
							dataOS.writeObject(ventas.get(i).getUnidadesVendidas()); 
							dataOS.writeObject(ventas.get(i).getArticulo().getPrecioVenta()); */
							
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
														
							dataOS.writeObject(pedidos.get(i)); // inserta nombre
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

							ventas = dataIS.readObject();

							System.out.println(ventas);
						}
						// Funcion pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						// TODO: handle exception
					}

				} else {

					try {

						File fichero = new File("FicheroDatosPedidos.dat");

						FileInputStream filein = new FileInputStream(fichero);

						DataInputStream dataIS = new DataInputStream(filein);

						String pedidos;

						while (true) {

							pedidos = dataIS.readUTF();

							System.out.println(pedidos);
						}
						// Funcion pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						// TODO: handle exception
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
