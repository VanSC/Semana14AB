package aplicaciones.spring.controlador;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import aplicaciones.spring.modelo.Producto;
import aplicaciones.spring.modelo.Venta;
import aplicaciones.spring.servicios.ClienteService;
import aplicaciones.spring.servicios.ProductoService;
import aplicaciones.spring.servicios.VentaService;
@Controller
@RequestMapping("/ventas")
@SessionAttributes("venta")
public class VentaController {
	@Autowired
	@Qualifier("venta")
	VentaService ventaService;
	
	@Autowired
	@Qualifier("cliente")
	ClienteService clienteService;
	
	@Autowired
	@Qualifier("producto")
	ProductoService productoService;
	
	@RequestMapping("/listar")
	public String listar(Model model) {
		List<Venta> ventas = ventaService.listar();
		model.addAttribute("ventas",ventas);
		model.addAttribute("titulo","Lista de Ventas");
		return "ventaListar";
	}
	
	@RequestMapping("/form")
	public String formulario(Model model) {
		Venta venta= new Venta();
		model.addAttribute("venta", venta);
		model.addAttribute("productos", productoService.listar());
		model.addAttribute("clientes", clienteService.listar());
		model.addAttribute("btn", "Registrar Venta");
		return "ventaForm";
	}
	@RequestMapping(value="/insertar",method=RequestMethod.POST)
	public String guardar(@Validated Venta venta, Model model) {
		try {
			String id =venta.getProducto();
			Producto pro = productoService.buscar(id);

			if(venta.getCantidad() <= pro.getCantidad()) {
				int diferencia=pro.getCantidad()-venta.getCantidad();
				pro.setCantidad(diferencia);
				double subtotal = pro.getPrecio() * venta.getCantidad();
				double igv = subtotal * 0.18;
				double total = subtotal+igv;
				venta.setSubtotal(subtotal);
				venta.setIgv(igv);
				venta.setTotal(total);
				productoService.guardar(pro);
				ventaService.guardar(venta);
			}else {
				model.addAttribute("ERROR", "No hay stock para este producto, solo tenemos un stock de: "+pro.getCantidad());
				venta= new Venta();
				model.addAttribute("venta", venta);
				model.addAttribute("productos", productoService.listar());
				model.addAttribute("clientes", clienteService.listar());
				model.addAttribute("btn", "Registrar Venta");
				return "ventaForm";
			}
		} catch (Exception e) {
		}
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/form/{codigo}")
	public String actualizar (@PathVariable("codigo") Long codigo,Model model) {
		model.addAttribute("venta",ventaService.buscar(codigo));
		model.addAttribute("productos", productoService.listar());
		model.addAttribute("clientes", clienteService.listar());
		model.addAttribute("btn","Actualiza Registro");
		return "ventaForm";
	}

	@RequestMapping("/eliminar/{codigo}")
	public String eliminar(@PathVariable("codigo") Long codigo) {
		ventaService.eliminar(codigo);
		return "redirect:/ventas/listar";
	}
}
