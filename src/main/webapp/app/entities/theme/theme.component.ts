import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITheme } from 'app/shared/model/theme.model';
import { AccountService } from 'app/core';
import { ThemeService } from './theme.service';

@Component({
  selector: 'jhi-theme',
  templateUrl: './theme.component.html'
})
export class ThemeComponent implements OnInit, OnDestroy {
  themes: ITheme[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected themeService: ThemeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.themeService
      .query()
      .pipe(
        filter((res: HttpResponse<ITheme[]>) => res.ok),
        map((res: HttpResponse<ITheme[]>) => res.body)
      )
      .subscribe(
        (res: ITheme[]) => {
          this.themes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInThemes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITheme) {
    return item.id;
  }

  registerChangeInThemes() {
    this.eventSubscriber = this.eventManager.subscribe('themeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
