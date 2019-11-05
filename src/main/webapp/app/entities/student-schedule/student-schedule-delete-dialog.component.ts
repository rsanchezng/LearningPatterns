import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentSchedule } from 'app/shared/model/student-schedule.model';
import { StudentScheduleService } from './student-schedule.service';

@Component({
  selector: 'jhi-student-schedule-delete-dialog',
  templateUrl: './student-schedule-delete-dialog.component.html'
})
export class StudentScheduleDeleteDialogComponent {
  studentSchedule: IStudentSchedule;

  constructor(
    protected studentScheduleService: StudentScheduleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.studentScheduleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'studentScheduleListModification',
        content: 'Deleted an studentSchedule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-student-schedule-delete-popup',
  template: ''
})
export class StudentScheduleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studentSchedule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StudentScheduleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.studentSchedule = studentSchedule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/student-schedule', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/student-schedule', { outlets: { popup: null } }]);
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
