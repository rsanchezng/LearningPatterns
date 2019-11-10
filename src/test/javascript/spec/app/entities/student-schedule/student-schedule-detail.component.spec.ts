/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentScheduleDetailComponent } from 'app/entities/student-schedule/student-schedule-detail.component';
import { StudentSchedule } from 'app/shared/model/student-schedule.model';

describe('Component Tests', () => {
  describe('StudentSchedule Management Detail Component', () => {
    let comp: StudentScheduleDetailComponent;
    let fixture: ComponentFixture<StudentScheduleDetailComponent>;
    const route = ({ data: of({ studentSchedule: new StudentSchedule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentScheduleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StudentScheduleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentScheduleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studentSchedule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
