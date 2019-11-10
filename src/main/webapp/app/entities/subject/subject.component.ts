import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubject } from 'app/shared/model/subject.model';
import { AccountService } from 'app/core';
import { SubjectService } from './subject.service';

@Component({
  selector: 'jhi-subject',
  templateUrl: './subject.component.html'
})
export class SubjectComponent implements OnInit, OnDestroy {
  subjects: ISubject[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected subjectService: SubjectService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.subjectService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubject[]>) => res.ok),
        map((res: HttpResponse<ISubject[]>) => res.body)
      )
      .subscribe(
        (res: ISubject[]) => {
          this.subjects = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSubjects();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubject) {
    return item.id;
  }

  registerChangeInSubjects() {
    this.eventSubscriber = this.eventManager.subscribe('subjectListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
