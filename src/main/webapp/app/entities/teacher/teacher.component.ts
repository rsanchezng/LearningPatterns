import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher',
  templateUrl: './teacher.component.html'
})
export class TeacherComponent implements OnInit, OnDestroy {
  teachers: ITeacher[];
  eventSubscriber: Subscription;

  constructor(protected teacherService: TeacherService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.teacherService.query().subscribe((res: HttpResponse<ITeacher[]>) => {
      this.teachers = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTeachers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITeacher) {
    return item.id;
  }

  registerChangeInTeachers() {
    this.eventSubscriber = this.eventManager.subscribe('teacherListModification', () => this.loadAll());
  }
}
