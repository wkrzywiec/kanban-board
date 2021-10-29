import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatInputModule, MatSelectModule } from "@angular/material";
import { DragDropModule } from '@angular/cdk/drag-drop';

import { HomeComponent } from './home/home.component';
import { KanbanComponent } from './kanban/kanban.component';
import { TaskDialogComponent } from './task-dialog/task-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { KanbanDialogComponent } from './kanban-dialog/kanban-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    KanbanComponent,
    TaskDialogComponent,
    KanbanDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatListModule,
    DragDropModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [TaskDialogComponent, KanbanDialogComponent]
})
export class AppModule { }
