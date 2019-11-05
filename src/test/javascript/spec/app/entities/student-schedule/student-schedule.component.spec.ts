import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentScheduleComponent } from 'app/entities/student-schedule/student-schedule.component';
import { StudentScheduleService } from 'app/entities/student-schedule/student-schedule.service';
import { StudentSchedule } from 'app/shared/model/student-schedule.model';

describe('Component Tests', () => {
  describe('StudentSchedule Management Component', () => {
    let comp: StudentScheduleComponent;
    let fixture: ComponentFixture<StudentScheduleComponent>;
    let service: StudentScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentScheduleComponent],
        providers: []
      })
        .overrideTemplate(StudentScheduleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentScheduleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentScheduleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StudentSchedule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.studentSchedules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
