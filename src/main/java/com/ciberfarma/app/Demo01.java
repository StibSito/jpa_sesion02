package com.ciberfarma.app;

import java.util.List;

import com.ciberfarma.model.Tipo;
import com.ciberfarma.model.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Demo01 {
	// listado de los tipos de usuario y sus usuarios de tipos
	public static void main(String[] args) {

		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();
		// proceso obtener un objeto usuario

		String sql = " Select * from tb_tipos";
		String jpql = "select t from Tipo t";

		List<Tipo> lstTipos = manager.createNativeQuery(sql, Tipo.class).getResultList();

		List<Tipo> lstTipos2 = manager.createQuery(jpql, Tipo.class).getResultList();

		System.out.println("Listado de usuarios");
		System.out.println("-------------------");
		for (Tipo t : lstTipos) {
			System.out.println("tipo : " + t.getDescripcion());
			System.out.println("-------------------");
			for (Usuario u : t.getLstUsuario()) {

				System.out.println("nombre " + u.getNom_usua());

			}
		}

		manager.close();
	}
}
