import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentSchedule } from 'app/shared/model/student-schedule.model';
import { AccountService } from 'app/core/auth/account.service';
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
      .subscribe((res: IStudentSchedule[]) => {
        this.studentSchedules = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
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
}
