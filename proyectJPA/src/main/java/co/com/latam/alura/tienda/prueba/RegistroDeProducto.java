package co.com.latam.alura.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.com.latam.alura.tienda.dao.ProductoDAO;
import co.com.latam.alura.tienda.modelo.Categoria;
import co.com.latam.alura.tienda.modelo.Producto;
import co.com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		Producto celular = new Producto("Samsung","telefono usado", new BigDecimal("1000"), Categoria.CELULARES);
		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDAO productoDAO = new ProductoDAO(em);
		
		em.getTransaction().begin();
		
		productoDAO.guardar(celular);
		
		em.getTransaction().commit();
		em.close();
		
		
	}

}
