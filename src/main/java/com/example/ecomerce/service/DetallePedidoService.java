package com.example.ecomerce.service;

import com.example.ecomerce.dao.DetallePedidoDao;
import com.example.ecomerce.models.*;
import com.lowagie.text.Header;
import jakarta.annotation.Resource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DetallePedidoService {

    private final DetallePedidoDao detallePedidoDao;

    public DetallePedidoService(DetallePedidoDao detallePedidoDao) {
        this.detallePedidoDao = detallePedidoDao;
    }

    //Método para guardar un detalle de pedido en la base de datos
    public DetallePedido guardarDetallePedido(DetallePedido detallepedido){
        return detallePedidoDao.save(detallepedido);
    }
    //Método para buscar un detalle de pedido por su Id
    public Optional<DetallePedido> buscarDetallePedidoPorId(int id){
        return detallePedidoDao.findById(id);
    }

    //Método para obtener todos los detalles de pedidos
    public List<DetallePedido> obtenerTodosLosDetallesPedidos(){
        return detallePedidoDao.findAll();
    }

    //Método para eliminar un detalle de pedido por su Id
    public void eliminarDetallePedidoPorId(int id){
        detallePedidoDao.deleteById(id);
    }

    //Método para mostrar un detalle del pedido por Id con tablas cruzadas
    public DetallePedidoDTO obtenerDetallePedidoPorId(Integer id){
        return detallePedidoDao.obtenerIformacionDePedidoPorId(id);
    };

    //Método para mostrar todos los productos de un pedido en especifico por su Id del detalle del pedido
    public List<ProductosDelPedidoDTO> obtenerProductosDelPedidoPorId(Integer id){
        return detallePedidoDao.obtenerProductosDelPedidoPorId(id);
    };

    //Método para obtener el total del pedido mediante el Id del detalle del pedido
    public TotalDelPedidoDTO obtenerTotalDelDetalleDelPedido(Integer id){
        return detallePedidoDao.obtenerTotalDelPedido(id);
    };
    @NotNull
    public ResponseEntity<ByteArrayResource> generarFacturaByIde(Integer id){
        DetallePedidoDTO detallePedido = this.detallePedidoDao.obtenerIformacionDePedidoPorId(id);
        if(detallePedido != null){
            TotalDelPedidoDTO totalDelPedido = this.obtenerTotalDelDetalleDelPedido(id);

            try{
                final File file = ResourceUtils.getFile("classpath:reports/Factura.jasper");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("id", detallePedido.getId().toString());
                parameters.put("nombreCompleto", detallePedido.getNombreCompleto());
                parameters.put("telefono",detallePedido.getTelefono());
                parameters.put("correo",detallePedido.getCorreo());
                parameters.put("direccion",detallePedido.getDireccion());
                parameters.put("ciudad",detallePedido.getCiudad());
                parameters.put("formaPago",detallePedido.getFormaPago());
                parameters.put("productosList", new JRBeanCollectionDataSource((Collection<?>) this.detallePedidoDao.obtenerProductosDelPedidoPorId(id)));
                parameters.put("total",totalDelPedido.getTotal());


                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("InvocePDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append(detallePedido.getId())
                                .append("generateDate:").append(".pdf").toString()).build();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);

                return ResponseEntity.ok()
                        .contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers)
                        .body(new ByteArrayResource(reporte));


            }catch (Exception e){
                e.printStackTrace();

            }

        }else{
            System.out.println("PDF null");
        }

        return null;
    }
}
