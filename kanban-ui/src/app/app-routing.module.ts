import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { KanbanComponent } from './kanban/kanban.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'kanbans/:id', component: KanbanComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
