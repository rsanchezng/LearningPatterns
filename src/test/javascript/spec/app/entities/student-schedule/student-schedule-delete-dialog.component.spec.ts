import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentScheduleDeleteDialogComponent } from 'app/entities/student-schedule/student-schedule-delete-dialog.component';
import { StudentScheduleService } from 'app/entities/student-schedule/student-schedule.service';

describe('Component Tests', () => {
  describe('StudentSchedule Management Delete Component', () => {
    let comp: StudentScheduleDeleteDialogComponent;
    let fixture: ComponentFixture<StudentScheduleDeleteDialogComponent>;
    let service: StudentScheduleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentScheduleDeleteDialogComponent]
      })
        .overrideTemplate(StudentScheduleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentScheduleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentScheduleService);
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
