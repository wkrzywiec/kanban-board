import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kanban } from '../model/kanban/kanban';
import { Task } from '../model/task/task';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  private kanbanAppUrl = "http://localhost:8080"
  
  constructor(private http: HttpClient) { }

  retrieveAllKanbanBoards(): Observable<Kanban[]> {
    return this.http.get<Kanban[]>(this.kanbanAppUrl + '/kanbans/');
  }

  retrieveKanbanById(id: String): Observable<Kanban> {
    return this.http.get<Kanban>(this.kanbanAppUrl + '/kanbans/' + id);
  }

  saveNewTaskInKanban(kanbanId: String, task: Task): Observable<Task> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.http.post<Task>(
      this.kanbanAppUrl + '/kanbans/' + kanbanId + '/tasks/',
      task,
      options);
  }
}
