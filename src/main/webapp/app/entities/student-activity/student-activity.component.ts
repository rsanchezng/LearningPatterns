import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentActivity } from 'app/shared/model/student-activity.model';
import { StudentActivityService } from './student-activity.service';

@Component({
  selector: 'jhi-student-activity',
  templateUrl: './student-activity.component.html'
})
export class StudentActivityComponent implements OnInit, OnDestroy {
  studentActivities: IStudentActivity[];
  eventSubscriber: Subscription;

  constructor(protected studentActivityService: StudentActivityService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.studentActivityService.query().subscribe((res: HttpResponse<IStudentActivity[]>) => {
      this.studentActivities = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInStudentActivities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudentActivity) {
    return item.id;
  }

  registerChangeInStudentActivities() {
    this.eventSubscriber = this.eventManager.subscribe('studentActivityListModification', () => this.loadAll());
  }
}
