/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentActivityUpdateComponent } from 'app/entities/student-activity/student-activity-update.component';
import { StudentActivityService } from 'app/entities/student-activity/student-activity.service';
import { StudentActivity } from 'app/shared/model/student-activity.model';

describe('Component Tests', () => {
  describe('StudentActivity Management Update Component', () => {
    let comp: StudentActivityUpdateComponent;
    let fixture: ComponentFixture<StudentActivityUpdateComponent>;
    let service: StudentActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudentActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentActivity(123);
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
        const entity = new StudentActivity();
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
