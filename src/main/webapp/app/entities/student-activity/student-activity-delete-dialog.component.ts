import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentActivity } from 'app/shared/model/student-activity.model';
import { StudentActivityService } from './student-activity.service';

@Component({
  selector: 'jhi-student-activity-delete-dialog',
  templateUrl: './student-activity-delete-dialog.component.html'
})
export class StudentActivityDeleteDialogComponent {
  studentActivity: IStudentActivity;

  constructor(
    protected studentActivityService: StudentActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.studentActivityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'studentActivityListModification',
        content: 'Deleted an studentActivity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-student-activity-delete-popup',
  template: ''
})
export class StudentActivityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studentActivity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StudentActivityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.studentActivity = studentActivity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/student-activity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/student-activity', { outlets: { popup: null } }]);
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
