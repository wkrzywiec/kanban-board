import { Component, OnInit } from '@angular/core';
import { Kanban } from '../model/kanban/kanban';
import { KanbanService } from '../service/kanban-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  kanbanList: Kanban[];

  constructor(
    private kanbanService: KanbanService
  ) { }

  ngOnInit() {
    this.retrieveAllKanbanBoards();
  }

  private retrieveAllKanbanBoards(): void {
    this.kanbanService.retrieveAllKanbanBoards().subscribe(

      response => {
        this.kanbanList = response;
      }
    )
  }
}
