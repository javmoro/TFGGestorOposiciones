import { Component, Input, OnInit } from '@angular/core';
import {ModalController} from '@ionic/angular';
import {ReferenciaAnterior} from 'src/app/shared/app.model';

import{ClienteApiRestService} from 'src/app/shared/cliente-api-rest.service';
@Component({
  selector: 'app-detalle-oposicion',
  templateUrl: './detalle-oposicion.component.html',
  styleUrls: ['./detalle-oposicion.component.scss'],
})
export class DetalleOposicionComponent implements OnInit {

  @Input() id:string;
  @Input() titulo:string;
  @Input() nombreEp:string;
  @Input() nombreDep:string;
  @Input() urlPdf:string;
  referenciasAnteriores:ReferenciaAnterior[];
  referenciasPosteriores:ReferenciaAnterior[];

  constructor(private modalCtrl: ModalController,private clienteApiRestService: ClienteApiRestService, ) {
    this.referenciasAnteriores = [];
    this.referenciasPosteriores = [];
   }
  
  ngOnInit() {
    this.getReferenciasAnteriores();
    this.getReferenciasPosteriores();
  }
  _dismiss(){
    console.log("dismiss");
    this.modalCtrl.dismiss();
  }
  getReferenciasAnteriores(){
    this.clienteApiRestService.getReferenciasAnteriores(this.id).subscribe(
      resp => {
        if (resp.status < 400) {

          this.referenciasPosteriores.push(...resp.body);
        }

      },
      err => {
        
      }
    )
  }
  getReferenciasPosteriores(){
    this.clienteApiRestService.getReferenciasPosteriores(this.id).subscribe(
      resp => {
        if (resp.status < 400) {

          this.referenciasAnteriores.push(...resp.body);
        }

      },
      err => {
        
      }
    )
  }
}
