import { Component, OnInit } from '@angular/core';
import { KanbanService } from '../service/kanban-service.service';
import { ActivatedRoute } from '@angular/router';
import { Kanban } from '../model/kanban/kanban';
import { Task } from '../model/task/task';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { TaskDialogComponent } from '../task-dialog/task-dialog.component';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.css']
})
export class KanbanComponent implements OnInit {
  
//https://blog.angular-university.io/angular-material-dialog/
//https://code-maze.com/angular-material-form-validation/

  kanban: Kanban;
  todos: Task[] = [];
  inprogress: Task[] = [];
  dones: Task[] = [];

  constructor(
    private kanbanService: KanbanService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.getKanban();
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }

  openDialogForNewTask() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      title: 'Create New Task',
      task: new Task()
  };

    this.dialog.open(TaskDialogComponent, dialogConfig)
  }

  private getKanban(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.kanbanService.retrieveKanbanById(id).subscribe(

      response => {
        this.kanban = response;
        this.splitTasksByStatus(response);
      }
    )
  }

  private splitTasksByStatus(kanban: Kanban): void {
    this.todos = kanban.tasks.filter(t=>t.status==='TODO');
    this.inprogress = kanban.tasks.filter(t=>t.status==='INPROGRESS');
    this.dones = kanban.tasks.filter(t=>t.status==='DONE');
  }
  

}
