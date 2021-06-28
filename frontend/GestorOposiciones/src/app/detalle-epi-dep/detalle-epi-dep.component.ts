import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Departamento,Mensaje, RelDepEpi } from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';


@Component({
  selector: 'app-detalle-epi-dep',
  templateUrl: './detalle-epi-dep.component.html',
  styleUrls: ['./detalle-epi-dep.component.scss'],
})
export class DetalleEpiDepComponent implements OnInit {
  opcion: boolean;
  idEp: String;
  nombreDep:String;
  idDep: String;
  identificador: String[];
  detalles: RelDepEpi[];
  pagina: number;
  constructor(private ruta: ActivatedRoute, private clienteApiRestService: ClienteApiRestService, private datos: DataService) {
    this.detalles = []; this.opcion = true; this.pagina = 0;
    this.nombreDep ="";
  }

  ngOnInit() {
    
    this.ruta.paramMap.subscribe(
      params => {
        this.idEp = params.get('idEpi');
        this.idDep = params.get('idDep');
      },
      err => console.log("Error al leer id para editar: " + err)
    )
    if (this.idEp == null) {
      console.log('Cargando siguientes epigrafes..')
      this.opcion = true;
      this.getEpigrafes();
    this.getDepartamento();

    } else {
      console.log('Cargando siguientes departamentos..')
      this.opcion = false;
      this.getDepartamentos();
    }
  }
  cargarMasDetalles(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      if (this.opcion){
        this.getEpigrafes();
      }
      else
        this.getDepartamentos();
      event.target.complete();
    }, 1000);
  }

  getDepartamento() {

    this.clienteApiRestService.getDepartamento(this.idDep).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;
          var dep= resp.body;
          this.nombreDep = dep.nombre;
        }
        else {
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        }
      },
      err => {
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }

  getDepartamentos() {

    this.clienteApiRestService.getDepartamentosFromEp(this.idEp, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;
          this.detalles.push(...resp.body);
        }
        else {
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        }
      },
      err => {
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }

  getEpigrafes() {

    this.clienteApiRestService.getEpigrafesFromDep(this.idDep, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;
          this.detalles.push(...resp.body);
        }
        else {
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        }
      },
      err => {
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }
}
