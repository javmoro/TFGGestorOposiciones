import { Component, Input, OnInit } from '@angular/core';
import {ModalController} from '@ionic/angular';
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
  

  constructor(private modalCtrl: ModalController) { }
  
  ngOnInit() {}
  _dismiss(){
    console.log("dismiss");
    this.modalCtrl.dismiss();
  }

}
