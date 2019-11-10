import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudentActivity } from 'app/shared/model/student-activity.model';
import { AccountService } from 'app/core';
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
    protected jhiAlertService: JhiAlertService,
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
      .subscribe(
        (res: IStudentActivity[]) => {
          this.studentActivities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
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

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
