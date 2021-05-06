import { Component } from '@angular/core';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';
import { Oposicion,Departamento,RelDepEpi,RelDepEpiPK, Mensaje, Tipo,} from '../shared/app.model';

@Component({
  selector: 'app-oposiciones',
  templateUrl: './oposiciones.component.html',
  styleUrls: ['./oposiciones.component.scss'],
})
export class OposicionesComponent {
  oposiciones: Oposicion[];
  oposicion: Oposicion;
  count: number;
  pagina:number;
  constructor(private clienteApiRestService:ClienteApiRestService, private datos: DataService) {
    this.oposiciones = [];
    this.pagina = 1;
    this.getOposicion("BOE-A-2020-7");
    this.getOposiciones();
    this.getCount(10);

  }
  getCount(num: number){
    this.clienteApiRestService.getSizeOposicion().subscribe(
      resp=>{
        if (resp.status < 400) {
          this.count =Math.ceil(resp.body/ num);
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
  getOposicion(num: String){
    this.clienteApiRestService.getOposicion(num).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.oposicion = resp.body ;
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

  cargarMasOposiciones(event){
    console.log('Cargando siguientes..')
    setTimeout(()=>{
      this.getOposiciones();
      event.target.complete();
    },1000);
  }

  getOposiciones(){
    
    this.clienteApiRestService.getOposiciones(this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.oposiciones.push( ...resp.body);
          
          // ||[];
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
