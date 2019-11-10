import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroup } from 'app/shared/model/group.model';
import { AccountService } from 'app/core';
import { GroupService } from './group.service';

@Component({
  selector: 'jhi-group',
  templateUrl: './group.component.html'
})
export class GroupComponent implements OnInit, OnDestroy {
  groups: IGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected groupService: GroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.groupService
      .query()
      .pipe(
        filter((res: HttpResponse<IGroup[]>) => res.ok),
        map((res: HttpResponse<IGroup[]>) => res.body)
      )
      .subscribe(
        (res: IGroup[]) => {
          this.groups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
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

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
