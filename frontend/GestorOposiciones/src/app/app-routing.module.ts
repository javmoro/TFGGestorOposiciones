import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { OposicionesComponent } from './oposiciones/oposiciones.component';
import { DepartamentosComponent} from './departamentos/departamentos.component';
import { EpigrafesComponent} from './epigrafes/epigrafes.component';
import { DetalleEpiDepComponent } from './detalle-epi-dep/detalle-epi-dep.component';
import { DetalleOposicionComponent} from './componentes/detalle-oposicion/detalle-oposicion.component';
import { HomeComponent } from './home/home.component';
const routes: Routes = [
  {path: 'oposiciones', component:OposicionesComponent},
  {path: 'oposiciones/search',component:OposicionesComponent},
  {path: 'oposiciones/:idOp', component:DetalleOposicionComponent},
  {path: 'epigrafes/:idEpi/departamentos/:idDep/oposiciones', component:OposicionesComponent},
  {path: 'epigrafes/:idEpi/departamentos/:idDep/oposiciones/fecha/:fecha', component:OposicionesComponent},
  {path: 'oposiciones/fecha/:fecha', component:OposicionesComponent},
  {path: 'departamentos/:idDep/epigrafes/:idEpi/oposiciones',component:OposicionesComponent},
  {path: 'departamentos/:idDep/epigrafes/:idEpi/oposiciones/fecha/:fecha',component:OposicionesComponent},
  {path: 'departamentos', component:DepartamentosComponent},
  {path: 'departamentos/search/:search', component:DepartamentosComponent},
  {path: 'epigrafes', component:EpigrafesComponent},
  {path: 'epigrafes/search/:search', component:EpigrafesComponent},
  {path: 'departamentos/:idDep/epigrafes',component: DetalleEpiDepComponent},
  {path: 'epigrafes/:idEpi/departamentos', component: DetalleEpiDepComponent},
  {path: 'departamentos/search/:search',component:DepartamentosComponent},
  {path: 'epigrafes/search/:search',component:EpigrafesComponent},
  {path:  'home', component:HomeComponent},
  {path: '**', redirectTo:'home', pathMatch:'full'},

  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
