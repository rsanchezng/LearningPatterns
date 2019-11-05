import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from './subtheme.service';

@Component({
  selector: 'jhi-subtheme-delete-dialog',
  templateUrl: './subtheme-delete-dialog.component.html'
})
export class SubthemeDeleteDialogComponent {
  subtheme: ISubtheme;

  constructor(protected subthemeService: SubthemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subthemeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'subthemeListModification',
        content: 'Deleted an subtheme'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subtheme-delete-popup',
  template: ''
})
export class SubthemeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subtheme }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubthemeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subtheme = subtheme;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/subtheme', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/subtheme', { outlets: { popup: null } }]);
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
