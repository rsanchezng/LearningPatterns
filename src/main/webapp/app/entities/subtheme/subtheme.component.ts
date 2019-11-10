import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubtheme } from 'app/shared/model/subtheme.model';
import { AccountService } from 'app/core';
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
    protected jhiAlertService: JhiAlertService,
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
      .subscribe(
        (res: ISubtheme[]) => {
          this.subthemes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
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

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
