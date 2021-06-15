import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { ClienteApiRestService } from './shared/cliente-api-rest.service';
import { DataService } from './shared/data.service'; 
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { OposicionesComponent } from './oposiciones/oposiciones.component';
import { DepartamentosComponent} from './departamentos/departamentos.component';
import { EpigrafesComponent} from './epigrafes/epigrafes.component';
import { DetalleOposicionComponent} from './componentes/detalle-oposicion/detalle-oposicion.component';
import { HomeComponent} from './home/home.component';
import {DetalleEpiDepComponent } from './detalle-epi-dep/detalle-epi-dep.component'
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {PdfViewerModule} from 'ng2-pdf-viewer'
import { FormsModule } from '@angular/forms';

import { HeaderComponent } from './header/header.component';
@NgModule({
  declarations: [AppComponent,OposicionesComponent,HeaderComponent,DepartamentosComponent,EpigrafesComponent,DetalleOposicionComponent,DetalleEpiDepComponent,HomeComponent],
  entryComponents: [],
  
  imports: [CommonModule,BrowserModule, IonicModule.forRoot(), AppRoutingModule,PdfViewerModule,HttpClientModule,FormsModule],
  providers: [
    HttpClient,
    ClienteApiRestService,
    DataService,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
