package aplicaciones.spring.servicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aplicaciones.spring.modelo.Producto;
import aplicaciones.spring.repositorios.IProducto;
@Service("producto")
public class ProductoService {
	
	@Autowired
	IProducto iProducto;
	
	public void guardar(Producto producto) {		
		iProducto.save(producto);
	}
	
	public List<Producto> listar(){		
		return iProducto.findAll();
	}
	
	public Producto buscar(Long codigo) {
		return iProducto.findByCodigo(codigo);
	}
	
	public boolean eliminar(Long codigo) {
		try {
			iProducto.delete(iProducto.findByCodigo(codigo));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Producto buscar(String nombre) {
		return iProducto.findByNombre(nombre);
	}
}
