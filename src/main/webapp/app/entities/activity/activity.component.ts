import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';

@Component({
  selector: 'jhi-activity',
  templateUrl: './activity.component.html'
})
export class ActivityComponent implements OnInit, OnDestroy {
  activities: IActivity[];
  eventSubscriber: Subscription;

  constructor(protected activityService: ActivityService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.activityService.query().subscribe((res: HttpResponse<IActivity[]>) => {
      this.activities = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInActivities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IActivity) {
    return item.id;
  }

  registerChangeInActivities() {
    this.eventSubscriber = this.eventManager.subscribe('activityListModification', () => this.loadAll());
  }
}
