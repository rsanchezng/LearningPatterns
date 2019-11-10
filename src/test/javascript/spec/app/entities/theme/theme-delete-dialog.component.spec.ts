/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearningPatternsTestModule } from '../../../test.module';
import { ThemeDeleteDialogComponent } from 'app/entities/theme/theme-delete-dialog.component';
import { ThemeService } from 'app/entities/theme/theme.service';

describe('Component Tests', () => {
  describe('Theme Management Delete Component', () => {
    let comp: ThemeDeleteDialogComponent;
    let fixture: ComponentFixture<ThemeDeleteDialogComponent>;
    let service: ThemeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [ThemeDeleteDialogComponent]
      })
        .overrideTemplate(ThemeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
