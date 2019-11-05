import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { AccountService } from 'app/core/auth/account.service';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher',
  templateUrl: './teacher.component.html'
})
export class TeacherComponent implements OnInit, OnDestroy {
  teachers: ITeacher[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected teacherService: TeacherService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.teacherService
      .query()
      .pipe(
        filter((res: HttpResponse<ITeacher[]>) => res.ok),
        map((res: HttpResponse<ITeacher[]>) => res.body)
      )
      .subscribe((res: ITeacher[]) => {
        this.teachers = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTeachers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITeacher) {
    return item.id;
  }

  registerChangeInTeachers() {
    this.eventSubscriber = this.eventManager.subscribe('teacherListModification', response => this.loadAll());
  }
}
