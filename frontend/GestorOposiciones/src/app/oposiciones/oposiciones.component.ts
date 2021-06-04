import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';
import { Oposicion, Departamento, RelDepEpi, RelDepEpiPK, Mensaje, Tipo, } from '../shared/app.model';
import { ModalController } from '@ionic/angular';
import { DetalleOposicionComponent } from 'src/app/componentes/detalle-oposicion/detalle-oposicion.component';
import { GlobalConstants } from 'src/app/shared/global-constants';
@Component({
  selector: 'app-oposiciones',
  templateUrl: './oposiciones.component.html',
  styleUrls: ['./oposiciones.component.scss'],
})
export class OposicionesComponent implements OnInit {
  fecha1: String;
  fecha2: String;
  oposiciones: Oposicion[];
  oposicion: Oposicion;
  fechaActual: String;
  departamentos: Departamento[];
  href: String;
  idEp: String;
  idDep: String;
  fecha: String;
  count: number;
  pagina: number;
  search: String;
  constructor(private modalCtrl: ModalController,private router: Router, private ruta: ActivatedRoute, private clienteApiRestService: ClienteApiRestService, private datos: DataService) {
    this.oposiciones = [];
    this.fechaActual = "";
    this.departamentos = [];
    this.fecha1 = "";
    this.fecha2 = "";
    this.pagina = 1;
    this.href = this.router.url;
  }
  getDepartamentos() {

    this.clienteApiRestService.getDepartamentos(0).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.departamentos.push(...resp.body);

          // ||[];
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
  getTipoLlamada(search: String, fecha1: String, fecha2: String, idEp: String, idDep: String, fecha: String): number {
    if (idEp != null && idDep != null) {
      if (fecha != null) {
        return 1;
      } else
        return 2;
    } else {
      if (search != null || fecha1 != null || fecha2 != null) {
        return 3;
      } else
        return 4;
    }
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
  ngOnInit() {
    var dateActual = new Date();
    
    this.fechaActual = dateActual.toISOString().substr(0,10);

    this.ruta.paramMap.subscribe(
      params => {

        this.pagina = 1;
        this.idEp = params.get('idEpi');
        this.idDep = params.get('idDep');
        this.fecha = params.get('fecha');

      },
      err => console.log("Error al leer id para editar: " + err)
    );
    this.ruta.queryParams
      .subscribe(params => {
        this.resetear();

        console.log(params); // { order: "popular" }
        this.search = params.busqueda;
        this.fecha1 = params.desde;
        this.fecha2 = params.hasta;
        var tipo = this.getTipoLlamada(this.search, this.fecha1, this.fecha2, this.idEp, this.idDep, this.fecha);
        console.log(tipo);
        switch (tipo) {
          case 1:
            this.getOposicionesByFrom();
            break;
          case 2:
            this.getOposicionesFrom();
            break;
          case 3:
            this.busquedaOposiciones();
            break;
          case 4:
            this.getOposiciones();
            break;
        }
      }
      );
      this.getDepartamentos();

  }
  busquedaOposiciones() {
    console.log("busqueda");
    this.clienteApiRestService.busqueda(this.search, this.getFecha(this.fecha1), this.getFecha(this.fecha2), this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);
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
  buscarFecha() {
    console.log("secamiob" + this.fecha1);
    var date = new Date(this.fecha1 + '');

    var fecha = date.getFullYear() + "-" + (date.getUTCMonth() + 1) + "-" + date.getUTCDate();
    if (this.fecha != null) {
      this.href = this.href.substring(0, this.href.lastIndexOf("/fecha/"));
    }

    this.router.navigateByUrl(this.href + '/fecha/' + fecha);
  }
  getOposicionesFecha() {
    this.clienteApiRestService.getOposicionesByFecha(this.fecha, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);
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
  getCount(num: number) {
    this.clienteApiRestService.getSizeOposicion().subscribe(
      resp => {
        if (resp.status < 400) {
          this.count = Math.ceil(resp.body / num);
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
  getOposicionesFrom() {
    this.clienteApiRestService.getOposicionesFrom(this.idDep, this.idEp, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);
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

  getOposicionesByFrom() {
    this.clienteApiRestService.getOposicionesFromByFecha(this.idDep, this.idEp, this.fecha, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);
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
  getOposicion(num: String) {
    this.clienteApiRestService.getOposicion(num).subscribe(
      resp => {
        if (resp.status < 400) {
          this.oposicion = resp.body;
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

  cargarMasOposiciones(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      var tipo = this.getTipoLlamada(this.search, this.fecha1, this.fecha2, this.idEp, this.idDep, this.fecha);
      console.log(tipo);
      switch (tipo) {
        case 1:
          this.getOposicionesByFrom();
          break;
        case 2:
          this.getOposicionesFrom();
          break;
        case 3:
          this.busquedaOposiciones();
          break;
        case 4:
          this.getOposiciones();
          break;
      }
      event.target.complete();
    }, 1000);
  }
  getFecha(fecha: String): String {
    if (fecha == null)
      return "";
    var f = fecha.substring(0, 10)
    return f;
  }
  resetear() {
    console.log("resetear");

    this.oposiciones = [];
    this.fecha1 = "";
    this.fecha2 = "";
    this.search = "";
    this.pagina = 1;
    this.href = this.router.url;
  }
  buscar() {
    var busc = this.search;
    if (busc == null)
      busc = "";
    console.log(this.fecha1 + "set");
    var f1 = this.getFecha(this.fecha1);
    var f2 = this.getFecha(this.fecha2);

    //this.resetear();


    this.href = this.href.substring(0, this.href.lastIndexOf("/oposiciones/"));
    this.router.navigateByUrl(this.href + '/oposiciones/search?busqueda=' + busc + '&desde=' + f1 + '&hasta=' + f2);
  }

  buscarEvent(q: string) {
    var busc = q;
    if (q == null)
      busc = ""
    var f1 = this.getFecha(this.fecha1);
    var f2 = this.getFecha(this.fecha2);

    //this.resetear();
    console.log(f1);
    this.href = this.href.substring(0, this.href.lastIndexOf("/oposiciones/"));
    this.router.navigateByUrl(this.href + '/oposiciones/search?busqueda=' + busc + '&desde=' + f1 + '&hasta=' + f2);
  }

  getOposiciones() {
    this.clienteApiRestService.getOposiciones(this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);

          // ||[];a
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
