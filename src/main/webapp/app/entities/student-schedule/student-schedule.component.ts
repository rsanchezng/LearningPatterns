import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudentSchedule } from 'app/shared/model/student-schedule.model';
import { AccountService } from 'app/core';
import { StudentScheduleService } from './student-schedule.service';

@Component({
  selector: 'jhi-student-schedule',
  templateUrl: './student-schedule.component.html'
})
export class StudentScheduleComponent implements OnInit, OnDestroy {
  studentSchedules: IStudentSchedule[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected studentScheduleService: StudentScheduleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.studentScheduleService
      .query()
      .pipe(
        filter((res: HttpResponse<IStudentSchedule[]>) => res.ok),
        map((res: HttpResponse<IStudentSchedule[]>) => res.body)
      )
      .subscribe(
        (res: IStudentSchedule[]) => {
          this.studentSchedules = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStudentSchedules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudentSchedule) {
    return item.id;
  }

  registerChangeInStudentSchedules() {
    this.eventSubscriber = this.eventManager.subscribe('studentScheduleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
