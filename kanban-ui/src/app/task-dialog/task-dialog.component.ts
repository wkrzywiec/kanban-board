import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material';
import { Task } from '../model/task/task';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-task-dialog',
  templateUrl: './task-dialog.component.html',
  styleUrls: ['./task-dialog.component.css']
})
export class TaskDialogComponent implements OnInit {

  dialogTitle: String;
  task: Task;

  form: FormGroup;
  description:string;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<TaskDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.dialogTitle = data.title;
    this.task = data.task;

    this.form = fb.group({
      title: [this.task.title, Validators.required],
      description: [this.task.description, Validators.required],
      color: [this.task.color,Validators.required]
  });
   }

  ngOnInit() {
  }

  save() {
    
}

close() {
    this.dialogRef.close();
} 

}
