import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from './group.service';

@Component({
  selector: 'jhi-group',
  templateUrl: './group.component.html'
})
export class GroupComponent implements OnInit, OnDestroy {
  groups: IGroup[];
  eventSubscriber: Subscription;

  constructor(protected groupService: GroupService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.groupService.query().subscribe((res: HttpResponse<IGroup[]>) => {
      this.groups = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGroup) {
    return item.id;
  }

  registerChangeInGroups() {
    this.eventSubscriber = this.eventManager.subscribe('groupListModification', () => this.loadAll());
  }
}
