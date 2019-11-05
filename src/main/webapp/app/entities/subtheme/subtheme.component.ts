import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISubtheme } from 'app/shared/model/subtheme.model';
import { AccountService } from 'app/core/auth/account.service';
import { SubthemeService } from './subtheme.service';

@Component({
  selector: 'jhi-subtheme',
  templateUrl: './subtheme.component.html'
})
export class SubthemeComponent implements OnInit, OnDestroy {
  subthemes: ISubtheme[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected subthemeService: SubthemeService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.subthemeService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubtheme[]>) => res.ok),
        map((res: HttpResponse<ISubtheme[]>) => res.body)
      )
      .subscribe((res: ISubtheme[]) => {
        this.subthemes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSubthemes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubtheme) {
    return item.id;
  }

  registerChangeInSubthemes() {
    this.eventSubscriber = this.eventManager.subscribe('subthemeListModification', response => this.loadAll());
  }
}
