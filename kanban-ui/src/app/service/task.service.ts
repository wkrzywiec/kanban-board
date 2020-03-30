import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from '../model/task/task';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private kanbanAppUrl = environment.kanbanAppUrl

  constructor(private http: HttpClient) { }

  updateTask(task: Task): Observable<Task> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.http.put<Task>(
      this.kanbanAppUrl + '/tasks/' + task.id,
      task,
      options);
  }

  getTaskById(id: string): Observable<Task> {
    return this.http.get<Task>(this.kanbanAppUrl + '/tasks/' + id);
  }
}
