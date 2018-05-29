package ventanas;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;
import clases.Articulo;
import clases.Tienda;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class EjercicioListaComboInsert extends JFrame {

	private JPanel contentPane;
	private JTextField txtFecha;
	private JTextField txtCategoria;

	private JList<Tienda> listTiendas = new JList();
	private JSpinner spinPeso = new JSpinner();
	private JSpinner spinUnidades = new JSpinner();

	private Conexion miConexion = new Conexion();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private String nifTienda = null; 
	private String nombreArticulo;
	private int codFabricante;
	private int peso;
	private int unidades;
	private String fecha;
	private String categoria;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioListaComboInsert frame = new EjercicioListaComboInsert();
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
	public EjercicioListaComboInsert() {

		miConexion.conectar();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 34, 294, 308);
		contentPane.add(scrollPane);

		listTiendas = new JList();

		scrollPane.setViewportView(listTiendas);

		JComboBox cboxArticulos = new JComboBox();

		cboxArticulos.setBounds(379, 34, 267, 20);
		contentPane.add(cboxArticulos);

		spinPeso = new JSpinner();
		spinPeso.setBounds(539, 91, 107, 20);
		contentPane.add(spinPeso);

		spinUnidades = new JSpinner();
		spinUnidades.setBounds(539, 153, 107, 20);
		contentPane.add(spinUnidades);

		txtFecha = new JTextField();
		txtFecha.setBounds(539, 223, 107, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);

		txtCategoria = new JTextField();
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(539, 273, 107, 20);
		contentPane.add(txtCategoria);

		JLabel lbPeso = new JLabel("Peso");
		lbPeso.setBounds(389, 94, 46, 14);
		contentPane.add(lbPeso);

		JLabel lbunidades = new JLabel("Unidades");
		lbunidades.setBounds(389, 156, 64, 14);
		contentPane.add(lbunidades);

		JLabel lbFecha = new JLabel("Fecha");
		lbFecha.setBounds(389, 226, 46, 14);
		contentPane.add(lbFecha);

		JLabel lbCategoria = new JLabel("Categoria");
		lbCategoria.setBounds(389, 276, 64, 14);
		contentPane.add(lbCategoria);

		JButton btnInsertarArticulo = new JButton("Insertar");
		btnInsertarArticulo.setBounds(557, 327, 89, 23);
		contentPane.add(btnInsertarArticulo);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(389, 64, 109, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(500, 64, 109, 23);
		contentPane.add(rbtnPedidos);

		rellenaListaTiendas(listTiendas);

		rellenaComboBoxArticulos(cboxArticulos);

		listTiendas.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				Tienda tienda1 = new Tienda();

				if (e.getValueIsAdjusting()) {

					tienda1 = listTiendas.getSelectedValue();

					nifTienda = tienda1.getNif();

					System.out.println(nifTienda);

				}

			}

		});

		cboxArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Articulo articulo1 = new Articulo();

				articulo1 = (Articulo) cboxArticulos.getSelectedItem();

				nombreArticulo = articulo1.getNombreArticulo();

				codFabricante = articulo1.getCodFabricante();

			}
		});

		btnInsertarArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

				peso = (int)spinPeso.getValue();

				unidades = (int)spinUnidades.getValue();

				fecha = txtFecha.getText();

				categoria = txtCategoria.getText();

				if (rbtnVentas.isSelected() == true) {

					miConexion.insertaVenta(nifTienda, nombreArticulo, codFabricante, peso, categoria, fecha, unidades);
					
				} else {
					
					miConexion.insertaPedido(nifTienda, nombreArticulo, codFabricante, peso, categoria, fecha, unidades);
				}
				
				System.out.println(nifTienda + nombreArticulo + codFabricante + peso + unidades + fecha + categoria);

			}

		});

	}

	// ------------------------------------------METODOS-----------------------------------------//

	private void rellenaComboBoxArticulos(JComboBox cboxArticulos) {
		ArrayList<Articulo> articulos = new ArrayList<Articulo>();

		articulos = miConexion.dameArticulos();

		for (int i = 0; i < articulos.size(); i++) {
			cboxArticulos.addItem(articulos.get(i));
		}

	}

	private void rellenaListaTiendas(JList listTiendas) {

		DefaultListModel<Tienda> modeloLista = new DefaultListModel<Tienda>();

		ArrayList<Tienda> datos = new ArrayList<Tienda>();

		datos = miConexion.dameTiendas();

		for (int i = 0; i < datos.size(); i++) {
			modeloLista.addElement(datos.get(i));
		}

		listTiendas.setModel(modeloLista);
	}

}
