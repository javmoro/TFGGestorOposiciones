import { Component, OnInit } from '@angular/core';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DetalleOposicionComponent } from 'src/app/componentes/detalle-oposicion/detalle-oposicion.component';
import { ModalController } from '@ionic/angular';

import { Oposicion } from '../shared/app.model';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {

  

  oposiciones: Oposicion[];
  
  constructor( private modalCtrl: ModalController,private clienteApiRestService: ClienteApiRestService) { 
    this.oposiciones = [];
  }

  ngOnInit(  ) {
    this.getOposicionesSemana();
  }

  async _abrirDetalle(id:string,titulo:string,nombreEp:string, nombreDep:string,urlPdf:string ){
    const modal = await this.modalCtrl.create({
      component: DetalleOposicionComponent,
      componentProps:{
        "id":id,
        "titulo":titulo,
        "nombreEp":nombreEp,
        "nombreDep":nombreDep,
        "urlPdf": urlPdf
      }
    })
    return await modal.present();

  }
  getOposicionesSemana(){
    var fechaActual = new Date();
    var fechaSemana = new Date();
    fechaSemana.setDate(fechaSemana.getDate()-7);
    
    var fecha2 = fechaActual.toISOString().substr(0,10);
    var fecha1 = fechaSemana.toISOString().substr(0,10);
    console.log(fecha1);
    this.clienteApiRestService.busquedaAvanzada("","","",fecha1,fecha2,"",0).subscribe(
      resp => {
        if (resp.status < 400) {

          this.oposiciones.push(...resp.body);
        }
        
      },
      err => {
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }
  cargarMasOposiciones(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      this.getOposicionesSemana();
      event.target.complete();
    }, 1000);
  }
}
