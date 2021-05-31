import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Epigrafe, Mensaje } from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';

@Component({
  selector: 'app-epigrafes',
  templateUrl: './epigrafes.component.html',
  styleUrls: ['./epigrafes.component.scss'],
})
export class EpigrafesComponent implements OnInit {
  epigrafes: Epigrafe[];
  search: String;
  pagina: number;
  href: string;
  constructor(private router: Router, private ruta: ActivatedRoute, private clienteApiRestService: ClienteApiRestService, private datos: DataService) {
    this.epigrafes = [];
    this.pagina = 0;
    this.href = this.router.url;

  }

  ngOnInit() {
    this.ruta.paramMap.subscribe(
      params => {
        this.search = params.get('search');

        if (this.search != null)
          this.buscarEpigrafes();
        else {
          this.getEpigrafes();
        }

      },
      err => console.log("Error al leer id para editar: " + err)
    )
  }
  buscarEvent(q: string) {
    this.href = this.href.substring(0, this.href.lastIndexOf("/epigrafes/"));

    this.router.navigateByUrl(this.href + '/epigrafes/search/' + q);
  }
  cargarMasOposiciones(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      this.getEpigrafes();
      event.target.complete();
    }, 1000);
  }
  buscarEpigrafes() {
    this.clienteApiRestService.busquedaEpigrafesByWord(this.search, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.epigrafes.push(...resp.body);

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
  getEpigrafes() {

    this.clienteApiRestService.getEpigrafes(this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.epigrafes.push(...resp.body);

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
}
