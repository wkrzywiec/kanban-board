import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
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
    return this.http.get<string>(
      this.kanbanAppUrl + '/kanbans?title=' + title
    );
  }
  
  saveNewTaskInKanban(kanbanId: string, task: Task): Observable<Task> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    
    let queryParams = new HttpParams();
    queryParams = queryParams.append("kanbanId", kanbanId);
    queryParams = queryParams.append("title", task.title);
    queryParams = queryParams.append("description", task.description);
    queryParams = queryParams.append("status", task.status);

    return this.http.get<Task>(
      this.kanbanAppUrl + '/tasks',
      { params: queryParams }
      );
  }

  private prepareTiTleJsonObject(title: string) {
    const object = {
      title: title
    }
    return JSON.stringify(object);
  }

}
