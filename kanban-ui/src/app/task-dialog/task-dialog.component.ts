import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Task } from '../model/task/task';

@Component({
  selector: 'app-task-dialog',
  templateUrl: './task-dialog.component.html',
  styleUrls: ['./task-dialog.component.css']
})
export class TaskDialogComponent implements OnInit {

  title: String;
  task: Task;

  constructor(
    @Inject(MAT_DIALOG_DATA) data) {

    this.title = data.title;
    this.task = data.task;
   }

  ngOnInit() {
  }

}
