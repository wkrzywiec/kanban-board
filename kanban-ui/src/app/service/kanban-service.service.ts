import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kanban } from '../model/kanban/kanban';
import { Task } from '../model/task/task';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  private kanbanAppUrl = environment.kanbanAppUrl
  
  constructor(private http: HttpClient) { }

  retrieveAllKanbanBoards(): Observable<Kanban[]> {
    return this.http.get<Kanban[]>(this.kanbanAppUrl + '/kanbans/');
  }

  retrieveKanbanById(id: String): Observable<Kanban> {
    return this.http.get<Kanban>(this.kanbanAppUrl + '/kanbans/' + id);
  }

  saveNewKanban(title: string): Observable<string> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    let jsonObject = this.prepareTiTleJsonObject(title);
    return this.http.post<string>(
      this.kanbanAppUrl + '/kanbans/',
      jsonObject,
      options
    );
  }
  
  saveNewTaskInKanban(kanbanId: String, task: Task): Observable<Task> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.http.post<Task>(
      this.kanbanAppUrl + '/kanbans/' + kanbanId + '/tasks/',
      task,
      options);
  }

  private prepareTiTleJsonObject(title: string) {
    const object = {
      title: title
    }
    return JSON.stringify(object);
  }

}
