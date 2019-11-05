import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStudent } from 'app/shared/model/student.model';
import { AccountService } from 'app/core/auth/account.service';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student',
  templateUrl: './student.component.html'
})
export class StudentComponent implements OnInit, OnDestroy {
  students: IStudent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected studentService: StudentService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.studentService
      .query()
      .pipe(
        filter((res: HttpResponse<IStudent[]>) => res.ok),
        map((res: HttpResponse<IStudent[]>) => res.body)
      )
      .subscribe((res: IStudent[]) => {
        this.students = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStudents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudent) {
    return item.id;
  }

  registerChangeInStudents() {
    this.eventSubscriber = this.eventManager.subscribe('studentListModification', response => this.loadAll());
  }
}
