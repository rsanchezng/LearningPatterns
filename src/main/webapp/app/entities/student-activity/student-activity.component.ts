import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentActivity } from 'app/shared/model/student-activity.model';
import { AccountService } from 'app/core/auth/account.service';
import { StudentActivityService } from './student-activity.service';

@Component({
  selector: 'jhi-student-activity',
  templateUrl: './student-activity.component.html'
})
export class StudentActivityComponent implements OnInit, OnDestroy {
  studentActivities: IStudentActivity[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected studentActivityService: StudentActivityService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.studentActivityService
      .query()
      .pipe(
        filter((res: HttpResponse<IStudentActivity[]>) => res.ok),
        map((res: HttpResponse<IStudentActivity[]>) => res.body)
      )
      .subscribe((res: IStudentActivity[]) => {
        this.studentActivities = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStudentActivities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudentActivity) {
    return item.id;
  }

  registerChangeInStudentActivities() {
    this.eventSubscriber = this.eventManager.subscribe('studentActivityListModification', response => this.loadAll());
  }
}
