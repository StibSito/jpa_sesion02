package com.ciberfarma.app;

import java.util.List;

import com.ciberfarma.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Demo02 {
	public static void main(String[] args) {
		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();
		// proceso obtener un objeto usuario

		String jpql = "select p from Producto p";
		// String jpq2 = "select u.nom_usuar,u.ape_usua form Usuario u";

		List<Producto> lstProductos = manager.createQuery(jpql, Producto.class).getResultList();
		System.out.println("Listado de Productos");
		System.out.println("-------------------");
		for (Producto p : lstProductos) {
			System.out.println("Codigo : " + p.getId_prod());
			System.out.println("Nombre : " + p.getDes_prod());
			System.out.println("Categoria : " + p.getObtCategoria().getDescripcion());
			System.out.println("Proveedor : " + p.getObtProveedor().getNombre_rs());
			System.out.println("-------------------");
		}

		manager.close();
	}
}
