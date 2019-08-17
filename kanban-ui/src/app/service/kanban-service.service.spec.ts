import { TestBed } from '@angular/core/testing';

import { KanbanService } from './kanban-service.service';

describe('KanbanServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: KanbanService = TestBed.get(KanbanService);
    expect(service).toBeTruthy();
  });
});
