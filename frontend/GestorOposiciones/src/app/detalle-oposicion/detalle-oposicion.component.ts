import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Oposicion , Mensaje} from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';
@Component({
  selector: 'app-detalle-oposicion',
  templateUrl: './detalle-oposicion.component.html',
  styleUrls: ['./detalle-oposicion.component.scss'],
})
export class DetalleOposicionComponent implements OnInit {
  id:String;
  oposicion:Oposicion;
  constructor( private ruta: ActivatedRoute,private clienteApiRestService:ClienteApiRestService, private datos: DataService) { 
    this.oposicion = new Oposicion();
    
  }
  
  ngOnInit() {
    this.ruta.paramMap.subscribe(
      params => {
        this.id  = params.get('idOp');
        this.getOposicion(this.id);
        
      },
      err => console.log("Error al leer id para editar: " + err)
    )
    
    /*
    */
  }
  getOposicion(num: String){
    this.clienteApiRestService.getOposicion(num).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.oposicion = resp.body ;
          window.location.href = this.oposicion.urlpdf+"";
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
    
  }

}
