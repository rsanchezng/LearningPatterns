import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';

@Component({
  selector: 'jhi-subject',
  templateUrl: './subject.component.html'
})
export class SubjectComponent implements OnInit, OnDestroy {
  subjects: ISubject[];
  eventSubscriber: Subscription;

  constructor(protected subjectService: SubjectService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.subjectService.query().subscribe((res: HttpResponse<ISubject[]>) => {
      this.subjects = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSubjects();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubject) {
    return item.id;
  }

  registerChangeInSubjects() {
    this.eventSubscriber = this.eventManager.subscribe('subjectListModification', () => this.loadAll());
  }
}
