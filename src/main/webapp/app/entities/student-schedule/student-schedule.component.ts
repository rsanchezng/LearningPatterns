import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentSchedule } from 'app/shared/model/student-schedule.model';
import { StudentScheduleService } from './student-schedule.service';

@Component({
  selector: 'jhi-student-schedule',
  templateUrl: './student-schedule.component.html'
})
export class StudentScheduleComponent implements OnInit, OnDestroy {
  studentSchedules: IStudentSchedule[];
  eventSubscriber: Subscription;

  constructor(protected studentScheduleService: StudentScheduleService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.studentScheduleService.query().subscribe((res: HttpResponse<IStudentSchedule[]>) => {
      this.studentSchedules = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInStudentSchedules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudentSchedule) {
    return item.id;
  }

  registerChangeInStudentSchedules() {
    this.eventSubscriber = this.eventManager.subscribe('studentScheduleListModification', () => this.loadAll());
  }
}
