package co.com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
/*import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;*/

import co.com.latam.alura.tienda.dao.CategoriaDAO;
import co.com.latam.alura.tienda.dao.ProductoDAO;
import co.com.latam.alura.tienda.modelo.Categoria;
import co.com.latam.alura.tienda.modelo.Producto;
import co.com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDAO = new ProductoDAO(em);
		Producto producto = productoDAO.consultaPorId(1l);
		System.out.println(producto.getNombre());
		
		List<Producto> productos = productoDAO.consultarTodos();
		productos.forEach(prod->System.out.println(prod.getDescripcion()));
		
		
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		
		Producto celular = new Producto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDAO = new ProductoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		
		em.getTransaction().begin();
	
		categoriaDAO.guardar(celulares);
		productoDAO.guardar(celular);
		
		em.getTransaction().commit();
		em.close();
	}

}
