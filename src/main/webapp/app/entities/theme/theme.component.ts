import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';

@Component({
  selector: 'jhi-theme',
  templateUrl: './theme.component.html'
})
export class ThemeComponent implements OnInit, OnDestroy {
  themes: ITheme[];
  eventSubscriber: Subscription;

  constructor(protected themeService: ThemeService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.themeService.query().subscribe((res: HttpResponse<ITheme[]>) => {
      this.themes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInThemes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITheme) {
    return item.id;
  }

  registerChangeInThemes() {
    this.eventSubscriber = this.eventManager.subscribe('themeListModification', () => this.loadAll());
  }
}
