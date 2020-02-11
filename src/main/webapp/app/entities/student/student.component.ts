import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student',
  templateUrl: './student.component.html'
})
export class StudentComponent implements OnInit, OnDestroy {
  students: IStudent[];
  eventSubscriber: Subscription;

  constructor(protected studentService: StudentService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => {
      this.students = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInStudents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudent) {
    return item.id;
  }

  registerChangeInStudents() {
    this.eventSubscriber = this.eventManager.subscribe('studentListModification', () => this.loadAll());
  }
}
