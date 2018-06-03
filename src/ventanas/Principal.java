package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Principal extends JFrame {

	private Conexion miConexion = new Conexion();

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		
		miConexion.conectar();

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnEjercicio1 = new JButton("Ejercicio 1");

		btnEjercicio1.setBounds(48, 92, 124, 23);
		contentPane.add(btnEjercicio1);

		JButton btnEjercicio2 = new JButton("Ejercicio 2");
		btnEjercicio2.setBounds(48, 141, 124, 23);
		contentPane.add(btnEjercicio2);

		JButton btnEjercicio3 = new JButton("Ejercicio 3");
		btnEjercicio3.setBounds(48, 194, 124, 23);
		contentPane.add(btnEjercicio3);

		JLabel lbTituloPractica = new JLabel("Pr\u00E1ctica 1");
		lbTituloPractica.setFont(new Font("Arial", Font.BOLD, 16));
		lbTituloPractica.setBounds(70, 27, 83, 34);
		contentPane.add(lbTituloPractica);

		btnEjercicio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				EjercicioComboTabla ejercicio1 = new EjercicioComboTabla(miConexion);

				ejercicio1.setVisible(true);
			}
		});

		btnEjercicio3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EjercicioListaComboInsert ejercicio3 = new EjercicioListaComboInsert(miConexion);

				ejercicio3.setVisible(true);

			}
		});

		btnEjercicio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				EjercicioExportarImportar ejercicio2 = new EjercicioExportarImportar(miConexion);

				ejercicio2.setVisible(true);

			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				miConexion.cerrarConexion();
				JOptionPane.showMessageDialog(null, "La conexión se cerro correctamente");
			}
		});
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

}
