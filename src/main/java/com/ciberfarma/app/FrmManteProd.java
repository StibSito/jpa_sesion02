package com.ciberfarma.app;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import com.ciberfarma.model.Categoria;
import com.ciberfarma.model.Producto;
import com.ciberfarma.model.Proveedor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCodigo;
	private JComboBox<String> cboCategorias;
	private JComboBox<String> cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
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
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(324, 29, 89, 23);
		contentPane.add(btnRegistrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox<String>();
		cboCategorias.setBounds(122, 70, 86, 22);
		contentPane.add(cboCategorias);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		JLabel lblProveedores = new JLabel("Proveedor:");
		lblProveedores.setBounds(230, 106, 102, 14);
		contentPane.add(lblProveedores);

		cboProveedores = new JComboBox<String>();
		cboProveedores.setBounds(300, 104, 120, 22);
		contentPane.add(cboProveedores);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(324, 63, 89, 23);
		contentPane.add(btnBuscar);

		llenaCombo();
	}

	void llenaCombo() {
		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();

		String jpql = "select c from Categoria c";

		List<Categoria> lstCategorias = manager.createQuery(jpql, Categoria.class).getResultList();

		cboCategorias.addItem("--Seleccione--");
		for (Categoria c : lstCategorias) {
			cboCategorias.addItem(c.getDescripcion());
		}

		String jpql2 = "select p from Proveedor p";

		List<Proveedor> lstProveedores = manager.createQuery(jpql2, Proveedor.class).getResultList();

		cboProveedores.addItem("--Seleccione--");
		for (Proveedor p : lstProveedores) {
			cboProveedores.addItem(p.getNombre_rs());
		}

	}

	void registrar() {
		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();
		// objeto a grabar
		Producto u = new Producto();

		String code = leerCodigo();
		String desc = leerDec();
		int stock = leerStock();
		double precio = leerPrecio();
		int idcate = leerCategoria();
		int estado = 1;
		int idprove = leerProveedor();

		u.setId_prod(code);
		u.setDes_prod(desc);
		u.setStk_prod(stock);
		u.setPre_prod(precio);
		u.setIdcategoria(idcate);
		u.setEst_prod(estado);
		u.setIdproveedor(idprove); // valor default

		// proceso
		try {
			manager.getTransaction().begin();
			manager.persist(u);
			manager.getTransaction().commit();
			limpiar();
			mensaje("Registro Ok");
		} catch (Exception e) {
			mensaje("Error: " + e.getCause().getMessage());
			mensaje("No se pudo realizar el registro");
		}
		manager.close();

	}

	void listado() {
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");

		EntityManager manager = conexion.createEntityManager();

		String jpql = "select p from Producto p";

		List<Producto> lstProductos = manager.createQuery(jpql, Producto.class).getResultList();
		imprimir("Listado de Productos");
		for (Producto p : lstProductos) {
			imprimir("Codigo : " + p.getId_prod());
			imprimir("Nombre : " + p.getDes_prod());
			imprimir("Categoria : " + p.getObtCategoria().getDescripcion());
			imprimir("Proveedor : " + p.getObtProveedor().getNombre_rs());
			imprimir("-------------------");
		}

		manager.close();

	}

	void imprimir(String s) {
		txtSalida.append(s + "\n");
	}

	void buscar() {
		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();

		String cod = leerCodigo();

		Producto p = manager.find(Producto.class, cod);

		// proceso
		if (p != null) {
			mensaje("codigo " + cod + " encontrado");
			txtCodigo.setText(p.getId_prod());
			txtDescripcion.setText(p.getDes_prod());
			txtPrecio.setText(p.getPre_prod() + "");
			txtStock.setText(p.getStk_prod() + "");
			cboCategorias.setSelectedIndex(p.getIdcategoria());
			cboProveedores.setSelectedIndex(p.getIdproveedor());
		} else {
			mensaje("el producto " + cod + " no existe");
		}
		manager.close();

	}

	void mensaje(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	String leerCodigo() {
		return txtCodigo.getText();
	}

	String leerDec() {
		return txtDescripcion.getText();
	}

	double leerPrecio() {
		return Double.parseDouble(txtPrecio.getText());
	}

	int leerStock() {
		return Integer.parseInt(txtStock.getText());
	}

	int leerCategoria() {
		return cboCategorias.getSelectedIndex();
	}

	int leerProveedor() {
		return cboProveedores.getSelectedIndex();
	}

	void limpiar() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtStock.setText("");
		cboCategorias.setSelectedIndex(0);
		cboProveedores.setSelectedIndex(0);
	}
}
