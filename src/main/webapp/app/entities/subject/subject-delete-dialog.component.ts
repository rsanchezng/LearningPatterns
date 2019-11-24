import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';

@Component({
  selector: 'jhi-subject-delete-dialog',
  templateUrl: './subject-delete-dialog.component.html'
})
export class SubjectDeleteDialogComponent {
  subject: ISubject;

  constructor(protected subjectService: SubjectService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subjectService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'subjectListModification',
        content: 'Deleted an subject'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subject-delete-popup',
  template: ''
})
export class SubjectDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subject }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubjectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subject = subject;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/subject', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/subject', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
