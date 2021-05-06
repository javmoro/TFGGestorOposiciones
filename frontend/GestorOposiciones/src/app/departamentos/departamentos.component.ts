import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Departamento , Mensaje} from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';

@Component({
  selector: 'app-departamentos',
  templateUrl: './departamentos.component.html',
  styleUrls: ['./departamentos.component.scss'],
})
export class DepartamentosComponent {
  departamentos: Departamento[];
  pagina:number;
  constructor(private clienteApiRestService:ClienteApiRestService, private datos: DataService) {
    this.departamentos = [];
    this.pagina = 1;
    this.getEpigrafes();
  }
  
  
  cargarMasOposiciones(event){
    console.log('Cargando siguientes..')
    setTimeout(()=>{
      this.getEpigrafes();
      event.target.complete();
    },1000);
  }

  getEpigrafes(){
    
    this.clienteApiRestService.getDepartamentos(this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.departamentos.push( ...resp.body);
          
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
