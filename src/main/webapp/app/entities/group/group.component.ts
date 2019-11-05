import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IGroup } from 'app/shared/model/group.model';
import { AccountService } from 'app/core/auth/account.service';
import { GroupService } from './group.service';

@Component({
  selector: 'jhi-group',
  templateUrl: './group.component.html'
})
export class GroupComponent implements OnInit, OnDestroy {
  groups: IGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected groupService: GroupService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.groupService
      .query()
      .pipe(
        filter((res: HttpResponse<IGroup[]>) => res.ok),
        map((res: HttpResponse<IGroup[]>) => res.body)
      )
      .subscribe((res: IGroup[]) => {
        this.groups = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGroup) {
    return item.id;
  }

  registerChangeInGroups() {
    this.eventSubscriber = this.eventManager.subscribe('groupListModification', response => this.loadAll());
  }
}
