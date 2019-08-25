import { Component, OnInit } from '@angular/core';
import { KanbanService } from '../service/kanban-service.service';
import { ActivatedRoute } from '@angular/router';
import { Kanban } from '../model/kanban/kanban';
import { Task } from '../model/task/task';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { TaskDialogComponent } from '../task-dialog/task-dialog.component';
import { TaskService } from '../service/task.service';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.css']
})
export class KanbanComponent implements OnInit {
  
  kanban: Kanban;
  todos: Task[] = [];
  inprogress: Task[] = [];
  dones: Task[] = [];

  constructor(
    private kanbanService: KanbanService,
    private taskService: TaskService,
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
      this.updateTaskStatusAfterDragDrop(event);
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }

  openDialogForNewTask(): void {
    this.openDialog('Create New Task', new Task());
  }

  openTaskDialog(event): void {
    let taskId = event.srcElement.id;

    this.taskService.getTaskById(taskId).subscribe(
      response => {
        this.openDialog('Update Task', response);
      }
    );
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
  
  private updateTaskStatusAfterDragDrop(event: CdkDragDrop<string[], string[]>) {
    let taskId = event.item.element.nativeElement.id;
    let containerId = event.container.id;

    this.taskService.getTaskById(taskId).subscribe(
        response => {
          this.updateTaskStatus(response, containerId);
        }
    );
  }

  private updateTaskStatus(task: Task, containerId: string): void {
    if (containerId === 'todo'){
      task.status = 'TODO'
    } else if (containerId === 'inpro'){
      task.status = 'INPROGRESS'
    } else {
      task.status = 'DONE'
    }
    this.taskService.updateTask(task).subscribe();
  }

  private openDialog(title: string, task: Task): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      title: title,
      task: task,
      kanbanId: this.kanban.id
    };
    this.dialog.open(TaskDialogComponent, dialogConfig)
  }
}
