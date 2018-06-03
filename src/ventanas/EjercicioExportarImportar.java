package ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import bbdd.Conexion;
import clases.Articulo;
import clases.Fabricante;
import clases.Pedido;
import clases.Tienda;
import clases.Venta;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class EjercicioExportarImportar extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private ArrayList<Venta> ventas;
	private ArrayList<Pedido> pedidos;

	private Conexion miConexion;

	// private Document document;
	// private Element raiz;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioExportarImportar frame = new EjercicioExportarImportar(null);
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
	public EjercicioExportarImportar(Conexion conex) {

		miConexion = conex;

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

							/*
							 * pedido1.setNif(pedidos.get(i).getNif());
							 * pedido1.setNombreArticulo(pedidos.get(i).getNombreArticulo());
							 * 
							 * fabricante1.setNombre(pedidos.get(i).getFabricante().getNombre());
							 * pedido1.setFabricante(fabricante1);
							 * 
							 * pedido1.setPeso(pedidos.get(i).getPeso());
							 * pedido1.setCategoria(pedidos.get(i).getCategoria());
							 * pedido1.setFechaPedido(pedidos.get(i).getFechaPedido());
							 * pedido1.setUnidadesPedidas(pedidos.get(i).getUnidadesPedidas());
							 * 
							 * articulo1.setPrecioVenta(pedidos.get(i).getArticulo().getPrecioVenta());
							 * pedido1.setArticulo(articulo1);
							 */

							pedido1.setNif(pedidos.get(i).getNif());
							pedido1.setNombreArticulo(pedidos.get(i).getNombreArticulo());
							pedido1.setCodFabricante(pedidos.get(i).getCodFabricante());
							pedido1.setPeso(pedidos.get(i).getPeso());
							pedido1.setCategoria(pedidos.get(i).getCategoria());
							pedido1.setFechaPedido(pedidos.get(i).getFechaPedido());
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

							miConexion.insertaVenta(venta1.getNif(), venta1.getNombreArticulo(),
									venta1.getCodFabricante(), venta1.getPeso(), venta1.getCategoria(),
									venta1.getFechaVenta(), venta1.getUnidadesVendidas());

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

							miConexion.insertaPedido(pedido1.getNif(), pedido1.getNombreArticulo(),
									pedido1.getCodFabricante(), pedido1.getPeso(), pedido1.getCategoria(),
									pedido1.getFechaPedido(), pedido1.getUnidadesPedidas());

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

				if (rbtnVentas.isSelected() == true) {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

					DocumentBuilder builder;
					try {
						builder = factory.newDocumentBuilder();

						DOMImplementation implementation = builder.getDOMImplementation();
						Document document = implementation.createDocument(null, "Ventas", null);
						document.setXmlVersion("1.0");

						for (int i = 0; i < ventas.size(); i++) {

							Element raiz = document.createElement("venta"); // nodo venta
							document.getDocumentElement().appendChild(raiz);

							// añadir Nif
							crearElemento("nif", ventas.get(i).getNif(), raiz, document);
							// añadir nombreArticulo
							crearElemento("nombreArticulo", ventas.get(i).getNombreArticulo(), raiz, document);
							// añadir codFabricante
							crearElemento("codFabricante", ventas.get(i).getCodFabricante() + "", raiz, document);
							// añadir peso
							crearElemento("peso", ventas.get(i).getPeso() + "", raiz, document);
							// añadir categoria
							crearElemento("categoria", ventas.get(i).getCategoria() + "", raiz, document);
							// añadir fechaVenta
							crearElemento("fechaVenta", ventas.get(i).getFechaVenta(), raiz, document);
							// añadir unidadesVendidas
							crearElemento("unidadesVendidas", ventas.get(i).getUnidadesVendidas() + "", raiz, document);
						}

						Source source = new DOMSource(document);
						Result result = new StreamResult(new java.io.File("Ventas.xml"));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(source, result);

						JOptionPane.showMessageDialog(null, "Exportado Fichero Ventas.xml");

					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerFactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

					DocumentBuilder builder;
					try {
						builder = factory.newDocumentBuilder();

						DOMImplementation implementation = builder.getDOMImplementation();
						Document document = implementation.createDocument(null, "Pedidos", null);
						document.setXmlVersion("1.0");

						for (int i = 0; i < pedidos.size(); i++) {

							Element raiz = document.createElement("pedido"); // nodo venta
							document.getDocumentElement().appendChild(raiz);

							// añadir Nif
							crearElemento("nif", pedidos.get(i).getNif(), raiz, document);
							// añadir nombreArticulo
							crearElemento("nombreArticulo", pedidos.get(i).getNombreArticulo(), raiz, document);
							// añadir codFabricante
							crearElemento("codFabricante", pedidos.get(i).getCodFabricante() + "", raiz, document);
							// añadir peso
							crearElemento("peso", pedidos.get(i).getPeso() + "", raiz, document);
							// añadir categoria
							crearElemento("categoria", pedidos.get(i).getCategoria() + "", raiz, document);
							// añadir fechaPedido
							crearElemento("fechaPedido", pedidos.get(i).getFechaPedido(), raiz, document);
							// añadir unidadesPedidas
							crearElemento("unidadesPedidas", pedidos.get(i).getUnidadesPedidas() + "", raiz, document);
						}

						Source source = new DOMSource(document);
						Result result = new StreamResult(new java.io.File("Pedidos.xml"));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(source, result);

						JOptionPane.showMessageDialog(null, "Exportado Fichero Pedidos.xml");

					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerFactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

		});

		btnImportarFicheroXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (rbtnVentas.isSelected() == true) {

					try {

						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

						DocumentBuilder builder = factory.newDocumentBuilder();
						Document documento = builder.parse(new File("Ventas.xml"));
						documento.getDocumentElement().normalize();

						NodeList config = documento.getElementsByTagName("venta");

						for (int i = 0; i < ventas.size(); i++) {

							Node venta = config.item(i);

							if (venta.getNodeType() == Node.ELEMENT_NODE) {
								Element elemento = (Element) venta;

								String nif = elemento.getElementsByTagName("nif").item(0).getTextContent();
								String nombreArticulo = elemento.getElementsByTagName("nombreArticulo").item(0)
										.getTextContent();
								int codFabricante = Integer.parseInt(
										elemento.getElementsByTagName("codFabricante").item(0).getTextContent());
								int peso = Integer
										.parseInt(elemento.getElementsByTagName("peso").item(0).getTextContent());
								String categoria = elemento.getElementsByTagName("categoria").item(0).getTextContent();
								String fechaVenta = elemento.getElementsByTagName("fechaVenta").item(0)
										.getTextContent();
								int unidadesVendidas = Integer.parseInt(
										elemento.getElementsByTagName("unidadesVendidas").item(0).getTextContent());

								/* Comprobamos si esta recogiedo los datos del XML */

								System.out.println("Nif: " + nif);
								System.out.println("Nombre Articulo: " + nombreArticulo);
								System.out.println("Cod Fabricante: " + codFabricante);
								System.out.println("Peso: " + peso);
								System.out.println("Categoria: " + categoria);
								System.out.println("Fecha Venta: " + fechaVenta);
								System.out.println("Unidades Vendidas: " + unidadesVendidas);

								miConexion.insertaVenta(nif, nombreArticulo, codFabricante, peso, categoria, fechaVenta,
										unidadesVendidas);
							}
						}

					} catch (Exception e) {

					}

				} else {
					try {

						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

						DocumentBuilder builder = factory.newDocumentBuilder();
						Document documento = builder.parse(new File("Pedidos.xml"));
						documento.getDocumentElement().normalize();

						NodeList config = documento.getElementsByTagName("pedido");

						for (int i = 0; i < pedidos.size(); i++) {

							Node pedido = config.item(i);

							if (pedido.getNodeType() == Node.ELEMENT_NODE) {
								Element elemento = (Element) pedido;

								String nif = elemento.getElementsByTagName("nif").item(0).getTextContent();
								String nombreArticulo = elemento.getElementsByTagName("nombreArticulo").item(0)
										.getTextContent();
								int codFabricante = Integer.parseInt(
										elemento.getElementsByTagName("codFabricante").item(0).getTextContent());
								int peso = Integer
										.parseInt(elemento.getElementsByTagName("peso").item(0).getTextContent());
								String categoria = elemento.getElementsByTagName("categoria").item(0).getTextContent();
								String fechaPedido = elemento.getElementsByTagName("fechaPedido").item(0)
										.getTextContent();
								int unidadesPedidas = Integer.parseInt(
										elemento.getElementsByTagName("unidadesPedidas").item(0).getTextContent());

								/* Comprobamos si esta recogiedo los datos del XML */

								System.out.println("Nif: " + nif);
								System.out.println("Nombre Articulo: " + nombreArticulo);
								System.out.println("Cod Fabricante: " + codFabricante);
								System.out.println("Peso: " + peso);
								System.out.println("Categoria: " + categoria);
								System.out.println("Fecha Pedido: " + fechaPedido);
								System.out.println("Unidades Pedidas: " + unidadesPedidas);

								miConexion.insertaPedido(nif, nombreArticulo, codFabricante, peso, categoria,
										fechaPedido, unidadesPedidas);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

		});
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

	public void crearElemento(String dato, String valor, Element raiz, Document document) {

		Element elem = document.createElement(dato);

		Text text = document.createTextNode(valor); // damos valor

		raiz.appendChild(elem); // pegamos el elemento hijo a la raiz

		elem.appendChild(text); // pegamos el valor
	}
}
