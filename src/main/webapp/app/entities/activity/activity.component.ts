import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActivity } from 'app/shared/model/activity.model';
import { AccountService } from 'app/core';
import { ActivityService } from './activity.service';

@Component({
  selector: 'jhi-activity',
  templateUrl: './activity.component.html'
})
export class ActivityComponent implements OnInit, OnDestroy {
  activities: IActivity[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected activityService: ActivityService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.activityService
      .query()
      .pipe(
        filter((res: HttpResponse<IActivity[]>) => res.ok),
        map((res: HttpResponse<IActivity[]>) => res.body)
      )
      .subscribe(
        (res: IActivity[]) => {
          this.activities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInActivities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IActivity) {
    return item.id;
  }

  registerChangeInActivities() {
    this.eventSubscriber = this.eventManager.subscribe('activityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
