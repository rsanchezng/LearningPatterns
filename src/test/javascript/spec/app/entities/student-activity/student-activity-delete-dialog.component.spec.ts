/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentActivityDeleteDialogComponent } from 'app/entities/student-activity/student-activity-delete-dialog.component';
import { StudentActivityService } from 'app/entities/student-activity/student-activity.service';

describe('Component Tests', () => {
  describe('StudentActivity Management Delete Component', () => {
    let comp: StudentActivityDeleteDialogComponent;
    let fixture: ComponentFixture<StudentActivityDeleteDialogComponent>;
    let service: StudentActivityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentActivityDeleteDialogComponent]
      })
        .overrideTemplate(StudentActivityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentActivityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentActivityService);
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
