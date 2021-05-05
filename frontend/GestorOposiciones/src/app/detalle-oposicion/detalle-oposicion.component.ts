import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-detalle-oposicion',
  templateUrl: './detalle-oposicion.component.html',
  styleUrls: ['./detalle-oposicion.component.scss'],
})
export class DetalleOposicionComponent implements OnInit {
  aaa:string;
  constructor( private ruta: ActivatedRoute) { }

  ngOnInit() {
    this.ruta.paramMap.subscribe(
      params => {
        let id = params.get('id');
        this.aaa=id;

      },
      err => console.log("Error al leer id para editar: " + err)
    )
  }

}
