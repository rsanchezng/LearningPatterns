/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentScheduleUpdateComponent } from 'app/entities/student-schedule/student-schedule-update.component';
import { StudentScheduleService } from 'app/entities/student-schedule/student-schedule.service';
import { StudentSchedule } from 'app/shared/model/student-schedule.model';

describe('Component Tests', () => {
  describe('StudentSchedule Management Update Component', () => {
    let comp: StudentScheduleUpdateComponent;
    let fixture: ComponentFixture<StudentScheduleUpdateComponent>;
    let service: StudentScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentScheduleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudentScheduleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentScheduleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentScheduleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentSchedule(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentSchedule();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
