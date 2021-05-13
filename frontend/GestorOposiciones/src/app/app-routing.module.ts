import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { OposicionesComponent } from './oposiciones/oposiciones.component';
import { DepartamentosComponent} from './departamentos/departamentos.component';
import { EpigrafesComponent} from './epigrafes/epigrafes.component';
import { DetalleEpiDepComponent } from './detalle-epi-dep/detalle-epi-dep.component'
import { DetalleOposicionComponent } from './detalle-oposicion/detalle-oposicion.component'
const routes: Routes = [
  {path: 'oposiciones', component:OposicionesComponent},
  {path: 'oposiciones/:idOp', component:DetalleOposicionComponent},
  {path: 'epigrafes/:idEpi/departamentos/:idDep/oposiciones', component:OposicionesComponent},
  {path: 'epigrafes/:idEpi/departamentos/:idDep/oposiciones/fecha/:fecha', component:OposicionesComponent},
  {path: 'oposiciones/fecha/:fecha', component:OposicionesComponent},
  {path: 'departamentos/:idDep/epigrafes/:idEpi/oposiciones',component:OposicionesComponent},
  {path: 'departamentos/:idDep/epigrafes/:idEpi/oposiciones/fecha/:fecha',component:OposicionesComponent},
  {path: 'departamentos', component:DepartamentosComponent},
  {path: 'epigrafes', component:EpigrafesComponent},
  {path: 'departamentos/:idDep/epigrafes',component: DetalleEpiDepComponent},
  {path: 'epigrafes/:idEpi/departamentos', component: DetalleEpiDepComponent},
  {path: 'oposiciones/search/:search',component:OposicionesComponent},
  {path: '**', redirectTo:'oposiciones', pathMatch:'full'}
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
