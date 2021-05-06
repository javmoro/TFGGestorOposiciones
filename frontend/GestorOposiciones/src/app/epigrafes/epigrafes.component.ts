import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Epigrafe , Mensaje} from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';

@Component({
  selector: 'app-epigrafes',
  templateUrl: './epigrafes.component.html',
  styleUrls: ['./epigrafes.component.scss'],
})
export class EpigrafesComponent  {
  epigrafes: Epigrafe[];
  pagina:number;
  constructor(private clienteApiRestService:ClienteApiRestService, private datos: DataService) {
    this.epigrafes = [];
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
    
    this.clienteApiRestService.getEpigrafes(this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.epigrafes.push( ...resp.body);
          
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
