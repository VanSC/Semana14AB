package aplicaciones.spring.servicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aplicaciones.spring.modelo.Venta;
import aplicaciones.spring.repositorios.IVenta;
@Service("venta")
public class VentaService {
	@Autowired
	IVenta iVenta;	
	public void guardar(Venta venta) {		
		iVenta.save(venta);
	}
	
	public List<Venta> listar(){
		return iVenta.findAll();
	}
	
	public Venta buscar(Long codigo) {
		return iVenta.findByCodigo(codigo);
	}
	
	public boolean eliminar(Long codigo) {
		try {
			iVenta.delete(iVenta.findByCodigo(codigo));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
