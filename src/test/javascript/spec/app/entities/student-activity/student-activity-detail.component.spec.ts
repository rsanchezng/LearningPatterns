/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentActivityDetailComponent } from 'app/entities/student-activity/student-activity-detail.component';
import { StudentActivity } from 'app/shared/model/student-activity.model';

describe('Component Tests', () => {
  describe('StudentActivity Management Detail Component', () => {
    let comp: StudentActivityDetailComponent;
    let fixture: ComponentFixture<StudentActivityDetailComponent>;
    const route = ({ data: of({ studentActivity: new StudentActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StudentActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studentActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
