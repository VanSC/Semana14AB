package aplicaciones.spring.servicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aplicaciones.spring.modelo.Cliente;
import aplicaciones.spring.repositorios.ICliente;
@Service("cliente")
public class ClienteService {
	@Autowired
	private ICliente iCliente;
	public void guardar(Cliente cliente) {
		iCliente.save(cliente);
	}	
	public List<Cliente> listar (){
		 return iCliente.findAll();
	}	
	public  Cliente buscar(Long codigo) {
		return  iCliente.findByCodigo(codigo);
	}	
	public boolean eliminar (Long codigo) {
		try {
			iCliente.delete(iCliente.findByCodigo(codigo));
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
}
