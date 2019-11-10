import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';

@Component({
  selector: 'jhi-theme-delete-dialog',
  templateUrl: './theme-delete-dialog.component.html'
})
export class ThemeDeleteDialogComponent {
  theme: ITheme;

  constructor(protected themeService: ThemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.themeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'themeListModification',
        content: 'Deleted an theme'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-theme-delete-popup',
  template: ''
})
export class ThemeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ theme }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ThemeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.theme = theme;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/theme', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/theme', { outlets: { popup: null } }]);
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
