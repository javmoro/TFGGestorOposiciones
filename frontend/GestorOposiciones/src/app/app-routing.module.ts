import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { OposicionesComponent } from './oposiciones/oposiciones.component'
import { DetalleOposicionComponent } from './detalle-oposicion/detalle-oposicion.component'
const routes: Routes = [
  {path: 'oposiciones', component:OposicionesComponent},
  {path: 'oposiciones/:id', component:DetalleOposicionComponent},
  {path: '**', redirectTo:'oposiciones', pathMatch:'full'}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
