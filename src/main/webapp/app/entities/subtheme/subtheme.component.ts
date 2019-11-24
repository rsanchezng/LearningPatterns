import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ISubtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from './subtheme.service';

@Component({
  selector: 'jhi-subtheme',
  templateUrl: './subtheme.component.html'
})
export class SubthemeComponent implements OnInit, OnDestroy {
  subthemes: ISubtheme[];
  eventSubscriber: Subscription;

  constructor(protected subthemeService: SubthemeService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.subthemeService.query().subscribe((res: HttpResponse<ISubtheme[]>) => {
      this.subthemes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSubthemes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubtheme) {
    return item.id;
  }

  registerChangeInSubthemes() {
    this.eventSubscriber = this.eventManager.subscribe('subthemeListModification', () => this.loadAll());
  }
}
