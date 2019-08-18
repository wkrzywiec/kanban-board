import { Component, OnInit } from '@angular/core';
import { KanbanService } from '../service/kanban-service.service';
import { ActivatedRoute } from '@angular/router';
import { Kanban } from '../model/kanban/kanban';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.css']
})
export class KanbanComponent implements OnInit {

  kanban: Kanban;

  constructor(
    private kanbanService: KanbanService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getKanban();
  }

  private getKanban(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.kanbanService.retrieveKanbanById(id).subscribe(

      response => {
        this.kanban = response;
      }
    )
  }

}
