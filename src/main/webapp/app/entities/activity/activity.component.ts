import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IActivity } from 'app/shared/model/activity.model';
import { AccountService } from 'app/core/auth/account.service';
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
      .subscribe((res: IActivity[]) => {
        this.activities = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
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
}
