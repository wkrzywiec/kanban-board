import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kanban } from '../model/kanban/kanban';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  private kanbanAppUrl = "http://localhost:8080"
  
  constructor(private http: HttpClient) { }

  retrieveAllKanbanBoards(): Observable<Kanban[]> {
    return this.http.get<Kanban[]>(this.kanbanAppUrl + '/kanbans/');
  }
}
